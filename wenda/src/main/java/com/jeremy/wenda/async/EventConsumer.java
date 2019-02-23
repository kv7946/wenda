package com.jeremy.wenda.async;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jeremy.wenda.utils.JedisAdapter;
import com.jeremy.wenda.utils.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EventConsumer implements InitializingBean,ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(EventConsumer.class);
    //确定处理器和事件的映射关系，一个事件可能会有多个处理器
    private Map<EventType,List<EventHandler>> conf = new HashMap<>();
    private ApplicationContext applicationContext;

    @Autowired
    JedisAdapter jedisAdapter;
    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String,EventHandler> beans = applicationContext.getBeansOfType(EventHandler.class);
        if(beans != null){
            for(Map.Entry<String,EventHandler> entry : beans.entrySet()){
                //同时一个处理器也可以对多个事件感兴趣，获取所有感兴趣的事件
                List<EventType> eventTypes = entry.getValue().getSupportEventType();
                for(EventType eventType : eventTypes ){
                    if(!conf.containsKey(eventType)){
                        conf.put(eventType,new ArrayList<EventHandler>());
                    }
                    conf.get(eventType).add(entry.getValue());
                }
            }
        }
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    String key = RedisKeyUtil.getEventQueueKey();
                    List<String> events = jedisAdapter.brpop(0,key);
                    for(String message : events){
                        if(message.equals(key)){
                            continue;
                        }
                        EventModel eventModel = JSONObject.parseObject(message,EventModel.class);
                        if(!conf.containsKey(eventModel.getEventType())){
                            logger.info("不能识别的事件");
                            continue;
                        }
                        for(EventHandler eventHandler : conf.get(eventModel.getEventType())){
                            eventHandler.doHandle(eventModel);
                        }
                    }
                }
            }
        });
        thread.start();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}

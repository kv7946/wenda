package com.jeremy.wenda.async.handler;

import com.alibaba.fastjson.JSONObject;
import com.jeremy.wenda.async.EventHandler;
import com.jeremy.wenda.async.EventModel;
import com.jeremy.wenda.async.EventType;
import com.jeremy.wenda.model.Message;
import com.jeremy.wenda.model.User;
import com.jeremy.wenda.service.MessageService;
import com.jeremy.wenda.service.UserService;
import com.jeremy.wenda.utils.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class LikeHandler implements EventHandler{
    @Autowired
    UserService userService;
    @Autowired
    MessageService messageService;

    @Override
    public void doHandle(EventModel eventModel) {
        Message message = new Message();
        message.setFromId(WendaUtil.SYSTEM_USERID);
        message.setToId(eventModel.getActorId());//EntityOwnerId 是发表评论/回答的人
        message.setCreatedDate(new Date());
        User user = userService.getUser(eventModel.getActorId());//actorId 是点赞的那个人
        System.out.println("LikeHandler" + JSONObject.toJSON(eventModel));
        System.out.println("LikeHandler" + eventModel.getExt("questionId"));
        message.setContent("用户" + user.getName() +
                "赞了你的评论,http://192.168.25.128:8090/question/" + eventModel.getExt("questionId"));
        messageService.addMessage(message);
    }

    @Override
    public List<EventType> getSupportEventType() {
        return Arrays.asList(EventType.LIKE);
    }
}

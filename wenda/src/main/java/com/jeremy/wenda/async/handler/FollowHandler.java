package com.jeremy.wenda.async.handler;

import com.jeremy.wenda.async.EventHandler;
import com.jeremy.wenda.async.EventModel;
import com.jeremy.wenda.async.EventType;
import com.jeremy.wenda.model.EntityType;
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

/**
 * Created by nowcoder on 2016/7/30.
 */
@Component
public class FollowHandler implements EventHandler {
    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @Override
    public void doHandle(EventModel model) {
        Message message = new Message();
        message.setFromId(WendaUtil.SYSTEM_USERID);
        message.setToId(model.getEntityOwnerId());
        message.setCreatedDate(new Date());
        User user = userService.getUser(model.getActorId());

        if (model.getEntityType() == EntityType.ENTITY_QUESTION) {
            message.setContent("用户" + user.getName()
                    + "关注了你的问题,http://127.0.0.1:8080/question/" + model.getEntityId());
        } else if (model.getEntityType() == EntityType.ENTITY_USER) {
            message.setContent("用户" + user.getName()
                    + "关注了你,http://127.0.0.1:8080/user/" + model.getActorId());
        }

        messageService.addMessage(message);
    }

    @Override
    public List<EventType> getSupportEventType() {
        return Arrays.asList(EventType.FOLLOW);
    }
}

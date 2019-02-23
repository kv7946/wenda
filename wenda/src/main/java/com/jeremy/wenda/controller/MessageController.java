package com.jeremy.wenda.controller;

import com.jeremy.wenda.model.HostHolder;
import com.jeremy.wenda.model.Message;
import com.jeremy.wenda.model.User;
import com.jeremy.wenda.model.ViewObject;
import com.jeremy.wenda.service.MessageService;
import com.jeremy.wenda.service.SensitiveService;
import com.jeremy.wenda.service.UserService;
import com.jeremy.wenda.utils.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class MessageController {
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);
    @Autowired
    HostHolder hostHolder;
    @Autowired
    MessageService messageService;
    @Autowired
    UserService userService;
    @Autowired
    SensitiveService sensitiveService;
    @ResponseBody
    @RequestMapping(value = "/msg/addMessage",method = RequestMethod.POST)
    public String addMessage(@RequestParam("toName") String toName,
                             @RequestParam("content") String content){
        try{
            if(hostHolder.getUser() == null){
                WendaUtil.getJSONString(999,"用户未登录");
            }
            User user= userService.selectByName(toName);
            if(user == null){
                WendaUtil.getJSONString(1,"用户不存在");
            }
            Message message = new Message();
            message.setFromId(hostHolder.getUser().getId());
            message.setToId(user.getId());
            content = HtmlUtils.htmlEscape(content);
            content = sensitiveService.filter(content);
            message.setContent(content);
            message.setCreatedDate(new Date());
            messageService.addMessage(message);
            return WendaUtil.getJSONString(0);
        }catch (Exception e){
            logger.info("发送消息异常" + e.getMessage());
        }
        return WendaUtil.getJSONString(1, "插入站内信失败");
    }
    @RequestMapping(value = "/msg/list",method = RequestMethod.GET)
    public String conversationList(Model model){
        try{
            int localUserId = hostHolder.getUser().getId();
            List<ViewObject> conversations = new ArrayList<>();
            List<Message> conversationList = messageService.getConversationList(localUserId,0,10);
            for(Message message : conversationList){
                ViewObject vo = new ViewObject();
                vo.set("message",message);
                //判断目标id，看当前信息中的fromId是否和host中的id一致，一致就是message中的toId，否则是自己
                int targetId = message.getFromId() == localUserId ? message.getToId() : message.getFromId();
                User user = userService.getUser(targetId);
                vo.set("user",user);
                vo.set("unread",messageService.getConvesationUnreadCount(localUserId,message.getConversationId()));
                conversations.add(vo);
                model.addAttribute("conversations",conversations);
            }
        }catch (Exception e){
            logger.error("获取站内信列表失败" + e.getMessage());
        }
        return "letter";
    }
    @RequestMapping(value = "/msg/detail",method = RequestMethod.GET)
    public String conversationDetail(Model model, @RequestParam("conversationId") String conversationId) {
        try {
            List<Message> conversationList = messageService.getConversationDetail(conversationId, 0, 10);
            List<ViewObject> messages = new ArrayList<>();
            for (Message msg : conversationList) {
                ViewObject vo = new ViewObject();
                vo.set("message", msg);
                User user = userService.getUser(msg.getFromId());
                if (user == null) {
                    continue;
                }
                vo.set("headUrl", user.getHeadUrl());
                vo.set("userId", user.getId());
                messages.add(vo);
            }
            model.addAttribute("messages", messages);
        } catch (Exception e) {
            logger.error("获取详情消息失败" + e.getMessage());
        }
        return "letterDetail";
    }
}

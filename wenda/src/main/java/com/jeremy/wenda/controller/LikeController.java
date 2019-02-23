package com.jeremy.wenda.controller;

import com.jeremy.wenda.async.EventModel;
import com.jeremy.wenda.async.EventProducer;
import com.jeremy.wenda.async.EventType;
import com.jeremy.wenda.model.Comment;
import com.jeremy.wenda.model.EntityType;
import com.jeremy.wenda.model.HostHolder;
import com.jeremy.wenda.service.CommentService;
import com.jeremy.wenda.service.LikeService;
import com.jeremy.wenda.utils.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LikeController {
    @Autowired
    LikeService likeService;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    CommentService commentService;
    @Autowired
    EventProducer eventProducer;
    @ResponseBody
    @RequestMapping(path = {"/like"},method = {RequestMethod.POST})
    public String like(@RequestParam("commentId") int commentId){
        if(hostHolder.getUser() == null){
            return WendaUtil.getJSONString(999);
        }
        Comment comment = commentService.getCommentById(commentId);
        System.out.println("这是通过comment获取到的EntityId" + comment.getEntityId());
        EventModel eventModel = new EventModel(EventType.LIKE);
        eventProducer.fireEvent(eventModel
                .setActorId(hostHolder.getUser().getId()).setEntityId(commentId)
                .setEntityType(EntityType.ENTITY_COMMENT).setEntityOwnerId(comment.getUserId())
                .setExt("questionId", String.valueOf(comment.getEntityId())));
        System.out.println(eventModel.getExt("questionId"));
        long likeCount = likeService.like(hostHolder.getUser().getId(), EntityType.ENTITY_COMMENT,commentId);
        return WendaUtil.getJSONString(0,String.valueOf(likeCount));
    }
    @RequestMapping(path = {"/dislike"},method = {RequestMethod.POST})
    @ResponseBody
    public String dislike(@RequestParam("commentId") int commentId){
        if(hostHolder.getUser() == null){
            return WendaUtil.getJSONString(999);
        }
        Comment comment = commentService.getCommentById(commentId);
        long likeCount = likeService.disLike(hostHolder.getUser().getId(), EntityType.ENTITY_COMMENT,commentId);
        return WendaUtil.getJSONString(0,String.valueOf(likeCount));
    }
}

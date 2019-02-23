package com.jeremy.wenda.controller;

import com.jeremy.wenda.model.*;
import com.jeremy.wenda.service.*;
import com.jeremy.wenda.utils.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class QuestionController {
    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);
    @Autowired
    HostHolder hostHolder;
    @Autowired
    QuestionService questionService;
    @Autowired
    UserService userService;
    @Autowired
    SensitiveService sensitiveService;
    @Autowired
    CommentService commentService;
    @Autowired
    LikeService likeService;
    @RequestMapping(value = "/question/{qid}",method = RequestMethod.GET)
    public String questionDetail(Model model, @PathVariable("qid") int qid){
        Question question = questionService.getById(qid);
        model.addAttribute("question",question);
        List<Comment> commentList = commentService.getCommentsByEntity(qid, EntityType.ENTITY_COMMENT);
        List<ViewObject> vos = new ArrayList<>();
        System.out.println("commentList 大小为"+ commentList.size());
        for (Comment comment : commentList) {
            ViewObject vo = new ViewObject();
            vo.set("comment", comment);
            if(hostHolder == null){
                vo.set("liked",0);
            }else{
                vo.set("liked",likeService.likeStatus(hostHolder.getUser().getId(), EntityType.ENTITY_COMMENT, comment.getId()));
            }
            vo.set("likeCount",likeService.likeCount(EntityType.ENTITY_COMMENT,comment.getId()));
            vo.set("user", userService.getUser(comment.getUserId()));
            vos.add(vo);
        }
        model.addAttribute("comments", vos);
        return "detail";
    }
    @RequestMapping(value = "/question/add",method = RequestMethod.POST)
    @ResponseBody
    public String addQuestion(@RequestParam("title") String title,@RequestParam("content") String content){
        try{
            Question question = new Question();
            title = sensitiveService.filter(title);
            question.setTitle(title);
            question.setCreatedDate(new Date());
            content = sensitiveService.filter(content);
            question.setContent(content);
            if(hostHolder.getUser() == null){
                question.setUserId(WendaUtil.ANONYMOUS_USERID);
            }else {
                question.setUserId(hostHolder.getUser().getId());
            }
            if(questionService.addQuestion(question) > 0){
                return  WendaUtil.getJSONString(0);
            }
        }catch (Exception e){
           logger.info("增加问题失败",e.getMessage());
        }

        return WendaUtil.getJSONString(1,"失败");
    }
}

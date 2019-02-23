package com.jeremy.wenda.service;

import com.jeremy.wenda.dao.QuestionDao;
import com.jeremy.wenda.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Service
@Component
public class QuestionService {
    @Autowired
    QuestionDao questionDao;
    public List<Question> getLatestQuestions(int userId, int offset, int limit) {
        return questionDao.selectLatestQuestions(userId, offset, limit);
    }
    public Question getById(int id){
        return questionDao.getById(id);
    }
    public int addQuestion(Question question){
        question.setTitle(HtmlUtils.htmlEscape(question.getTitle()));
        question.setContent(HtmlUtils.htmlEscape(question.getContent()));
        return questionDao.addQuestion(question) > 0? question.getId():0;
    }
    public int updateCommentCount(int id,int count){
        return questionDao.updateQuestionCount(id,count);
    }
}

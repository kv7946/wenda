package com.jeremy.wenda.service;

import com.jeremy.wenda.dao.CommentDao;
import com.jeremy.wenda.dao.MessageDao;
import com.jeremy.wenda.model.Comment;
import com.jeremy.wenda.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentDao commentDao;

    public List<Comment> getCommentsByEntity(int entityId, int entityType) {
        return commentDao.selectByEntity(entityId, entityType);
    }
    public int getUserCommentCount(int userId) {
        return commentDao.getUserCommentCount(userId);
    }
    public int addComment(Comment comment) {
        return commentDao.addComment(comment);
    }

    public int getCommentCount(int entityId, int entityType) {
        return commentDao.getCommentCount(entityId, entityType);
    }
    public Comment getCommentById(int id){
        return  commentDao.getCommentById(id);
    }
    public void deleteComment(int entityId, int entityType) {
        commentDao.updateStatus(entityId, entityType, 1);
    }
}

package com.jeremy.wenda.service;

import com.jeremy.wenda.dao.UserDao;
import com.jeremy.wenda.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;
    public User getUser(int id) {
        return userDao.selectById(id);
    }
    public User selectByName(String name){
        return userDao.selectByName(name);
    }

}

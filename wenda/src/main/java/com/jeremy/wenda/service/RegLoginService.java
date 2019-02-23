package com.jeremy.wenda.service;

import com.jeremy.wenda.dao.TicketDao;
import com.jeremy.wenda.dao.UserDao;
import com.jeremy.wenda.model.Ticket;
import com.jeremy.wenda.model.User;
import com.jeremy.wenda.utils.WendaUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RegLoginService {
    @Autowired
    UserDao userDao;
    @Autowired
    TicketDao ticketDao;
    public Map<String,String> register(String username,String password){
        Map<String,String> map = new HashMap<>();
        User user = userDao.selectByName(username);
        //用户名和密码为空判断
        if(StringUtils.isBlank(username)){
            map.put("msg","用户名不能为空");
            return map;
        }
        if(StringUtils.isBlank(password)){
            map.put("msg","用户密码不能为空");
            return map;
        }
        //判断用户是否存在
        if(user != null){
            map.put("msg","该用户已经注册过");
            return map;
        }
        //加强密码强度
        user = new User();
        user.setName(username);
        user.setSalt(UUID.randomUUID().toString().substring(0,5));
        String head = String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000));
        user.setHeadUrl(head);
        user.setPassword(WendaUtil.MD5(password + user.getSalt()));
        userDao.addUser(user);
        return map;
    }
    public Map<String,String> login(String username,String password){
        Map<String,String> map = new HashMap<>();
        User user = userDao.selectByName(username);
        //用户名和密码为空判断
        if(StringUtils.isBlank(username)){
            map.put("msg","用户名不能为空");
            return map;
        }
        if(StringUtils.isBlank(password)){
            map.put("msg","用户密码不能为空");
            return map;
        }
        if (user == null) {
            map.put("msg", "用户名不存在");
            return map;
        }
        if (!WendaUtil.MD5(password+user.getSalt()).equals(user.getPassword())) {
            map.put("msg", "密码不正确");
            return map;
        }
        String ticket = addTicket(user.getId());
        map.put("ticket",ticket);
        return map;
    }
    public String addTicket(int userId){
        Ticket ticket = new Ticket();
        ticket.setUserId(userId);
        Date date = new Date();
        date.setTime(date.getTime() + 100 * 3600 * 24);
        ticket.setExpired(date);
        ticket.setStatus(0);
        ticket.setTicket(UUID.randomUUID().toString().replaceAll("-",""));
        ticketDao.addTicket(ticket);
        return ticket.getTicket();
    }

    public void logout(String ticket) {
        ticketDao.updateStatus(ticket,1);
    }
}

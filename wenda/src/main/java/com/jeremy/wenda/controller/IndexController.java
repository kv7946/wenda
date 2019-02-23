package com.jeremy.wenda.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class IndexController {

//    @RequestMapping("/index")
//    public String index(){
//        return "Hello Jeremy";
//    }
    @RequestMapping(value = "/profile/{groupId}/{userId}")
    public String profile(@PathVariable("groupId") String grouId, @PathVariable("userId") String userId){
        return "Hello Jeremy in "+ grouId +"小组中的"+ userId;
    }
    @RequestMapping(value = "/show/{groupId}/{userId}")
    public String show(@PathVariable("groupId") String grouId,
                       @PathVariable("userId") String userId,
                       @RequestParam(value = "name")String name,
                       @RequestParam(value = "age") int age){
        return "Hello Jeremy in "+ grouId +"小组中的"+ userId + "年龄为"
                + age + "的" + name;
    }
}

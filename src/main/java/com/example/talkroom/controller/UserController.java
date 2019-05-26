package com.example.talkroom.controller;

import com.example.talkroom.bean.User;
import com.example.talkroom.service.UserServiceImpl;
import com.example.talkroom.utils.ToCast;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Roxi酱
 */
@Controller
public class UserController {
    @Resource
    UserServiceImpl userService;
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String ws(){
        return "ws";
    }

    @ResponseBody
    @RequestMapping(value = "/enroll",method = RequestMethod.POST)
    public String enroll(@RequestParam String name,@RequestParam String password, String sex){
        User user=new User(name,password,sex);
        userService.enroll(user);
        return "啊，欢迎注册成功";
    }

    @ResponseBody
    @RequestMapping(value = "/landing",method = {RequestMethod.POST,RequestMethod.GET})
    public String landing(@RequestParam String id, @RequestParam String password, HttpServletRequest request){
        User user=new User(Integer.valueOf(id),password);
        user=userService.verify(user);
        if(user!=null){
                HttpSession session=request.getSession();
                session.setAttribute("user",user);
                return "验证通过";
        }
        return "验证失败";
    }

    @ResponseBody
    @RequestMapping(value = "/add",method = {RequestMethod.GET,RequestMethod.POST})
    public String addFriend(@RequestParam String id,HttpServletRequest request){
        HttpSession session=request.getSession();
        String you= (String) session.getAttribute("user");
        if(userService.addFriend(ToCast.intCast(you),ToCast.intCast(id))) {
            return "添加成功";
        }else {
            return "添加失败";
        }
    }
}

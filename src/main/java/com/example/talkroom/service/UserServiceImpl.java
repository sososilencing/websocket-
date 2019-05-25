package com.example.talkroom.service;

import com.example.talkroom.bean.User;
import com.example.talkroom.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Roxi酱
 */
@Service
public class UserServiceImpl implements UserService{
    @Resource
    UserMapper userMapper;

    /**
     * 注册
     * @param user
     */
    public void enroll(User user){
        userMapper.insert(user);
    }

    /**
     *验证登陆
     */
    public User verify(User user){
        User user1=userMapper.select(user);
        if(user1!=null){
            return user1;
        }
        return null;
    }

    /**
     *
     * @param uid1
     * @param uid2
     */
    public boolean addFriend(int uid1,int uid2){
        if(uid1==-1|| uid2==-1){
            return false;
        }
        userMapper.addFriend(uid1,uid2);
        return true;
    }
}

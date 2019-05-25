package com.example.talkroom.service;

import com.example.talkroom.bean.Relation;
import com.example.talkroom.bean.User;
import com.example.talkroom.mapper.RelationMapper;
import com.example.talkroom.mapper.UserMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Roxi酱
 */

public class WebsocketService {

    @Resource
    RelationMapper relationMapper;
    @Resource
    UserMapper userMapper;
    public WebsocketService(){
        System.out.println("??");
    }
    /**
     *是否好友关系
     * @return
     */
    private boolean relation(int uid1,int uid2){
        Relation relation=relationMapper.judge(uid1,uid2);
        if(relation==null) {
            return false;
        }
        return true;
    }

    /**
     * 是单聊还是群聊
     * @return 单聊 返回 单聊的name 的一个数组 ，群聊 返回null 然后广播
     */
    public int isOne(@NotNull String message, int id) {
        List<Integer> list = new ArrayList<>();
        String[] contexts = message.split(":");
        System.out.println(contexts[0]);
        if (contexts[0] == null || "".equals(contexts[0])) {
            System.out.println("看我返回的是什么");
            return -1;
        } else {
            int uid = 0;
            try {
                System.out.println("eee");
                System.out.println(userMapper == null);
                System.out.println(userMapper.getUser(contexts[0]));
            } catch (NullPointerException e) {
                e.printStackTrace();
                return 0;
            }
            return uid;
        }
    }
}

package com.example.talkroom.service;

import com.example.talkroom.bean.Relation;
import com.example.talkroom.bean.User;
import com.example.talkroom.mapper.RelationMapper;
import com.example.talkroom.mapper.UserMapper;
import com.example.talkroom.utils.ToCast;
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
@Component
public class WebsocketService {

    @Resource
    RelationMapper relationMapper;
    @Resource
    UserMapper userMapper;
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
        if (contexts[0] == null || "".equals(contexts[0])) {
            System.out.println("看我返回的是什么");
            return -1;
        } else {
            String uid = userMapper.getUser(contexts[0]);
           if(uid==null){
               return 0;
           }
           return ToCast.intCast(uid);
        }
    }
}

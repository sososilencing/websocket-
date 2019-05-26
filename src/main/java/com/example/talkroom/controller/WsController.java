package com.example.talkroom.controller;

import com.example.talkroom.bean.User;
import com.example.talkroom.config.WebsocketConfig;
import com.example.talkroom.service.WebsocketService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author Roxi酱
 */
@ServerEndpoint(value = "/log",configurator =  WebsocketConfig.class)
@Component
public class WsController {

    @Resource
    WebsocketService websocketService;


    private HttpSession httpSession;
    private Session session;
    private static int count=0;
    private static CopyOnWriteArraySet<WsController> wsControllers=new CopyOnWriteArraySet<>();
    private static Map<Integer,String> map=new HashMap<>();
    private static Map<Integer,WsController> wsControllerMap=new HashMap<>();

//HttpSession  是唯一的 在每个 浏览器 开启 单独界面 就用 HttpSession 实现
    @OnOpen
    public void onOpen(Session session, EndpointConfig config) throws IOException {
            count++;
            this.httpSession= (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
            this.session=session;
            for(WsController wsController:wsControllers){
                if(wsController!=this){
                    wsControllers.add(this);
                }else {
                    session.getBasicRemote().sendText("已开启聊天界面");
                    session.close();
                }
            }
            if(httpSession==null){
                session.getBasicRemote().sendText("未登录，连接关闭");
                session.close();
            }
        User user= (User) httpSession.getAttribute("user");
        map.put(user.getId(),user.getName());
        wsControllerMap.put(user.getId(),this);
        System.out.println("连接成功 当前人数为：" + count);
    }

    @OnClose
    public void onClose(){
        wsControllers.remove(this);
        count--;
        System.out.println("断开连接 当前人数为: " + count);
    }
    @OnError
    public void onError(Throwable error){
       session.getAsyncRemote().sendText("发生了错误");
       error.printStackTrace();
    }

    @OnMessage
    public void onMessage(String message,Session session) throws IOException {
        User user= (User) httpSession.getAttribute("user");
        message=message.replaceAll("<","&lt;");
        message=message.replaceAll(">","&gt;");
        String[] context = message.split(":");
        int uid2=websocketService.isOne(message,user.getId());
        System.out.println(uid2);
        if(uid2==0) {
            this.session.getBasicRemote().sendText("并没有这个人");
        }
        else if(uid2!=-1){
            sendTo(user.getName(), context[0], uid2, context[1]);
        }else {
            sendAll(user.getName(), message);
        }
    }

    public void sendAll(String name,String message) throws IOException {
        System.out.println("1");
        for (WsController item :wsControllers) {
            System.out.println("e");
            item.session.getBasicRemote().sendText(name+message);
        }
    }


    /*
    这里 还有 就是 发的用户没上线的问题
     */
    public void sendTo(String name1,String name2,int id,String message) throws IOException {
        WsController item=wsControllerMap.get(id);
        if(this.equals(item)) {
            this.session.getBasicRemote().sendText(name1 + "向" + name2 + "单独发送了：" + message);
        }else {
            this.session.getBasicRemote().sendText(name1 + "向" + name2 + "单独发送了：" + message);
            item.session.getBasicRemote().sendText(name1 + "向" + name2 + "单独发送了：" + message);
        }
    }
}


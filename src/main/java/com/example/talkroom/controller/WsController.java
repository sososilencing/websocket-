package com.example.talkroom.controller;

import com.example.talkroom.bean.User;
import com.example.talkroom.config.GetHttpSessionConfigurator;
import com.example.talkroom.service.WebsocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author Roxi酱
 */
@ServerEndpoint(value = "/log",configurator = GetHttpSessionConfigurator.class)
@RestController
@Component
public class WsController {

    final
    WebsocketService websocketService;


    private HttpSession httpSession;
    private Session session;
    private static int count=0;
    private static CopyOnWriteArraySet<WsController> wsControllers=new CopyOnWriteArraySet<>();
    private static Map<Integer,String> map=new HashMap<>();
   private static Map<Integer,WsController> wsControllerMap=new HashMap<>();

    public WsController(WebsocketService websocketService) {
        System.out.println(websocketService==null);
        this.websocketService = websocketService;
    }

    @OnOpen
    public void onOpen(Session session, EndpointConfig config, @PathParam("name") String name) throws IOException {
        this.httpSession= (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        this.session=session;
        wsControllers.add(this);
        if(httpSession==null){
            session.getBasicRemote().sendText("未登录，连接关闭");
            session.close();
        }
        User user= (User) httpSession.getAttribute("user");
        map.put(user.getId(),user.getName());
        wsControllerMap.put(user.getId(),this);
        count++;
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
       session.getAsyncRemote().sendText("未登录");
        error.printStackTrace();
    }

    @OnMessage
    public void onMessage(String message,Session session) throws IOException {
        User user= (User) httpSession.getAttribute("user");
        message=message.replaceAll("<","&lt;");
        message=message.replaceAll(">","&gt;");
        String[] context = message.split(":");
        int uid2=1;
        uid2=websocketService.isOne(message,user.getId());
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
        for (WsController item :wsControllers) {
            item.session.getBasicRemote().sendText(name+message);
        }
    }

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


package com.dev.controllers;


import com.dev.eventObjects.PlaceBidEvent;
import com.dev.models.MessageModel;
import com.dev.objects.User;
import com.dev.utils.Persist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;

import static com.dev.utils.Definitions.*;

@Controller
public class LiveUpdatesController {

    @Autowired
    private Persist persist;
    private List<SseEmitter> emitterList = new ArrayList<>();
    private Map<Integer, SseEmitter> emitterMap = new HashMap<>();
//
//    @PostConstruct
//    public void init(){
//        new Thread(()->{
//            while (true){
//                for (SseEmitter sseEmitter : emitterList){
//                    try {
//                        sseEmitter.send(new Date().toString());
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                }
//                try {
//                    Thread.sleep(15*SECOND);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }).start();
//    }


//    @RequestMapping(value = "/sse-handler", method = RequestMethod.GET)
//    public SseEmitter handle(String token, int recipientId) {
//        User user = persist.getUserByToken(token);
//        SseEmitter sseEmitter = null;
//        if (user != null) {
//            sseEmitter = new SseEmitter(10L * MINUTE);
//            String key = createKey(user.getId(), recipientId);
//            this.emitterMap.put(key, sseEmitter);
//        }
//        return sseEmitter;
//    }
    @RequestMapping(value = "/main-page-handler", method = RequestMethod.GET)
    public SseEmitter mainPageHandler(String token) {
        User user = persist.getUserByToken(token);
        SseEmitter sseEmitter = null;
        if (user != null) {
            sseEmitter = new SseEmitter(10L * MINUTE);
            this.emitterMap.put(user.getId(), sseEmitter);
        }
        return sseEmitter;
    }


    public void sendPlaceBidEvent(int sellerId , String bidderUsername) {
        PlaceBidEvent placeBidEvent = new PlaceBidEvent(EVENT_PLACE_BID,bidderUsername);
        SseEmitter conversationEmitter = this.emitterMap.get(sellerId);
        if (conversationEmitter != null) {
            try {
                //conversationEmitter.send(EVENT_PLACE_BID);
                conversationEmitter.send(placeBidEvent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private String createKey(int userSellerId, String type) {
        return String.format("%d_%d", userSellerId, type);
    }






    @RequestMapping(value = "/login-sse-handler", method = RequestMethod.GET)
    public SseEmitter loginSse() {
        SseEmitter sseEmitter = new SseEmitter(10L* MINUTE);
        emitterList.add(sseEmitter);
        return sseEmitter;
    }




    public void sendStartTypingEvent(int senderId, int recipientId) {
        String key = createKey(recipientId, "f");
        SseEmitter conversationEmitter = this.emitterMap.get(key);
        if (conversationEmitter != null) {
            try {
                conversationEmitter.send(EVENT_TYPING);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void sendConversationMessage(int senderId, int recipientId) {
        String key = createKey(recipientId, "f");
        SseEmitter conversationEmitter = this.emitterMap.get(key);
        if (conversationEmitter != null) {
            MessageModel messageModel = new MessageModel();
            messageModel.setContent("Content");
            messageModel.setSendDate("Date");
            try {
                conversationEmitter.send(messageModel);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

}

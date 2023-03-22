package com.dev.controllers;


import com.dev.eventObjects.CloseAuctionEvent;
import com.dev.eventObjects.PlaceBidEvent;
import com.dev.objects.User;
import com.dev.utils.Persist;
import com.dev.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.*;

import static com.dev.utils.Definitions.*;

@Controller
public class LiveUpdatesController {

    @Autowired
    private Persist persist;

    @Autowired
    private Utils utils;
    private final List<SseEmitter> loginEmitterList = new ArrayList<>();
    private final Map<Integer, SseEmitter> mainPageEmitterMap = new HashMap<>();



    @RequestMapping(value = "/main-page-handler", method = RequestMethod.GET)
    public SseEmitter mainPageHandler(String token) {
        User user = persist.getUserByToken(token);
        SseEmitter sseEmitter = null;
        if (user != null) {
            sseEmitter = new SseEmitter(15L * MINUTE);
            this.mainPageEmitterMap.put(user.getId(), sseEmitter);
        }
        return sseEmitter;
    }

    @RequestMapping(value = "/login-page-handler", method = RequestMethod.GET)
    public SseEmitter loginPageHandler() {
        SseEmitter sseEmitter = new SseEmitter(999L * MINUTE);
        this.loginEmitterList.add(sseEmitter);
        return sseEmitter;
    }

    public void sendStatsEvent(){
        for (SseEmitter sseEmitter : loginEmitterList){
            if (sseEmitter != null) {
                try {
                    sseEmitter.send(EVENT_STATS);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }


    public void sendPlaceBidEvent(int sellerId , String bidderUsername) {
        PlaceBidEvent placeBidEvent = new PlaceBidEvent(EVENT_PLACE_BID,bidderUsername);
        SseEmitter conversationEmitter = this.mainPageEmitterMap.get(sellerId);
        if (conversationEmitter != null) {
            try {
                conversationEmitter.send(placeBidEvent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void sendCloseAuctionEvent(String sellerUsername,  List<Integer> biddersId) {
        CloseAuctionEvent closeAuctionEvent = new CloseAuctionEvent(EVENT_CLOSE_AUCTION,sellerUsername);
        //List<Integer> biddersIdWithoutDuplicates = utils.removeUserIdDuplicates(biddersId);
            for (Integer bidderId : biddersId){
                SseEmitter conversationEmitter = this.mainPageEmitterMap.get(bidderId);
                if (conversationEmitter != null){
                    try {
                        conversationEmitter.send(closeAuctionEvent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
    }









}

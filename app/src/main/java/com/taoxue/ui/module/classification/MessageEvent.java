package com.taoxue.ui.module.classification;

/**
 * Created by User on 2017/4/13.
 */

public class MessageEvent {
    public  static class Duration{
        long curent;

        public Duration(long curent) {
            this.curent = curent;
        }

        public long getCurent() {
            return curent;
        }
    }

    public String message;
    public int code;

    public int getCode() {
        return code;
    }

    public MessageEvent setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public MessageEvent setMessage(String message) {
        this.message = message;
        return  this;
    }

    public MessageEvent() {
    }

    public MessageEvent(String message){
        this.message=message;
    }

    public static MessageEvent messageEvent;

    public static MessageEvent newInstance(){
        if (messageEvent==null){
            messageEvent=new MessageEvent();
            return  messageEvent;
        }
        return messageEvent;
    }
}

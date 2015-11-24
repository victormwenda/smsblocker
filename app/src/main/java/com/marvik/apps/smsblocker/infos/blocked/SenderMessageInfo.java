package com.marvik.apps.smsblocker.infos.blocked;

/**
 * Created by victor on 11/23/2015.
 */
public class SenderMessageInfo {

    private int messageId;
    private String messageText;
    private long messageReceiveTime;
    private long messageSendTime;
    private long systemTime;

    public SenderMessageInfo(int messageId, String messageText, long messageReceiveTime, long messageSendTime, long systemTime) {
        this.messageId = messageId;
        this.messageText = messageText;
        this.messageReceiveTime = messageReceiveTime;
        this.messageSendTime = messageSendTime;
        this.systemTime = systemTime;
    }

    public int getMessageId() {
        return messageId;
    }

    public String getMessageText() {
        return messageText;
    }

    public long getMessageReceiveTime() {
        return messageReceiveTime;
    }

    public long getMessageSendTime() {
        return messageSendTime;
    }

    public long getSystemTime() {
        return systemTime;
    }
}

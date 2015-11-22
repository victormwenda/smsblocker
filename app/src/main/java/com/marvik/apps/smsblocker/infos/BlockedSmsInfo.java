package com.marvik.apps.smsblocker.infos;

/**
 * Created by victor on 11/21/2015.
 */
public class BlockedSmsInfo {

    private int blockedSmsId;
    private String senderPhonenumber;
    private String messageText;
    private long messageSendTime;
    private long messageReceiveTime;
    private long systemTime;

    public BlockedSmsInfo(int blockedSmsId, String senderPhonenumber, String messageText, long messageSendTime, long messageReceiveTime, long systemTime) {
        this.blockedSmsId = blockedSmsId;
        this.senderPhonenumber = senderPhonenumber;
        this.messageText = messageText;
        this.messageSendTime = messageSendTime;
        this.messageReceiveTime = messageReceiveTime;
        this.systemTime = systemTime;
    }

    public int getBlockedSmsId() {
        return blockedSmsId;
    }

    public void setBlockedSmsId(int blockedSmsId) {
        this.blockedSmsId = blockedSmsId;
    }

    public String getSenderPhonenumber() {
        return senderPhonenumber;
    }

    public void setSenderPhonenumber(String senderPhonenumber) {
        this.senderPhonenumber = senderPhonenumber;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public long getMessageSendTime() {
        return messageSendTime;
    }

    public void setMessageSendTime(long messageSendTime) {
        this.messageSendTime = messageSendTime;
    }

    public long getMessageReceiveTime() {
        return messageReceiveTime;
    }

    public void setMessageReceiveTime(long messageReceiveTime) {
        this.messageReceiveTime = messageReceiveTime;
    }

    public long getSystemTime() {
        return systemTime;
    }

    public void setSystemTime(long systemTime) {
        this.systemTime = systemTime;
    }
}

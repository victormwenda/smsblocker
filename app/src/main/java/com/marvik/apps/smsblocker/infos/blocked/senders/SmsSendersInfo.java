package com.marvik.apps.smsblocker.infos.blocked.senders;

/**
 * Created by victor on 11/22/2015.
 */
public class SmsSendersInfo {

    private int messageSenderId;
    private String messageSenderAddress;
    private int blocked;
    private boolean isBlocked;
    private long blockedTime;


    public SmsSendersInfo(int messageSenderId, String messageSenderAddress, int blocked, long blockedTime) {
        this.messageSenderId = messageSenderId;
        this.messageSenderAddress = messageSenderAddress;
        this.blocked = blocked;
        this.blockedTime = blockedTime;

        setBlocked(blocked);
    }

    public int getMessageSenderId() {
        return messageSenderId;
    }

    public void setMessageSenderId(int messageSenderId) {
        this.messageSenderId = messageSenderId;
    }

    public String getMessageSenderAddress() {
        return messageSenderAddress;
    }

    public void setMessageSenderAddress(String messageSenderAddress) {
        this.messageSenderAddress = messageSenderAddress;
    }

    public int getBlocked() {
        return blocked;
    }

    private void setBlocked(int blocked) {
        if (blocked > 1) {
            blocked = 0;
        }
        this.blocked = blocked;

        setIsBlocked(blocked == 1 ? true : false);
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(boolean isBlocked) {
        this.isBlocked = isBlocked = (getBlocked() == 1 ? true : false);
    }

    public long getBlockedTime() {
        return blockedTime;
    }

    public void setBlockedTime(long blockedTime) {
        this.blockedTime = blockedTime;
    }


}

package com.marvik.apps.smsblocker.adapters.sms.sender;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.marvik.apps.smsblocker.R;
import com.marvik.apps.smsblocker.infos.blocked.SenderMessageInfo;
import com.marvik.apps.smsblocker.utils.Utils;

import java.util.List;

/**
 * Created by victor on 11/23/2015.
 */
public class SenderSmsAdapter extends BaseAdapter {

    private Utils utils;
    private Context mContext;
    private int layout;
    private List<SenderMessageInfo> mSenderMessageInfos;

    public SenderSmsAdapter(Context context, int layout, List<SenderMessageInfo> senderMessageInfos) {
        this.mContext = context;
        this.layout = layout;
        this.mSenderMessageInfos = senderMessageInfos;

        utils = new Utils(getContext());
    }

    public Context getContext() {
        return mContext;
    }

    public int getLayout() {
        return layout;
    }

    public List<SenderMessageInfo> getSenderMessageInfos() {
        return mSenderMessageInfos;
    }

    public Utils getUtils() {
        return utils;
    }

    @Override
    public int getCount() {
        return getSenderMessageInfos().size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View blockedSmsView = convertView;
        SenderMessagesViewHolder smsSendersViewHolder = null;

        if (blockedSmsView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            blockedSmsView = layoutInflater.inflate(getLayout(), parent, false);

            smsSendersViewHolder = new SenderMessagesViewHolder(blockedSmsView);
            blockedSmsView.setTag(smsSendersViewHolder);
        }

        smsSendersViewHolder = (SenderMessagesViewHolder) blockedSmsView.getTag();
        smsSendersViewHolder.populateUI(position);
        return blockedSmsView;
    }

    private class SenderMessagesViewHolder {

        private TextView mTvDate;
        private TextView mTvMessageText;
        private TextView mTvMessageSendTime;
        private TextView mTvMessageReceiveTime;


        public SenderMessagesViewHolder(View view) {

            mTvDate = (TextView) view.findViewById(R.id.list_sender_blocked_sms_tv_delivery_date);
            mTvMessageText = (TextView) view.findViewById(R.id.list_sender_blocked_sms_tv_message_text);
            mTvMessageSendTime = (TextView) view.findViewById(R.id.list_sender_blocked_sms_tv_send_time_value);
            mTvMessageReceiveTime = (TextView) view.findViewById(R.id.list_sender_blocked_sms_tv_receive_time_value);

        }

        public void populateUI(int position) {

            int messageId = getSenderMessageInfos().get(position).getMessageId();
            String messageText = getSenderMessageInfos().get(position).getMessageText();
            long messageReceiveTime = getSenderMessageInfos().get(position).getMessageReceiveTime();
            long messageSendTime = getSenderMessageInfos().get(position).getMessageSendTime();
            long systemTime = getSenderMessageInfos().get(position).getSystemTime();

            String date = getUtils().getUtilities().getHumanFriendlyFormattedTime(messageReceiveTime);
            String sendTime = getUtils().getUtilities().getFormattedTime("hh:mm a", messageSendTime);
            String receiveTime = getUtils().getUtilities().getFormattedTime("hh:mm a", messageReceiveTime);

            mTvDate.setText(date);
            mTvMessageText.setText(messageText);
            mTvMessageSendTime.setText(sendTime);
            mTvMessageReceiveTime.setText(receiveTime);

            if (position > 0) {
                long previousMessageReceiveTime = getSenderMessageInfos().get(position - 1).getMessageReceiveTime();
                String previousDate = getUtils().getUtilities().getHumanFriendlyFormattedTime(previousMessageReceiveTime);

                if (previousDate.equals(date)) {
                    mTvDate.setVisibility(TextView.GONE);
                } else {
                    mTvDate.setVisibility(TextView.VISIBLE);
                }
            }
        }
    }
}

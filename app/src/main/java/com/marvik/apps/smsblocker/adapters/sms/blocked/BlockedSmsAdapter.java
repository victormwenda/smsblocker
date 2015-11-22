package com.marvik.apps.smsblocker.adapters.sms.blocked;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.marvik.apps.smsblocker.R;
import com.marvik.apps.smsblocker.infos.blocked.sms.BlockedSmsInfo;
import com.marvik.apps.smsblocker.utils.Utils;

import java.util.List;

/**
 * Created by victor on 11/7/2015.
 */
public class BlockedSmsAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<BlockedSmsInfo> blockedSmses;
    private Utils utils;

    public BlockedSmsAdapter(Context context, int layout, List<BlockedSmsInfo> blockedSmses) {
        this.context = context;
        this.layout = layout;
        this.blockedSmses = blockedSmses;
        utils = new Utils(getContext());
    }

    public Context getContext() {
        return context;
    }

    public int getLayout() {
        return layout;
    }

    public List<BlockedSmsInfo> getBlockedSmses() {
        return blockedSmses;
    }

    public Utils getUtils() {
        return utils;
    }

    @Override
    public int getCount() {
        return getBlockedSmses().size();
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
        BlockedSmsViewHolder blockedSmsViewHolder = null;

        if (blockedSmsView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            blockedSmsView = layoutInflater.inflate(getLayout(), parent, false);

            blockedSmsViewHolder = new BlockedSmsViewHolder(blockedSmsView);
            blockedSmsView.setTag(blockedSmsViewHolder);
        }

        blockedSmsViewHolder = (BlockedSmsViewHolder) blockedSmsView.getTag();
        blockedSmsViewHolder.populateUI(position);
        return blockedSmsView;
    }

    private class BlockedSmsViewHolder {

        private TextView mTvDate;
        private ImageView mIvSenderAvatar;
        private TextView mTvSender;
        private TextView mTvReceiveTime;
        private TextView mTvBlockedSmsCount;
        private ImageView mIvMoreMessages;

        public BlockedSmsViewHolder(View view) {

            mTvDate = (TextView) view.findViewById(R.id.list_blocked_sms_all_tv_date);
            mIvSenderAvatar = (ImageView) view.findViewById(R.id.list_blocked_sms_all_iv_sender_avatar);
            mTvSender = (TextView) view.findViewById(R.id.list_blocked_sms_all_tv_sender);
            mTvBlockedSmsCount = (TextView) view.findViewById(R.id.list_blocked_sms_all_tv_sms_count);
            mTvReceiveTime = (TextView) view.findViewById(R.id.list_blocked_sms_all_tv_sms_delivery_time);
            mIvMoreMessages = (ImageView) view.findViewById(R.id.list_blocked_sms_all_iv_more);
        }

        public void populateUI(int position) {

            int blockedSmsId = getBlockedSmses().get(position).getBlockedSmsId();
            String senderPhonenumber = getBlockedSmses().get(position).getSenderPhonenumber();
            String messageText = getBlockedSmses().get(position).getMessageText();
            long messageSendTime = getBlockedSmses().get(position).getMessageSendTime();
            long messageReceiveTime = getBlockedSmses().get(position).getMessageReceiveTime();
            long systemTime = getBlockedSmses().get(position).getSystemTime();


            String date = getUtils().getUtilities().getHumanFriendlyFormattedTime(messageReceiveTime);
            String receiveTime = getUtils().getUtilities().getFormattedTime("hh:mm a", messageReceiveTime);
            String sender = getUtils().getHumanFriendlySenderName(senderPhonenumber);

            int senderMessages = getUtils().getTransactionsManager().getBlockedSmsSenderBlockedMessagesCount(senderPhonenumber);

            mTvDate.setText(date);
            mIvSenderAvatar.setImageBitmap(getUtils().getHumanFriendlySenderAvatar(senderPhonenumber));
            mTvSender.setText(sender);
            mTvReceiveTime.setText(receiveTime);
            mTvBlockedSmsCount.setText(senderMessages == 1 ? senderMessages + " Message" : senderMessages + " Messages");


            //UI - MODIFICATIONS
            //Messages
            if (senderMessages > 0) {
                mIvMoreMessages.setVisibility(ImageView.VISIBLE);
            } else { mIvMoreMessages.setVisibility(ImageView.GONE);}

            //Date
            if (position > 0) {

                long previousMessageReceiveTime = getBlockedSmses().get(position).getMessageReceiveTime();
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

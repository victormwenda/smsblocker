package com.marvik.apps.smsblocker.adapters.sms.senders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.marvik.apps.smsblocker.R;
import com.marvik.apps.smsblocker.infos.blocked.senders.SmsSendersInfo;
import com.marvik.apps.smsblocker.utils.Utils;

import java.util.List;

/**
 * Created by victor on 11/22/2015.
 */
public class SmsSendersAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<SmsSendersInfo> smsSendersInfos;
    private Utils utils;

    public SmsSendersAdapter(Context context, int layout, List<SmsSendersInfo> smsSendersInfos) {
        this.context = context;
        this.layout = layout;
        this.smsSendersInfos = smsSendersInfos;
        utils = new Utils(getContext());
    }

    public Context getContext() {
        return context;
    }

    public int getLayout() {
        return layout;
    }

    public List<SmsSendersInfo> getSmsSendersInfos() {
        return smsSendersInfos;
    }

    public Utils getUtils() {
        return utils;
    }

    @Override
    public int getCount() {
        return getSmsSendersInfos().size();
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
        SmsSendersViewHolder smsSendersViewHolder = null;

        if (blockedSmsView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            blockedSmsView = layoutInflater.inflate(getLayout(), parent, false);

            smsSendersViewHolder = new SmsSendersViewHolder(blockedSmsView);
            blockedSmsView.setTag(smsSendersViewHolder);
        }

        smsSendersViewHolder = (SmsSendersViewHolder) blockedSmsView.getTag();
        smsSendersViewHolder.populateUI(position);
        return blockedSmsView;
    }

    private class SmsSendersViewHolder {

        private TextView mTvSenderAddress;
        private ImageView mIvSenderAvatar;
        private TextView mTvSender;
        private TextView mTvBlockedStatus;


        public SmsSendersViewHolder(View view) {

            mIvSenderAvatar = (ImageView) view.findViewById(R.id.list_sms_senders_iv_sender_avatar);
            mTvSender = (TextView) view.findViewById(R.id.list_sms_senders_tv_sender);
            mTvSenderAddress = (TextView) view.findViewById(R.id.list_sms_senders_tv_sms_sender_address);
            mTvBlockedStatus = (TextView) view.findViewById(R.id.list_sms_senders_tv_blocked);

        }

        public void populateUI(int position) {

            int smsSenderId = getSmsSendersInfos().get(position).getMessageSenderId();
            String senderPhonenumber = getSmsSendersInfos().get(position).getMessageSenderAddress();
            boolean isBlocked = getSmsSendersInfos().get(position).isBlocked();
            int blocked = getSmsSendersInfos().get(position).getBlocked();
            long blockTime = getSmsSendersInfos().get(position).getBlockedTime();

            String sender = getUtils().getHumanFriendlySenderName(senderPhonenumber) == null ? senderPhonenumber : getUtils().getHumanFriendlySenderName(senderPhonenumber);

            int senderMessages = getUtils().getTransactionsManager().getBlockedSmsSenderBlockedMessagesCount(senderPhonenumber);


            mIvSenderAvatar.setImageBitmap(getUtils().getHumanFriendlySenderAvatar(senderPhonenumber));
            mTvSender.setText(sender);
            mTvSenderAddress.setText(senderPhonenumber);

            if (isBlocked) {
                mTvBlockedStatus.setText(getContext().getString(R.string.unblock));
                mTvBlockedStatus.setTextColor(getContext().getResources().getColor(android.R.color.holo_red_dark));
            } else {
                mTvBlockedStatus.setText(getContext().getString(R.string.block));
                mTvBlockedStatus.setTextColor(getContext().getResources().getColor(android.R.color.holo_green_dark));
            }
        }
    }
}

package com.marvik.apps.smsblocker.fragments.sendersms;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.marvik.apis.core.fragments.FragmentWrapper;
import com.marvik.apps.smsblocker.R;
import com.marvik.apps.smsblocker.adapters.sms.sender.SenderSmsAdapter;
import com.marvik.apps.smsblocker.constants.Constants;
import com.marvik.apps.smsblocker.infos.blocked.SenderMessageInfo;
import com.marvik.apps.smsblocker.intents.Intents;

import java.util.List;

/**
 * Created by victor on 11/23/2015.
 */
public class SenderMessagesFragment extends FragmentWrapper {

    private String senderAddress;
    private EditText mEtSearch;
    private ListView mLvSenderMessages;

    private View mSenderMessagesView;
    private List<SenderMessageInfo> senderMessageInfos;

    @Override
    public void onCreateFragment(@Nullable Bundle savedInstanceState) {
        getActivity().registerReceiver(receiver, new IntentFilter(Intents.ACTION_BLOCKED_MESSAGE_SAVED));
    }

    @Nullable
    @Override
    public String getActivityTitle() {
        return getActivity().getString(R.string.activity_title_blocked_sms);
    }

    @Override
    public void receiveBundle() {

        if (getArguments() == null) {
            return;
        }

        senderAddress = getArguments().getString(Constants.Intents.EXTRA_MESSAGE_SENDER_ADDRESS, getUtils().getUtilities().getActiveLine1Number());
    }

    @Nullable
    @Override
    public void onCreateFragmentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mSenderMessagesView = getActivity().getLayoutInflater().inflate(getParentLayout(), container, false);
        initChildViews(mSenderMessagesView);
        getContainer().addView(mSenderMessagesView);
    }

    @Override
    public void consumeBundle() {
        if (getArguments() != null)
            populateSenderMessages(senderAddress, mEtSearch.getText().toString());
    }

    @Override
    public void onAttachFragment() {

    }

    @Override
    public void onResumeFragment() {

    }


    @Override
    public void onPauseFragment() {

    }

    @Override
    public void onDestroyFragment() {
        getActivity().unregisterReceiver(receiver);
    }

    @Override
    public int getParentLayout() {
        return R.layout.fragment_sender_blocked_sms;
    }

    private void initChildViews(View view) {
        mEtSearch = (EditText) view.findViewById(R.id.fragment_sender_blocked_sms_et_search_messages);

        mLvSenderMessages = (ListView) view.findViewById(R.id.fragment_sender_blocked_sms_lv_messages);

        mEtSearch.addTextChangedListener(senderMessagesSearchTextWatcher);

        mLvSenderMessages.setOnItemClickListener(senderMessagesItemClickListener);
    }

    public List<SenderMessageInfo> getSenderMessageInfos() {
        return senderMessageInfos;
    }

    private void populateSenderMessages(String senderAddress, String searchKey) {
        senderMessageInfos = getUtils().getTransactionsManager().getSenderBlockedMessages(senderAddress, searchKey);
        mLvSenderMessages.setAdapter(new SenderSmsAdapter(getActivity(), R.layout.list_sender_blocked_sms, senderMessageInfos));
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intents.ACTION_BLOCKED_MESSAGE_SAVED)) {
                populateSenderMessages(senderAddress, mEtSearch.getText().toString());
            }
        }
    };
    private TextWatcher senderMessagesSearchTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            populateSenderMessages(senderAddress, mEtSearch.getText().toString());
        }
    };

    private AdapterView.OnItemClickListener senderMessagesItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (parent == mLvSenderMessages) {
                showDeleteMessageDialog(getSenderMessageInfos().get(position).getMessageId(), getSenderMessageInfos().get(position).getMessageText());
            }
        }
    };

    private void showDeleteMessageDialog(final int messageId, String messageText) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        alert.setTitle("Delete Message");
        alert.setMessage(messageText);
        alert.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getUtils().getTransactionsManager().deleteBlockedSms(messageId);
                getUtils().getUtilities().toast("Message Deleted!");
                populateSenderMessages(senderAddress, mEtSearch.getText().toString());
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getUtils().getUtilities().toast("Message Not Deleted");
            }
        });
        alert.create();
        alert.show();
    }
}

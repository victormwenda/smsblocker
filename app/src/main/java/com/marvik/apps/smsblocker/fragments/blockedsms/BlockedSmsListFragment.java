package com.marvik.apps.smsblocker.fragments.blockedsms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;

import com.marvik.apis.core.fragments.FragmentWrapper;
import com.marvik.apps.smsblocker.R;
import com.marvik.apps.smsblocker.adapters.sms.blocked.BlockedSmsAdapter;
import com.marvik.apps.smsblocker.infos.blocked.sms.BlockedSmsInfo;
import com.marvik.apps.smsblocker.intents.Intents;

import java.util.List;

/**
 * Created by victor on 11/7/2015.
 */
public class BlockedSmsListFragment extends FragmentWrapper {

    private ListView mLvBlockedSms;
    private MultiAutoCompleteTextView mEtSearchBlockedSms;
    private ImageView mIvCancelSearch;
    private ImageView mIvSearch;

    private OnBlockedSms onBlockedSms;

    private View mBlockedMessagesView;
    private List<BlockedSmsInfo> mBlockedSmsInfos;
    private AdapterView.OnItemClickListener blockedSmsListClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (parent == mLvBlockedSms) {
                String senderAddress = getBlockedSmsInfos().get(position).getSenderPhonenumber();
                onBlockedSms.showSenderBlockedMessages(senderAddress);
            }
        }
    };
    private TextWatcher blockedSmsSearchTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            populateBlockedMessages();
            if (mEtSearchBlockedSms.getText().toString().length() > 0) {
                mIvSearch.setVisibility(ImageView.GONE);
                mIvCancelSearch.setVisibility(ImageView.VISIBLE);
                mEtSearchBlockedSms.setPadding(mIvSearch.getWidth() / 2, 0, 0, 0);
            }
            if (mEtSearchBlockedSms.getText().toString().length() <= 0) {
                mIvSearch.setVisibility(ImageView.VISIBLE);
                mIvCancelSearch.setVisibility(ImageView.GONE);
                mEtSearchBlockedSms.setPadding(mIvSearch.getWidth() + 16, 0, 0, 0);

            }
        }
    };
    private View.OnClickListener blockedSmsSearchWizardClicksListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == mIvCancelSearch) {
                mEtSearchBlockedSms.setText("");
            }
            if (v == mIvSearch) {
                if (!mEtSearchBlockedSms.getText().equals("")) {
                    getUtils().getUtilities().toast("Showing " + getBlockedSmsInfos().size() + " search result(s) for \" " + mEtSearchBlockedSms.getText().toString() + " \" ");
                }
            }
        }
    };

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intents.ACTION_BLOCKED_MESSAGE_SAVED)) {
                populateBlockedMessages();
            }
        }
    };

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

    }

    @Nullable
    @Override
    public void onCreateFragmentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBlockedMessagesView = getActivity().getLayoutInflater().inflate(getParentLayout(), container, false);
        initChildViews(mBlockedMessagesView);
        getContainer().addView(mBlockedMessagesView);
    }

    @Override
    public void consumeBundle() {

    }

    @Override
    public void onAttachFragment() {
        onBlockedSms = (OnBlockedSms) getActivity();
    }

    @Override
    public void onResumeFragment() {
        populateBlockedMessages();
        mEtSearchBlockedSms.setText("");
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
        return R.layout.fragment_blocked_sms_all;
    }

    public List<BlockedSmsInfo> getBlockedSmsInfos() {
        return mBlockedSmsInfos;
    }

    private void initChildViews(View blockedMessagesView) {

        mLvBlockedSms = (ListView) blockedMessagesView.findViewById(R.id.fragment_blocked_sms_all_lv_blocked_sms);

        mIvCancelSearch = (ImageView) blockedMessagesView.findViewById(R.id.fragment_blocked_sms_all_iv_close_clear_cancel);

        mIvSearch = (ImageView) blockedMessagesView.findViewById(R.id.fragment_blocked_sms_all_iv_search);

        mEtSearchBlockedSms = (MultiAutoCompleteTextView) blockedMessagesView.findViewById(R.id.fragment_blocked_sms_all_mactv_search);
        mEtSearchBlockedSms.setText(getActivityTitle());

        mLvBlockedSms.setOnItemClickListener(blockedSmsListClickListener);

        mEtSearchBlockedSms.addTextChangedListener(blockedSmsSearchTextWatcher);

        mIvCancelSearch.setOnClickListener(blockedSmsSearchWizardClicksListener);

        mIvSearch.setOnClickListener(blockedSmsSearchWizardClicksListener);
    }

    private void populateBlockedMessages() {
        mBlockedSmsInfos = getUtils().getTransactionsManager().getBlockedSmsInfo(mEtSearchBlockedSms.getText().toString());
        mLvBlockedSms.setAdapter(new BlockedSmsAdapter(getActivity(), R.layout.list_blocked_sms_all, mBlockedSmsInfos));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_blocked_messages, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_blocked_messages_view_message_senders) {
            onBlockedSms.viewMessageSenders();
        }
        return super.onOptionsItemSelected(item);
    }

    public interface OnBlockedSms {

        void viewMessageSenders();

        void showSenderBlockedMessages(String senderAddress);
    }


}

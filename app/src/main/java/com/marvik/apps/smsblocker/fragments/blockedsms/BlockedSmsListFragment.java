package com.marvik.apps.smsblocker.fragments.blockedsms;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
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

import java.util.List;

/**
 * Created by victor on 11/7/2015.
 */
public class BlockedSmsListFragment extends FragmentWrapper {

    private ListView mLvBlockedSms;
    private MultiAutoCompleteTextView mEtSearchBlockedSms;
    private ImageView mIvCancelSearch;
    private ImageView mIvSearch;

    private View mBlockedMessagesView;
    private List<BlockedSmsInfo> mBlockedSmsInfos;
    private AdapterView.OnItemClickListener blockedSmsListClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (parent == mLvBlockedSms) {

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

    @Override
    public void onCreateFragment(@Nullable Bundle savedInstanceState) {

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

    }

    @Override
    public void onResumeFragment() {
        populateBlockedMessages();
    }

    @Override
    public void performPartialSync() {

    }

    @Override
    public void onPerformPartialSync() {

    }

    @Override
    public void onPauseFragment() {

    }

    @Override
    public void onDestroyFragment() {

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

        mEtSearchBlockedSms = (MultiAutoCompleteTextView) blockedMessagesView.findViewById(R.id.fragment_blocked_sms_all_mactv_search);

        mIvCancelSearch = (ImageView) blockedMessagesView.findViewById(R.id.fragment_blocked_sms_all_iv_close_clear_cancel);

        mIvSearch = (ImageView) blockedMessagesView.findViewById(R.id.fragment_blocked_sms_all_iv_search);

        mLvBlockedSms.setOnItemClickListener(blockedSmsListClickListener);

        mEtSearchBlockedSms.addTextChangedListener(blockedSmsSearchTextWatcher);

        mIvCancelSearch.setOnClickListener(blockedSmsSearchWizardClicksListener);

        mIvSearch.setOnClickListener(blockedSmsSearchWizardClicksListener);
    }

    private void populateBlockedMessages() {
        mBlockedSmsInfos = getUtils().getTransactionsManager().getBlockedSmsInfo(mEtSearchBlockedSms.getText().toString());
        mLvBlockedSms.setAdapter(new BlockedSmsAdapter(getActivity(), R.layout.list_blocked_sms_all, mBlockedSmsInfos));
    }

}

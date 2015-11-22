package com.marvik.apps.smsblocker.fragments.smssenders;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.marvik.apis.core.fragments.FragmentWrapper;
import com.marvik.apps.smsblocker.R;
import com.marvik.apps.smsblocker.adapters.sms.senders.SmsSendersAdapter;
import com.marvik.apps.smsblocker.infos.blocked.senders.SmsSendersInfo;

import java.util.List;

/**
 * Created by victor on 11/21/2015.
 */
public class SmsSendersFragment extends FragmentWrapper {

    private EditText mEtSearchSender;
    private ListView mLvSmsSenders;
    private ImageView mIvAddBlockedSmsSender;

    private View mSmsSendersView;
    private List<SmsSendersInfo> smsSendersInfo;

    private OnSmsSender onSmsSender;
    private int lastKnownScrollYPosition;
    private View.OnClickListener addBlockedSmsSenderClickLister = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onSmsSender.onRequestPickContact();
        }
    };
    private AdapterView.OnItemClickListener smsSenderListClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (parent == mLvSmsSenders) {
                setLastKnownScrollYPosition(mLvSmsSenders.getScrollY());
                String address = getSmsSendersInfo().get(position).getMessageSenderAddress();
                boolean blocked = getSmsSendersInfo().get(position).isBlocked();
                long blockTime = getSmsSendersInfo().get(position).getBlockedTime();
                getUtils().getTransactionsManager().saveMessageSender(address, !blocked, blockTime);
                populateSmsSenders();
                mLvSmsSenders.setScrollY(getLastKnownScrollYPosition());
            }


        }
    };
    private TextWatcher smsSenderTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

            populateSmsSenders();
        }
    };

    @Override
    public void onCreateFragment(@Nullable Bundle savedInstanceState) {

    }

    @Nullable
    @Override
    public String getActivityTitle() {
        return getActivity().getString(R.string.activity_title_blocked_numbers);
    }

    @Override
    public void receiveBundle() {

    }

    @Nullable
    @Override
    public void onCreateFragmentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mSmsSendersView = getActivity().getLayoutInflater().inflate(getParentLayout(), container, false);
        initChildViews(mSmsSendersView);
        getContainer().addView(mSmsSendersView);

    }

    @Override
    public void consumeBundle() {

    }

    @Override
    public void onAttachFragment() {
        onSmsSender = (OnSmsSender) getActivity();
    }

    @Override
    public void onResumeFragment() {

        populateSmsSenders();

        if (smsSendersInfo.size() > 0) {
            //mLvSmsSenders.scrollListBy(getLastKnownScrollYPosition());
        }
    }

    private int getLastKnownScrollYPosition() {
        return lastKnownScrollYPosition;
    }

    public void setLastKnownScrollYPosition(int lastKnownScrollYPosition) {
        this.lastKnownScrollYPosition = lastKnownScrollYPosition;
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
        return R.layout.fragment_sms_senders;
    }

    public List<SmsSendersInfo> getSmsSendersInfo() {
        return smsSendersInfo;
    }

    private void initChildViews(View smsSendersView) {
        mEtSearchSender = (EditText) smsSendersView.findViewById(R.id.fragment_sms_senders_editText_search_sms_sender);

        mLvSmsSenders = (ListView) smsSendersView.findViewById(R.id.fragment_sms_senders_listView_sms_senders);

        mIvAddBlockedSmsSender = (ImageView) smsSendersView.findViewById(R.id.fragment_sms_senders_imageView_add_blocked_sms_sender);

        mEtSearchSender.addTextChangedListener(smsSenderTextWatcher);

        mLvSmsSenders.setOnItemClickListener(smsSenderListClickListener);

        mIvAddBlockedSmsSender.setOnClickListener(addBlockedSmsSenderClickLister);
    }

    private void populateSmsSenders() {
        smsSendersInfo = getUtils().getTransactionsManager().getSmsSendersInfo(mEtSearchSender.getText().toString());
        mLvSmsSenders.setAdapter(new SmsSendersAdapter(getActivity(), R.layout.list_sms_senders, smsSendersInfo));
    }

    public interface OnSmsSender {
        void onRequestPickContact();
    }

}

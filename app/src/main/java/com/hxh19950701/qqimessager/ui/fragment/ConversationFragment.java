package com.hxh19950701.qqimessager.ui.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.hxh19950701.qqimessager.R;
import com.hxh19950701.qqimessager.adapter.ConversationListAdapter;
import com.hxh19950701.qqimessager.base.BaseFragment;
import com.hxh19950701.qqimessager.dao.ConversationDao;
import com.hxh19950701.qqimessager.dao.SimpleQueryHandler;
import com.hxh19950701.qqimessager.ui.activity.ConversationDetailActivity;
import com.hxh19950701.qqimessager.ui.activity.NewConversationActivity;
import com.hxh19950701.qqimessager.ui.activity.SearchActivity;

/**
 * Created by hxh19950701 on 2016/4/4.
 */
public class ConversationFragment extends BaseFragment {

    public static final int DELETE_THIS_CONVERSATION = 0;
    public static final int DELETE_THIS_CONTACT = 1;
    public static final int DELETE_THIS_CONVERSATION_AND_CONTACT = 2;

    private ListView lv_conversation_list = null;
    private RelativeLayout rl_conversation_new = null;
    private RelativeLayout rl_conversation_search = null;

    private ConversationListAdapter conversationListAdapter = null;

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_conversation, null);
        lv_conversation_list = (ListView) view.findViewById(R.id.lv_conversation_list);
        rl_conversation_new = (RelativeLayout) view.findViewById(R.id.rl_conversation_new);
        rl_conversation_search = (RelativeLayout) view.findViewById(R.id.rl_conversation_search);
        return view;
    }

    @Override
    public void initListener() {
        rl_conversation_new.setOnClickListener(this);
        rl_conversation_search.setOnClickListener(this);

        lv_conversation_list.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        startConversationDetailActivity(position);
                    }
                }
        );

        lv_conversation_list.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        showConversationAlertDialog(position);
                        return true;
                    }
                }
        );
    }

    @Override
    public void initData() {
        SimpleQueryHandler queryHandler = new SimpleQueryHandler(getActivity().getContentResolver());
        String[] projection = new String[]{
                "body AS snippet", "thread_id AS _id", "msg_count AS msg_count", "address AS address", "date AS date"};
        conversationListAdapter = new ConversationListAdapter(getActivity(), null);
        lv_conversation_list.setAdapter(conversationListAdapter);
        queryHandler.startQuery(0, conversationListAdapter, Uri.parse("content://sms/conversations"), projection, null, null, "date desc");
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()) {
            case R.id.rl_conversation_search:
                startActivity(new Intent(getContext(), SearchActivity.class));
                break;
            case R.id.rl_conversation_new:
                startActivity(new Intent(getContext(), NewConversationActivity.class));
                break;
        }
    }

    protected void startConversationDetailActivity(int pos) {
        Intent intent = new Intent(getActivity(), ConversationDetailActivity.class);
        Cursor cursor = (Cursor) conversationListAdapter.getItem(pos);
        intent.putExtra("address", cursor.getString(cursor.getColumnIndex("address")));
        startActivity(intent);
    }

    protected void showConversationAlertDialog(int pos) {
        Cursor cursor = (Cursor) conversationListAdapter.getItem(pos);
        int threadId = cursor.getInt(cursor.getColumnIndex("_id"));
        AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                .setTitle(R.string.conversation_option)
                .setItems(R.array.conversation_option_item, new OnConversationOptionItemClickListener(threadId))
                .create();
        alertDialog.show();
    }

    protected class OnConversationOptionItemClickListener implements DialogInterface.OnClickListener {
        protected int threadId;
        protected int operation;

        public OnConversationOptionItemClickListener(int threadId) {
            this.threadId = threadId;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            operation = which;
            AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                    .setTitle(R.string.conversation_option_delete_dialog_title)
                    .setMessage(R.string.conversation_option_delete_dialog_message)
                    .setPositiveButton(R.string._continue, new DeleteListener())
                    .setNegativeButton(R.string.cancel, null)
                    .create();
            alertDialog.show();
        }

        protected class DeleteListener implements DialogInterface.OnClickListener{
            public void onClick(DialogInterface dialog, int which) {
                switch (operation) {
                    case DELETE_THIS_CONVERSATION:
                        ConversationDao.deleteConversation(getActivity().getContentResolver(), threadId);
                        break;
                    case DELETE_THIS_CONTACT:
                        break;
                    case DELETE_THIS_CONVERSATION_AND_CONTACT:
                        break;
                }
            }
        }
    }
}
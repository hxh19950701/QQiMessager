package com.hxh19950701.qqimessager.ui.activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hxh19950701.qqimessager.R;
import com.hxh19950701.qqimessager.adapter.ConversationDetailAdapter;
import com.hxh19950701.qqimessager.base.BaseActivity;
import com.hxh19950701.qqimessager.dao.ContactDao;
import com.hxh19950701.qqimessager.dao.SimpleQueryHandler;

public class ConversationDetailActivity extends BaseActivity {

    public static final int NO_SMS_ID = -1;

    private ConversationDetailAdapter conversationDetailAdapter = null;

    private RelativeLayout rl_total_title = null;
    private TextView tv_titlebar_title = null;
    private TextView tv_titlebar_title_detail = null;
    private ListView lv_conversation_detail = null;
    private EditText et_conversation_detail_input = null;
    private ImageView iv_conversation_detail_send = null;

    @Override
    public void initView() {
        setContentView(R.layout.activity_conversation_detail);
        rl_total_title = (RelativeLayout) findViewById(R.id.rl_total_title);
        tv_titlebar_title = (TextView) findViewById(R.id.tv_titlebar_title);
        tv_titlebar_title_detail = (TextView) findViewById(R.id.tv_titlebar_title_detail);
        lv_conversation_detail = (ListView) findViewById(R.id.lv_conversation_detail);
        et_conversation_detail_input = (EditText) findViewById(R.id.et_conversation_detail_input);
        iv_conversation_detail_send = (ImageView) findViewById(R.id.iv_conversation_detail_send);
    }

    @Override
    public void initListener() {
        rl_total_title.setOnClickListener(this);
        iv_conversation_detail_send.setOnClickListener(this);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        String address = intent.getStringExtra("address");
        int smsId = intent.getIntExtra("_id", NO_SMS_ID);
        if (address != null) {
            setTitleBar(address);
            setConversationDetailList(address, smsId);
        }
    }

    @Override
    public void processClick(View view) {
        switch (view.getId()) {
            case R.id.rl_total_title:
                finish();
                break;
            case R.id.iv_conversation_detail_send:
                break;
        }
    }

    protected void setTitleBar(String address) {
        Cursor cursor = getContentResolver().query(Uri.parse("content://sms/conversations"),
                new String[]{"msg_count AS msg_count"}, new String("address=?"), new String[]{address}, null);
        if (cursor != null && cursor.moveToFirst()) {
            String name = ContactDao.getNameByAddress(getContentResolver(), address);
            int count = cursor.getInt(cursor.getColumnIndex("msg_count"));
            tv_titlebar_title.setText(TextUtils.isEmpty(name) ? address : name);
            tv_titlebar_title_detail.setText(String.format(getString(R.string.title_detail), count));
            cursor.close();
        } else {
            tv_titlebar_title.setText(address);
            tv_titlebar_title_detail.setText(String.format(getString(R.string.title_detail), 0));
        }
    }

    protected void setConversationDetailList(String address, int smsId) {
        Cursor cursor = getContentResolver().query(Uri.parse("content://sms/conversations"),
                new String[]{"thread_id AS thread_id"}, new String("address=?"), new String[]{address}, null);
        if (cursor != null && cursor.moveToFirst()) {
            SimpleQueryHandler queryHandler = new SimpleQueryHandler(getContentResolver());
            String[] projection = new String[]{"_id AS _id", "body AS body", "type AS type", "date AS date"};
            String thread_id = cursor.getString(cursor.getColumnIndex("thread_id"));
            conversationDetailAdapter = new ConversationDetailAdapter(this, null);
            lv_conversation_detail.setAdapter(conversationDetailAdapter);
            queryHandler.startQuery(smsId, lv_conversation_detail, Uri.parse("content://sms"),
                    projection, new String("thread_id=?"), new String[]{thread_id}, "date");
        }
    }

}
package com.hxh19950701.qqimessager.ui.activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.hxh19950701.qqimessager.R;
import com.hxh19950701.qqimessager.adapter.KeywordSearchListAdapter;
import com.hxh19950701.qqimessager.base.BaseActivity;
import com.hxh19950701.qqimessager.dao.SimpleQueryHandler;

/**
 * Created by hxh19950701 on 2016/4/5.
 */
public class SearchActivity extends BaseActivity {

    private LinearLayout ll_titlebar_cancel = null;
    private EditText et_search_keyword = null;
    private ListView lv_search_result = null;

    private KeywordSearchListAdapter keywordSearchListAdapter = null;
    private SimpleQueryHandler queryHandler = null;

    private static final String[] PROJECTION = new String[]{
            "body AS body", "thread_id AS thread_id", "_id AS _id", "address AS address", "date AS date"};

    @Override
    public void initView() {
        setContentView(R.layout.activity_search);
        ll_titlebar_cancel = (LinearLayout) findViewById(R.id.ll_titlebar_cancel);
        lv_search_result = (ListView) findViewById(R.id.lv_search_result);
        et_search_keyword = (EditText) findViewById(R.id.et_search_keyword);
    }

    @Override
    public void initListener() {
        ll_titlebar_cancel.setOnClickListener(this);
        et_search_keyword.addTextChangedListener(
                new TextWatcher() {
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        searchContactAndMessage(s.toString());
                    }

                    public void afterTextChanged(Editable s) {

                    }
                }
        );
        lv_search_result.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        startConversationDetailActivity(position);
                        finish();
                    }
                }
        );
    }

    @Override
    public void initData() {
        keywordSearchListAdapter = new KeywordSearchListAdapter(this, null);
        lv_search_result.setAdapter(keywordSearchListAdapter);
        queryHandler = new SimpleQueryHandler(getContentResolver());
    }

    @Override
    public void processClick(View view) {
        switch (view.getId()) {
            case R.id.ll_titlebar_cancel:
                finish();
                break;
        }
    }

    protected void searchContactAndMessage(String keyword) {
        if (TextUtils.isEmpty(keyword)) {
            keywordSearchListAdapter.changeCursor(null);
        } else {
            String selection = "body LIKE '%" + keyword + "%' OR address LIKE '%" + keyword + "%'";
            queryHandler.startQuery(0, keywordSearchListAdapter, Uri.parse("content://sms"),
                    PROJECTION, selection, null, "date desc");
        }
    }

    protected void startConversationDetailActivity(int pos) {
        Intent intent = new Intent(this, ConversationDetailActivity.class);
        Cursor cursor = (Cursor) keywordSearchListAdapter.getItem(pos);
        intent.putExtra("address", cursor.getString(cursor.getColumnIndex("address")));
        intent.putExtra("_id", cursor.getInt(cursor.getColumnIndex("_id")));
        startActivity(intent);
    }
}
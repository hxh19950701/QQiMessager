package com.hxh19950701.qqimessager.dao;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.database.Cursor;
import android.widget.Adapter;
import android.widget.ListView;

import com.hxh19950701.qqimessager.adapter.ContactListAdapter;
import com.hxh19950701.qqimessager.adapter.ConversationDetailAdapter;
import com.hxh19950701.qqimessager.adapter.ConversationListAdapter;
import com.hxh19950701.qqimessager.adapter.KeywordSearchListAdapter;
import com.hxh19950701.qqimessager.ui.activity.ConversationDetailActivity;


public class SimpleQueryHandler extends AsyncQueryHandler {
    public SimpleQueryHandler(ContentResolver cr) {
        super(cr);
    }

    protected void moveToId(ListView listView, Cursor cursor, int _id) {
        if (_id == ConversationDetailActivity.NO_SMS_ID) {
            listView.setSelection(cursor.getCount() - 1);
        } else {
            while (cursor.moveToNext()) {
                if (cursor.getInt(cursor.getColumnIndex("_id")) == _id) {
                    break;
                }
            }
            listView.setSelection(cursor.getPosition());
        }
    }

    @Override
    protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
        if (cookie != null) {
            if (cookie instanceof ConversationListAdapter) {
                ((ConversationListAdapter) cookie).changeCursor(cursor);
            } else if (cookie instanceof KeywordSearchListAdapter) {
                ((KeywordSearchListAdapter) cookie).changeCursor(cursor);
            } else if (cookie instanceof ListView) {
                ListView listView = (ListView) cookie;
                Adapter adapter = listView.getAdapter();
                if (adapter instanceof ConversationDetailAdapter) {
                    ConversationDetailAdapter conversationListAdapter = (ConversationDetailAdapter) adapter;
                    conversationListAdapter.changeCursor(cursor);
                    moveToId(listView, cursor, token);
                }
            }else if(cookie instanceof ContactListAdapter){
                ((ContactListAdapter) cookie).changeCursor(cursor);
            }
        }
    }
}
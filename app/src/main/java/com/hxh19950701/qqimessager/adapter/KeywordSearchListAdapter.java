package com.hxh19950701.qqimessager.adapter;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.view.View;

import com.hxh19950701.qqimessager.dao.ContactDao;

/**
 * Created by hxh19950701 on 2016/4/7.
 */
public class KeywordSearchListAdapter extends ConversationListAdapter {

    public KeywordSearchListAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    protected void setConversationAddress(Context context, ConversationViewHolder holder, Cursor cursor) {
        String address = cursor.getString(cursor.getColumnIndex("address"));
        String name = ContactDao.getNameByAddress(context.getContentResolver(), address);
        holder.tv_conversation_address.setText(TextUtils.isEmpty(name) ? address : name);
    }

    @Override
    protected void setConversationBody(ConversationViewHolder holder, Cursor cursor) {
        String body = cursor.getString(cursor.getColumnIndex("body"));
        holder.tv_conversation_body.setText(body);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ConversationViewHolder viewHolder = getViewHolder(view);
        setConversationAddress(context, viewHolder, cursor);
        setConversationBody(viewHolder, cursor);
        setConversationDate(viewHolder, cursor);
        setConversationImage(context, viewHolder, cursor);
    }
}

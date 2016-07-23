package com.hxh19950701.qqimessager.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxh19950701.qqimessager.R;
import com.hxh19950701.qqimessager.dao.ContactDao;

import java.text.SimpleDateFormat;

public class ConversationListAdapter extends CursorAdapter {

    public ConversationListAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return View.inflate(context, R.layout.item_conversation_list, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ConversationViewHolder viewHolder = getViewHolder(view);
        setConversationAddress(context, viewHolder, cursor);
        setConversationBody(viewHolder, cursor);
        setConversationDate(viewHolder, cursor);
        setConversationImage(context, viewHolder, cursor);
    }

    protected void setConversationAddress(Context context, ConversationViewHolder holder, Cursor cursor) {
        String address = cursor.getString(cursor.getColumnIndex("address"));
        String msgCount = cursor.getString(cursor.getColumnIndex("msg_count"));
        String name = ContactDao.getNameByAddress(context.getContentResolver(), address);
        holder.tv_conversation_address.setText((TextUtils.isEmpty(name) ? address : name) + " (" + msgCount + ")");
    }

    protected void setConversationBody(ConversationViewHolder holder, Cursor cursor) {
        String body = cursor.getString(cursor.getColumnIndex("snippet"));
        holder.tv_conversation_body.setText(body);
    }

    protected void setConversationDate(ConversationViewHolder holder, Cursor cursor) {
        long date = cursor.getLong(cursor.getColumnIndex("date"));
        SimpleDateFormat sdf = new SimpleDateFormat(DateUtils.isToday(date) ? "HH:mm:ss" : "yyyy/MM/dd");
        holder.tv_conversation_date.setText(sdf.format(date));
    }

    protected void setConversationImage(Context context, ConversationViewHolder holder, Cursor cursor) {
        String address = cursor.getString(cursor.getColumnIndex("address"));
        Bitmap avatar = ContactDao.getAvatarByAddress(context.getContentResolver(), address);
        if (avatar != null) {
            holder.iv_conversation_avatar.setImageBitmap(avatar);
        } else {
            holder.iv_conversation_avatar.setBackgroundResource(R.mipmap.img_default_avatar);
        }
    }

    public ConversationViewHolder getViewHolder(View view) {
        ConversationViewHolder viewHolder = (ConversationViewHolder) view.getTag();
        if (viewHolder == null) {
            viewHolder = new ConversationViewHolder(view);
            view.setTag(viewHolder);
        }
        return viewHolder;
    }

    public class ConversationViewHolder {
        public ImageView iv_conversation_avatar = null;
        public TextView tv_conversation_address = null;
        public TextView tv_conversation_body = null;
        public TextView tv_conversation_date = null;

        public ConversationViewHolder(View view) {
            iv_conversation_avatar = (ImageView) view.findViewById(R.id.iv_conversation_avatar);
            tv_conversation_address = (TextView) view.findViewById(R.id.tv_conversation_address);
            tv_conversation_body = (TextView) view.findViewById(R.id.tv_conversation_body);
            tv_conversation_date = (TextView) view.findViewById(R.id.tv_conversation_date);
        }
    }
}


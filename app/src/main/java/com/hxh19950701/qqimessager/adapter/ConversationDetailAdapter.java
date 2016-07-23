package com.hxh19950701.qqimessager.adapter;

import android.content.Context;
import android.database.Cursor;
import android.text.format.DateUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.hxh19950701.qqimessager.R;

import java.text.SimpleDateFormat;

public class ConversationDetailAdapter extends CursorAdapter {

    public static final int RECEIVE = 1;
    public static final int SEND = 2;
    public static final int DURATION = 3 * 60 * 1000;

    public ConversationDetailAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return View.inflate(context, R.layout.item_conversation_detail, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        SmsViewHolder smsViewHolder = getViewHolder(view);
        setSmsDate(smsViewHolder, cursor);
        setSmsReceive(smsViewHolder, cursor);
        setSmsSend(smsViewHolder, cursor);
    }

    private SmsViewHolder getViewHolder(View view) {
        SmsViewHolder smsViewHolder = (SmsViewHolder) view.getTag();
        if (smsViewHolder == null) {
            smsViewHolder = new SmsViewHolder(view);
            view.setTag(smsViewHolder);
        }
        return smsViewHolder;
    }

    private void setSmsDate(SmsViewHolder holder, Cursor cursor) {
        int pos = cursor.getPosition();
        long date = cursor.getLong(cursor.getColumnIndex("date"));
        if (pos == 0) {
            setDateTextViewVisible(holder, date);
        } else {
            Cursor preCursor = (Cursor) getItem(pos - 1);
            long preDate = preCursor.getLong(preCursor.getColumnIndex("date"));
            cursor.moveToPosition(pos);
            if (date - preDate < DURATION) {
                holder.tv_sms_date.setVisibility(View.GONE);
            } else {
                setDateTextViewVisible(holder, date);
            }
        }
    }

    private void setDateTextViewVisible(SmsViewHolder holder, long date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DateUtils.isToday(date) ? "hh:mm:ss" : "yyyy/MM/dd");
        holder.tv_sms_date.setText(sdf.format(date));
        holder.tv_sms_date.setVisibility(View.VISIBLE);
    }

    private void setSmsReceive(SmsViewHolder holder, Cursor cursor) {
        if (cursor.getInt(cursor.getColumnIndex("type")) == RECEIVE) {
            holder.tv_sms_receive.setVisibility(View.VISIBLE);
            holder.tv_sms_receive.setText(cursor.getString(cursor.getColumnIndex("body")));
        } else {
            holder.tv_sms_receive.setVisibility(View.GONE);
        }
    }

    private void setSmsSend(SmsViewHolder holder, Cursor cursor) {
        if (cursor.getInt(cursor.getColumnIndex("type")) == SEND) {
            holder.tv_sms_send.setVisibility(View.VISIBLE);
            holder.tv_sms_send.setText(cursor.getString(cursor.getColumnIndex("body")));
        } else {
            holder.tv_sms_send.setVisibility(View.GONE);
        }
    }

    public static class SmsViewHolder {
        public final TextView tv_sms_date;
        public final TextView tv_sms_receive;
        public final TextView tv_sms_send;

        public SmsViewHolder(View view) {
            tv_sms_date = (TextView) view.findViewById(R.id.tv_sms_date);
            tv_sms_receive = (TextView) view.findViewById(R.id.tv_sms_receive);
            tv_sms_send = (TextView) view.findViewById(R.id.tv_sms_send);
        }
    }

}
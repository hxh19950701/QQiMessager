package com.hxh19950701.qqimessager.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxh19950701.qqimessager.R;
import com.hxh19950701.qqimessager.dao.ContactDao;
import com.hxh19950701.qqimessager.utils.GetFirstLetter;

public class ContactListAdapter extends CursorAdapter {
    public ContactListAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = View.inflate(context, R.layout.item_contact_list, null);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = getViewHolder(view);
        setContactAvatar(context, viewHolder, cursor);
        setContactNameAndAddress(context, viewHolder, cursor);
        setContactHead(context, viewHolder, cursor);
    }

    protected void setContactHead(Context context, ViewHolder holder, Cursor cursor) {
        int pos = cursor.getPosition();
        String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
        char head = GetFirstLetter.getFirstLetter(name).charAt(0);
        if (pos == 0) {
            holder.tv_contact_head.setVisibility(View.VISIBLE);
            holder.tv_contact_head.setText(String.valueOf(head));
        } else {
            Cursor preCursor = (Cursor) getItem(pos - 1);
            String preName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            char preHead = GetFirstLetter.getFirstLetter(preName).charAt(0);
            cursor.moveToPosition(pos);
            if (head == preHead) {
                holder.tv_contact_head.setVisibility(View.GONE);
            } else {
                holder.tv_contact_head.setVisibility(View.VISIBLE);
                holder.tv_contact_head.setText(String.valueOf(head));
            }
        }
    }

    protected void setContactNameAndAddress(Context context, ViewHolder holder, Cursor cursor) {
        String address = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA1));
        String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
        holder.tv_contact_name.setText(TextUtils.isEmpty(name) ? address : name);
        holder.tv_contact_address.setText(address);
    }

    protected void setContactAvatar(Context context, ViewHolder holder, Cursor cursor) {
        String address = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA1));
        Bitmap avatar = ContactDao.getAvatarByAddress(context.getContentResolver(), address);
        if (avatar != null) {
            holder.iv_contact_avatar.setImageBitmap(avatar);
        } else {
            holder.iv_contact_avatar.setBackgroundResource(R.mipmap.img_default_avatar);
        }
    }

    protected ViewHolder getViewHolder(View v) {
        ViewHolder holder = (ViewHolder) v.getTag();
        if (holder == null) {
            holder = new ViewHolder(v);
            v.setTag(holder);
        }
        return holder;
    }

    public class ViewHolder {
        public final TextView tv_contact_head;
        public final ImageView iv_contact_avatar;
        public final TextView tv_contact_name;
        public final TextView tv_contact_address;
        public final View root;

        public ViewHolder(View root) {
            tv_contact_head = (TextView) root.findViewById(R.id.tv_contact_head);
            iv_contact_avatar = (ImageView) root.findViewById(R.id.iv_contact_avatar);
            tv_contact_name = (TextView) root.findViewById(R.id.tv_contact_name);
            tv_contact_address = (TextView) root.findViewById(R.id.tv_contact_address);
            this.root = root;
        }
    }
}
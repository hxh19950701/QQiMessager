package com.hxh19950701.qqimessager.dao;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;

import java.io.InputStream;

public class ContactDao {
    public static String getNameByAddress(ContentResolver contentResolver, String address) {
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, address);
        Cursor cursor = contentResolver.query(uri, new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null, null);
        String name = null;
        if (cursor != null && cursor.moveToFirst()) {
            name = cursor.getString(0);
            cursor.close();
        }
        return name;
    }

    public static Bitmap getAvatarByAddress(ContentResolver contentResolver, String address) {
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, address);
        Cursor cursor = contentResolver.query(uri, new String[]{ContactsContract.PhoneLookup._ID}, null, null, null);
        Bitmap avatar = null;
        if (cursor != null && cursor.moveToFirst()) {
            String _id = cursor.getString(0);
            InputStream inputStream = ContactsContract.Contacts.openContactPhotoInputStream
                    (contentResolver, Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, _id));
            avatar = BitmapFactory.decodeStream(inputStream);
            cursor.close();
        }
        return avatar;
    }

//    public static int deleteContactByAddress(ContentResolver contentResolver, String address){
//        Cursor contactsCur = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
//        while(contactsCur.moveToNext()){
//            //获取ID
//            String rawId = contactsCur.getString(contactsCur.getColumnIndex(ContactsContract.Contacts._ID));
//            //删除
//            String where = ContactsContract.Data._ID  + " =?";
//            String[] whereparams = new String[]{rawId};
//            contentResolver.delete(ContactsContract.RawContacts.CONTENT_URI, where, whereparams);
//        }
//    }
}
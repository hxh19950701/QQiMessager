package com.hxh19950701.qqimessager.dao;

import android.content.ContentResolver;
import android.net.Uri;

/**
 * Created by hxh19950701 on 2016/4/22.
 */
public class ConversationDao {
    public static int deleteConversation(ContentResolver contentResolver, int threadId){
        return contentResolver.delete(Uri.parse("content://sms"),"thread_id=?",new String[]{String.valueOf(threadId)});
    }
}
package com.hxh19950701.qqimessager.ui.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hxh19950701.qqimessager.R;
import com.hxh19950701.qqimessager.adapter.ContactListAdapter;
import com.hxh19950701.qqimessager.base.BaseFragment;
import com.hxh19950701.qqimessager.dao.SimpleQueryHandler;

public class ContactFragment extends BaseFragment {
    private ListView contactList = null;
    protected ContactListAdapter contactListAdapter = null;

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, null);
        contactList = (ListView) view.findViewById(R.id.lv_contact_list);
        return view;
    }

    @Override
    public void initListener() {
        contactList.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                }
        );
    }

    @Override
    public void initData() {
        SimpleQueryHandler queryHandler = new SimpleQueryHandler(getActivity().getContentResolver());
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection = {ContactsContract.CommonDataKinds.Phone._ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.DATA1};
        contactListAdapter = new ContactListAdapter(getActivity(), null);
        contactList.setAdapter(contactListAdapter);
        queryHandler.startQuery(0, contactListAdapter, uri, projection, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
    }

    @Override
    public void processClick(View v) {
        System.out.println();
    }
}
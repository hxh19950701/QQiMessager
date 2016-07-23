package com.hxh19950701.qqimessager.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.hxh19950701.qqimessager.R;
import com.hxh19950701.qqimessager.adapter.MainViewPagerAdapter;
import com.hxh19950701.qqimessager.base.BaseActivity;
import com.hxh19950701.qqimessager.ui.fragment.ContactFragment;
import com.hxh19950701.qqimessager.ui.fragment.ConversationFragment;
import com.hxh19950701.qqimessager.ui.fragment.PluginFragment;

public class MainActivity extends BaseActivity {

    public static final int TAB_COUNT = 3;
    public static final int TAB_CONVERSATION = 0;
    public static final int TAB_CONTACT = 1;
    public static final int TAB_PLUGIN = 2;

    private RelativeLayout rl_tab_conversation = null;
    private RelativeLayout rl_tab_contact = null;
    private RelativeLayout rl_tab_plugin = null;

    private ImageView iv_tab_conversation = null;
    private ImageView iv_tab_contact = null;
    private ImageView iv_tab_plugin = null;

    private ViewPager viewPager = null;
    private Fragment[] fragments = null;
    private View indicate_line = null;

    @Override
    public void initView() {
        setContentView(R.layout.activity_main);

        rl_tab_conversation = (RelativeLayout) findViewById(R.id.rl_tab_conversation);
        rl_tab_contact = (RelativeLayout) findViewById(R.id.rl_tab_contact);
        rl_tab_plugin = (RelativeLayout) findViewById(R.id.rl_tab_plugin);

        iv_tab_conversation = (ImageView) findViewById(R.id.iv_tab_conversation);
        iv_tab_contact = (ImageView) findViewById(R.id.iv_tab_contact);
        iv_tab_plugin = (ImageView) findViewById(R.id.iv_tab_plugin);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        indicate_line = findViewById(R.id.indicate_line);
    }

    @Override
    public void initListener() {
        rl_tab_conversation.setOnClickListener(this);
        rl_tab_contact.setOnClickListener(this);
        rl_tab_plugin.setOnClickListener(this);

        viewPager.addOnPageChangeListener(
                new ViewPager.OnPageChangeListener() {
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                        indicate_line.setTranslationX(positionOffsetPixels / TAB_COUNT + position * indicate_line.getWidth());
                    }

                    public void onPageSelected(int position) {
                        refreshTopBar();
                    }

                    public void onPageScrollStateChanged(int state) {

                    }
                });
    }

    @Override
    public void initData() {
        initIndicateLineWidth();
        initFragmentList();
        viewPager.setAdapter(new MainViewPagerAdapter(getSupportFragmentManager(), fragments));
        refreshTopBar();
    }

    @Override
    public void processClick(View view) {
        switch (view.getId()){
            case R.id.rl_tab_conversation:
                viewPager.setCurrentItem(TAB_CONVERSATION);
                break;
            case R.id.rl_tab_contact:
                viewPager.setCurrentItem(TAB_CONTACT);
                break;
            case R.id.rl_tab_plugin:
                viewPager.setCurrentItem(TAB_PLUGIN);
                break;
        }
    }

    private void initIndicateLineWidth() {
        int screenWidth = getWindowManager().getDefaultDisplay().getWidth();
        indicate_line.getLayoutParams().width = screenWidth / TAB_COUNT;
    }

    private void refreshTopBar() {
        int currentItem = viewPager.getCurrentItem();
        iv_tab_conversation.setBackgroundResource(currentItem == TAB_CONVERSATION
                ? R.mipmap.skin_tab_icon_conversation_selected : R.mipmap.skin_tab_icon_conversation_normal);
        iv_tab_contact.setBackgroundResource(currentItem == TAB_CONTACT
                ? R.mipmap.skin_tab_icon_contact_selected : R.mipmap.skin_tab_icon_contact_normal);
        iv_tab_plugin.setBackgroundResource(currentItem == TAB_PLUGIN
                ? R.mipmap.skin_tab_icon_plugin_selected : R.mipmap.skin_tab_icon_plugin_normal);
    }

    private void initFragmentList() {
        fragments = new Fragment[TAB_COUNT];
        fragments[0] = new ConversationFragment();
        fragments[1] = new ContactFragment();
        fragments[2] = new PluginFragment();
    }
}
package com.pc.mimi.ui.connect;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.xlyuns.xunmi.BR;
import com.xlyuns.xunmi.R;
import com.pc.mimi.adapter.FragmentPageAdapter;
import com.pc.mimi.app.BaseLazyFragment;
import com.pc.mimi.config.CommonTags;
import com.xlyuns.xunmi.databinding.FragmentConnectBinding;

import java.util.ArrayList;
import java.util.List;

public class ConnectFragment extends BaseLazyFragment<FragmentConnectBinding, ConnectViewModel> {
    List<Fragment> mFragmentList;
    List<String> mTitles;
    FragmentPageAdapter mFragmentPageAdapter;

    @Override
    public void onFirstUserInvisible() {

    }

    @Override
    public void onUserVisible() {

    }

    @Override
    public void onUserInvisible() {

    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_connect;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        initFragments();
        iniiTitles();
        initTabLayout();
    }

    private void initTabLayout() {
        binding.tab.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorAccent));
        binding.tab.setTabTextColors(getResources().getColor(R.color.text_222222), getResources().getColor(R.color.text_F5922F));
        binding.tab.addTab(binding.tab.newTab().setText(mTitles.get(0)));
        binding.tab.addTab(binding.tab.newTab().setText(mTitles.get(1)));
        binding.tab.addTab(binding.tab.newTab().setText(mTitles.get(2)));
        binding.tab.addTab(binding.tab.newTab().setText(mTitles.get(3)));

        mFragmentPageAdapter = new FragmentPageAdapter(getChildFragmentManager(), mFragmentList, mTitles);
        binding.tab.setupWithViewPager(binding.pager);
        binding.pager.setAdapter(mFragmentPageAdapter);
        binding.tab.setTabsFromPagerAdapter(mFragmentPageAdapter);//给Tabs设置适配器
        binding.pager.setCurrentItem(0);
        binding.pager.setOffscreenPageLimit(4);
    }

    private void initFragments() {
        if (null == mFragmentList) {
            mFragmentList = new ArrayList<Fragment>() {
                {
                    add(RadioFragment.newInstance(CommonTags.RADIO_TAB_TYPE.RADIO_TAB_ALL));
                    add(RadioFragment.newInstance(CommonTags.RADIO_TAB_TYPE.RADIO_TAB_HOT));
                    add(RadioFragment.newInstance(CommonTags.RADIO_TAB_TYPE.RADIO_TAB_NORMAL));
                    add(RadioFragment.newInstance(CommonTags.RADIO_TAB_TYPE.RADIO_TAB_NEARBY));
                }
            };
        }
    }

    private void iniiTitles() {
        if (mTitles == null) {
            mTitles = new ArrayList<String>() {
                {
                    for (String str : CommonTags.RADIO_TAB) {
                        add(str);
                    }
                }
            };
        }
    }
}

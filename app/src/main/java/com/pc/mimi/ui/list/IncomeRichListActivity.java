package com.pc.mimi.ui.list;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.xlyuns.xunmi.BR;
import com.xlyuns.xunmi.R;
import com.pc.mimi.adapter.FragmentPageAdapter;
import com.pc.mimi.app.SwipeBackActivity;
import com.pc.mimi.config.CommonTags;
import com.xlyuns.xunmi.databinding.ActivityIncomeRichListBinding;
import com.pc.mimi.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;

public class IncomeRichListActivity extends SwipeBackActivity<ActivityIncomeRichListBinding, IncomeRichListViewModel> {
    List<Fragment> mFragmentList;
    List<String> mTitles;
    FragmentPageAdapter mFragmentPageAdapter;


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_income_rich_list;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        this.setTitle("排行榜");
        CommonUtil.initTitleBar(this);
        initFragments();
        iniiTitles();
        initTabLayout();
    }

    private void initTabLayout() {
        binding.tab.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorAccent));
        binding.tab.setTabTextColors(getResources().getColor(R.color.text_222222), getResources().getColor(R.color.text_F5922F));
        binding.tab.addTab(binding.tab.newTab().setText(mTitles.get(0)));
        binding.tab.addTab(binding.tab.newTab().setText(mTitles.get(1)));

        mFragmentPageAdapter = new FragmentPageAdapter(getSupportFragmentManager(), mFragmentList, mTitles);
        binding.tab.setupWithViewPager(binding.pager);
        binding.pager.setAdapter(mFragmentPageAdapter);
        binding.tab.setTabsFromPagerAdapter(mFragmentPageAdapter);//给Tabs设置适配器
        binding.pager.setCurrentItem(0);
    }

    private void initFragments() {
        if (null == mFragmentList) {
            mFragmentList = new ArrayList<Fragment>() {
                {
                    add(IncomeRichListFragment.newInstance(CommonTags.LIST_TYPE.IN_COME_LIST));
                    add(IncomeRichListFragment.newInstance(CommonTags.LIST_TYPE.RICH_LIST));
                }
            };
        }
    }

    private void iniiTitles() {
        if (mTitles == null) {
            mTitles = new ArrayList<String>() {
                {
                    for (String str : CommonTags.LIST_TYPE.LIST_TITLE) {
                        add(str);
                    }
                }
            };
        }
    }
}

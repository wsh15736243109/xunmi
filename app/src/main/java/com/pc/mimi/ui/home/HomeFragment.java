package com.pc.mimi.ui.home;

import android.databinding.Observable;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.pc.mimi.adapter.AreaSelectAdapter;
import com.pc.mimi.bean.AreaBean;
import com.pc.mimi.util.AppUtils;
import com.pc.mimi.util.inject.FindView;
import com.pc.mimi.util.inject.Init;
import com.pc.mimi.util.inject.OnClick;
import com.pc.mimi.util.inject.ViewUtils;
import com.pc.mimi.widget.BackgroundDarkPopupWindow;
import com.xlyuns.xunmi.BR;
import com.xlyuns.xunmi.R;
import com.pc.mimi.adapter.FragmentPageAdapter;
import com.pc.mimi.app.BaseLazyFragment;
import com.pc.mimi.config.CommonTags;
import com.xlyuns.xunmi.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.utils.KLog;

public class HomeFragment extends BaseLazyFragment<FragmentHomeBinding, HomeViewModel> {
    List<Fragment> mFragmentList;
    List<String> mTitles;
    FragmentPageAdapter mFragmentPageAdapter;
    BackgroundDarkPopupWindow mSelectNearByPop;
    SelectCityEvent mSelectCityEvent;

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
    public void initViewObservable() {
        viewModel.uc.nearBy.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                View inflateView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_select_city, null);
                mSelectCityEvent = new SelectCityEvent();
                ViewUtils.inject(inflateView, mSelectCityEvent);
                mSelectNearByPop = new BackgroundDarkPopupWindow(inflateView, WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.WRAP_CONTENT);
                mSelectNearByPop.setFocusable(true);
                mSelectNearByPop.setBackgroundDrawable(new ColorDrawable());
                mSelectNearByPop.setDarkStyle(R.style.DarkAnimation);
                mSelectNearByPop.setDarkColor(Color.parseColor("#a0000000"));//颜色
                mSelectNearByPop.setOutsideTouchable(true);
                mSelectNearByPop.darkBelow(binding.line);
                mSelectNearByPop.drakFillView(binding.root);
                mSelectNearByPop.showAsDropDown(binding.line);
                viewModel.getCityList();
            }
        });
        viewModel.uc.firstSelect.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                mSelectCityEvent.setDefaultSelect();
            }
        });
    }

    //选择城市的弹窗中事件
    private class SelectCityEvent {
        @FindView(R.id.rv_city)
        RecyclerView mRvCiry;
        @FindView(R.id.rv_area)
        RecyclerView mRvArea;
        @FindView(R.id.area_select)
        TextView mAreaSelect;
        String mSelectId = "";

        @Init
        private void initViews() {
            //城市
            viewModel.mCityBeans = new ArrayList<>();
            viewModel.mCitySelectAdapter = new AreaSelectAdapter(viewModel.mCityBeans);
            initRecycler(mRvCiry, viewModel.mCitySelectAdapter);
            viewModel.mCitySelectAdapter.setOnItemClickListener((adapter, view, position) -> {
                KLog.d("--------------position-------" + position);
                //清除区域全部选中
                if (viewModel.mAreaBeans.size() > 0) {
                    for (AreaBean areaBean : viewModel.mAreaBeans) {
                        areaBean.setSelected(false);
                    }
                    viewModel.mAreaSelectAdapter.notifyDataSetChanged();
                }
                //设置本选项选中
                if (viewModel.mCityBeans.size() > 0) {
                    for (AreaBean areaBean : viewModel.mCityBeans) {
                        areaBean.setSelected(false);
                    }
                    mAreaSelect.setText(viewModel.mCityBeans.get(position).getCityname());
                    mSelectId = viewModel.mCityBeans.get(position).getCity_id();
                    viewModel.mCityBeans.get(position).setSelected(true);
                    viewModel.mCitySelectAdapter.notifyDataSetChanged();
                }
                viewModel.loadAreaList(viewModel.mCityBeans.get(position).getCity_id());
            });
            //区域
            viewModel.mAreaBeans = new ArrayList<>();
            viewModel.mAreaSelectAdapter = new AreaSelectAdapter(viewModel.mAreaBeans);
            initRecycler(mRvArea, viewModel.mAreaSelectAdapter);
            viewModel.mAreaSelectAdapter.setOnItemClickListener(((adapter, view, position) -> {
                //清除城市全部选中
                if (viewModel.mCityBeans.size() > 0) {
                    for (AreaBean areaBean : viewModel.mCityBeans) {
                        areaBean.setSelected(false);
                    }
                    viewModel.mCitySelectAdapter.notifyDataSetChanged();
                }
                //设置本选项选中
                if (viewModel.mAreaBeans.size() > 0) {
                    for (AreaBean areaBean : viewModel.mAreaBeans) {
                        areaBean.setSelected(false);
                    }
                    mAreaSelect.setText(viewModel.mAreaBeans.get(position).getCityname());
                    mSelectId = viewModel.mAreaBeans.get(position).getCity_id();
                    viewModel.mAreaBeans.get(position).setSelected(true);
                    viewModel.mAreaSelectAdapter.notifyDataSetChanged();
                }
            }));
        }

        @OnClick({R.id.confir})
        private void onClicked(View view) {
            switch (view.getId()) {
                case R.id.confir:
                    mSelectNearByPop.dismiss();
                    Messenger.getDefault().send(mAreaSelect.getText().toString(), CommonTags.SELECT_CITY);
                    break;
            }
        }

        private void initRecycler(RecyclerView rvCiry, AreaSelectAdapter citySelectAdapter) {
            rvCiry.setLayoutManager(new LinearLayoutManager(getActivity()));
            citySelectAdapter.bindToRecyclerView(rvCiry);
        }

        //设置默认选中
        public void setDefaultSelect() {
            mAreaSelect.setText(viewModel.mCityBeans.get(0).getCityname());
        }
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_home;
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
        if (AppUtils.getUser().getGender() == 0) {//女
            binding.tab.addTab(binding.tab.newTab().setText(mTitles.get(0)));
            binding.tab.addTab(binding.tab.newTab().setText(mTitles.get(1)));
        } else {
            binding.tab.addTab(binding.tab.newTab().setText(mTitles.get(0)));
            binding.tab.addTab(binding.tab.newTab().setText(mTitles.get(1)));
            binding.tab.addTab(binding.tab.newTab().setText(mTitles.get(2)));

        }

        mFragmentPageAdapter = new FragmentPageAdapter(getChildFragmentManager(), mFragmentList, mTitles);
        binding.pager.setOffscreenPageLimit(3);
        binding.tab.setupWithViewPager(binding.pager);
        binding.pager.setAdapter(mFragmentPageAdapter);
        binding.tab.setTabsFromPagerAdapter(mFragmentPageAdapter);//给Tabs设置适配器
        binding.pager.setCurrentItem(0);
    }

    private void initFragments() {
        if (null == mFragmentList) {
            if (AppUtils.getUser().getGender() == 0) {//女
                mFragmentList = new ArrayList<Fragment>() {
                    {
                        add(HomeListFragment.newInstance(CommonTags.HOME_PAGE_TYPE.NEARBY));
                        add(HomeListFragment.newInstance(CommonTags.HOME_PAGE_TYPE.VIP));
                    }
                };
            } else {
                mFragmentList = new ArrayList<Fragment>() {
                    {
                        add(HomeListFragment.newInstance(CommonTags.HOME_PAGE_TYPE.NEARBY));
                        add(HomeListFragment.newInstance(CommonTags.HOME_PAGE_TYPE.NEW_REG));
                        add(HomeListFragment.newInstance(CommonTags.HOME_PAGE_TYPE.AUTH));
                    }
                };
            }
        }
    }

    private void iniiTitles() {
        if (mTitles == null) {
            if (AppUtils.getUser().getGender() == 0) {//女
                mTitles = new ArrayList<String>() {
                    {
                        for (String str : CommonTags.HOME_TAB_G) {
                            add(str);
                        }
                    }
                };
            } else {
                mTitles = new ArrayList<String>() {
                    {
                        for (String str : CommonTags.HOME_TAB_B) {
                            add(str);
                        }
                    }
                };
            }

        }
    }
}

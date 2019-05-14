package com.pc.mimi.ui.edituserinfo;

import android.databinding.Observable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.xlyuns.xunmi.BR;
import com.xlyuns.xunmi.R;
import com.pc.mimi.adapter.AreaSelectAdapter;
import com.pc.mimi.app.SwipeBackActivity;
import com.xlyuns.xunmi.databinding.ActivityMettingAreaSelectBinding;
import com.pc.mimi.util.CommonUtil;

import java.util.ArrayList;

import me.goldze.mvvmhabit.utils.KLog;

public class MettingAreaSelectActivity extends SwipeBackActivity<ActivityMettingAreaSelectBinding, MettingAreaSelectViewModel> {


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_metting_area_select;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        this.setTitle("约会范围");
        CommonUtil.initTitleBar(this);
        initRecycler();
        viewModel.getCityList();
    }

    private void initRecycler() {
        //城市
        viewModel.mCityBeans = new ArrayList<>();
        viewModel.mCitySelectAdapter = new AreaSelectAdapter(viewModel.mCityBeans);
        binding.rvCity.setLayoutManager(new LinearLayoutManager(this));
        viewModel.mCitySelectAdapter.bindToRecyclerView(binding.rvCity);
        viewModel.mCitySelectAdapter.setOnItemClickListener((adapter, view, position) -> {
            KLog.d("--------------position-------" + position);
            viewModel.mCityBeans.get(position).setSelected(!viewModel.mCityBeans.get(position).isSelected());
            viewModel.mCitySelectAdapter.notifyDataSetChanged();
            viewModel.loadAreaList(viewModel.mCityBeans.get(position).getCity_id());
            viewModel.updateDataSelected();
        });
        //区域
        viewModel.mAreaBeans = new ArrayList<>();
        viewModel.mAreaSelectAdapter = new AreaSelectAdapter(viewModel.mAreaBeans);
        binding.rvArea.setLayoutManager(new LinearLayoutManager(this));
        viewModel.mAreaSelectAdapter.bindToRecyclerView(binding.rvArea);
        viewModel.mAreaSelectAdapter.setOnItemClickListener(((adapter, view, position) -> {
            viewModel.mAreaBeans.get(position).setSelected(!viewModel.mAreaBeans.get(position).isSelected());
            viewModel.mAreaSelectAdapter.notifyDataSetChanged();
            viewModel.updateDataSelected();
        }));
    }

    @Override
    public void initViewObservable() {
        viewModel.uc.labels.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                binding.flow.setLabels(viewModel.mLabelsDatas);
            }
        });
    }
}

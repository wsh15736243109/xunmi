package com.pc.mimi.ui.edituserinfo;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.xlyuns.xunmi.BR;
import com.xlyuns.xunmi.R;
import com.pc.mimi.adapter.JobSelectAdapter;
import com.pc.mimi.app.SwipeBackActivity;
import com.pc.mimi.bean.JobBean;
import com.xlyuns.xunmi.databinding.ActivityJobSelectBinding;
import com.pc.mimi.util.CommonUtil;

import java.util.ArrayList;

public class JobSelectActivity extends SwipeBackActivity<ActivityJobSelectBinding, JobSelectViewModel> {


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_job_select;
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
        viewModel.getJobList();
    }

    private void initRecycler() {
        //one
        viewModel.mJobOneBeans = new ArrayList<>();
        viewModel.mJobSelectOneAdapter = new JobSelectAdapter(viewModel.mJobOneBeans);
        binding.rvOne.setLayoutManager(new LinearLayoutManager(this));
        viewModel.mJobSelectOneAdapter.bindToRecyclerView(binding.rvOne);
        viewModel.mJobSelectOneAdapter.setOnItemClickListener((adapter, view, position) -> {
            for (JobBean jobBean : viewModel.mJobOneBeans) {
                jobBean.setSelected(false);
            }
            viewModel.jobText.set(viewModel.mJobOneBeans.get(position).getIndustry_name());
            viewModel.mJobOneBeans.get(position).setSelected(true);
            viewModel.mJobSelectOneAdapter.notifyDataSetChanged();
            viewModel.getJobList(2, viewModel.mJobOneBeans.get(position).getIndustry_id());
        });
        //two
        viewModel.mJobTwoBeans = new ArrayList<>();
        viewModel.mJobSelectTwoAdapter = new JobSelectAdapter(viewModel.mJobTwoBeans);
        binding.rvTwo.setLayoutManager(new LinearLayoutManager(this));
        viewModel.mJobSelectTwoAdapter.bindToRecyclerView(binding.rvTwo);
        viewModel.mJobSelectTwoAdapter.setOnItemClickListener(((adapter, view, position) -> {
            for (JobBean jobBean : viewModel.mJobTwoBeans) {
                jobBean.setSelected(false);
            }
            viewModel.jobText.set(viewModel.mJobTwoBeans.get(position).getIndustry_name());
            viewModel.mJobTwoBeans.get(position).setSelected(true);
            viewModel.mJobSelectTwoAdapter.notifyDataSetChanged();
            viewModel.getJobList(3, viewModel.mJobTwoBeans.get(position).getIndustry_id());
        }));
        //three
        viewModel.mJobThreeBeans = new ArrayList<>();
        viewModel.mJobSelectThreeAdapter = new JobSelectAdapter(viewModel.mJobThreeBeans);
        binding.rvThree.setLayoutManager(new LinearLayoutManager(this));
        viewModel.mJobSelectThreeAdapter.bindToRecyclerView(binding.rvThree);
        viewModel.mJobSelectThreeAdapter.setOnItemClickListener(((adapter, view, position) -> {
            for (JobBean jobBean : viewModel.mJobThreeBeans) {
                jobBean.setSelected(false);
            }
            viewModel.jobText.set(viewModel.mJobThreeBeans.get(position).getIndustry_name());
            viewModel.mJobThreeBeans.get(position).setSelected(true);
            viewModel.mJobSelectThreeAdapter.notifyDataSetChanged();
        }));
    }
}

package com.pc.mimi.ui.mine;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.xlyuns.xunmi.BR;
import com.xlyuns.xunmi.R;
import com.pc.mimi.adapter.SignUpAcountListAdapter;
import com.pc.mimi.app.SwipeBackActivity;
import com.xlyuns.xunmi.databinding.ActivitySignUpAcountBinding;
import com.pc.mimi.util.CommonUtil;

import java.util.ArrayList;

public class SignUpAcountActivity extends SwipeBackActivity<ActivitySignUpAcountBinding, SignUpAcountViewModel> {


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_sign_up_acount;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        this.setTitle("报名人员");
        CommonUtil.initTitleBar(this);
        viewModel.all_dating_id = getIntent().getExtras().getString("all_dating_id", "");
        initRecycler();
        viewModel.requestSignUpAccountList();
    }


    private void initRecycler() {
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));
        viewModel.mSignUpAccountListBeans = new ArrayList<>();
        viewModel.mSignUpAcountListAdapter = new SignUpAcountListAdapter(viewModel.mSignUpAccountListBeans);
        viewModel.mSignUpAcountListAdapter.bindToRecyclerView(binding.recycler);
        viewModel.mSignUpAcountListAdapter.setOnItemChildClickListener(((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.connect_her://联系她 TODO

                    break;
            }
        }));
    }
}

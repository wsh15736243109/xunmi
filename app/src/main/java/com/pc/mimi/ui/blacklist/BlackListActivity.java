package com.pc.mimi.ui.blacklist;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.xlyuns.xunmi.BR;
import com.xlyuns.xunmi.R;
import com.pc.mimi.adapter.PersonalInfoListAdapter;
import com.pc.mimi.app.SwipeBackActivity;
import com.xlyuns.xunmi.databinding.ActivityBlackListBinding;
import com.pc.mimi.ui.userdetail.UserDetailActivity;
import com.pc.mimi.util.CommonUtil;

import java.util.ArrayList;

public class BlackListActivity extends SwipeBackActivity<ActivityBlackListBinding, BlackListViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_black_list;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        this.setTitle("黑名单");
        CommonUtil.initTitleBar(this);
        initRecycler();
    }

    private void initRecycler() {
        viewModel.personBeanList = new ArrayList<>();
        viewModel.mPersonInfoListAdapter = new PersonalInfoListAdapter(viewModel.personBeanList);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));
        viewModel.mPersonInfoListAdapter.bindToRecyclerView(binding.recycler);
        viewModel.requestBlackPeopleListData();

        viewModel.mPersonInfoListAdapter.setOnItemChildClickListener(((adapter, view, position) -> {

            Bundle bundle = new Bundle();
            bundle.putString("other_user_id",  viewModel.personBeanList.get(position).getUser_id());

            startActivity(UserDetailActivity.class,bundle);
        }));
    }
}

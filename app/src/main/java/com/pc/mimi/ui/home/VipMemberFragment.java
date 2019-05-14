package com.pc.mimi.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.xlyuns.xunmi.BR;
import com.xlyuns.xunmi.R;
import com.pc.mimi.adapter.PersonalInfoListAdapter;
import com.xlyuns.xunmi.databinding.FragmentVipMemberBinding;

import java.util.ArrayList;

import com.pc.mimi.ui.userdetail.UserDetailActivity;
import me.goldze.mvvmhabit.base.BaseFragment;
@Deprecated
public class VipMemberFragment extends BaseFragment<FragmentVipMemberBinding, HomeContentViewModel> {

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_vip_member;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        viewModel.personBeanList = new ArrayList<>();
        viewModel.mPersonInfoListAdapter = new PersonalInfoListAdapter(viewModel.personBeanList);
        binding.recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        viewModel.mPersonInfoListAdapter.bindToRecyclerView(binding.recycler);
        viewModel.requestVipPersonListData();

        viewModel.mPersonInfoListAdapter.setOnItemChildClickListener(((adapter, view, position) -> {

            Bundle bundle = new Bundle();
            bundle.putString("other_user_id",  viewModel.personBeanList.get(position).getUser_id());

            startActivity(UserDetailActivity.class,bundle);
        }));
    }
}

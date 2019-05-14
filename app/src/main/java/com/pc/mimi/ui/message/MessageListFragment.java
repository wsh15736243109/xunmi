package com.pc.mimi.ui.message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.xlyuns.xunmi.BR;
import com.xlyuns.xunmi.R;
import com.pc.mimi.adapter.MessageListAdapter;
import com.xlyuns.xunmi.databinding.FragmentMessageListBinding;
import com.pc.mimi.ui.chat.ChatActivity;

import java.util.ArrayList;

import me.goldze.mvvmhabit.base.BaseFragment;

public class MessageListFragment extends BaseFragment<FragmentMessageListBinding, MessageListViewModel> {

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_message_list;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        viewModel.mMessageListBeans = new ArrayList<>();
        binding.recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        viewModel.mPersonInfoListAdapter = new MessageListAdapter(viewModel.mMessageListBeans);
        viewModel.mPersonInfoListAdapter.bindToRecyclerView(binding.recycler);
        viewModel.mPersonInfoListAdapter.setOnItemClickListener(((adapter, view, position) -> {
            Bundle bundle = new Bundle();
            bundle.putString("user_id", viewModel.mMessageListBeans.get(position).getOut_user_id());
            bundle.putString("user_name", viewModel.mMessageListBeans.get(position).getOut_user_nick_name());
            startActivity(ChatActivity.class, bundle);
        }));
        viewModel.requestMessageList();
    }
}

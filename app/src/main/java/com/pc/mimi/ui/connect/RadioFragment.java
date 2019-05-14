package com.pc.mimi.ui.connect;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.xlyuns.xunmi.BR;
import com.xlyuns.xunmi.R;
import com.pc.mimi.adapter.PersonalConnListAdapter;
import com.pc.mimi.app.BaseLazyFragment;
import com.pc.mimi.config.CommonTags;
import com.xlyuns.xunmi.databinding.FragmentRadioBinding;
import com.pc.mimi.ui.chat.ChatActivity;

import java.util.ArrayList;

public class RadioFragment extends BaseLazyFragment<FragmentRadioBinding, RadioViewModel> {


    public static RadioFragment newInstance(String tag) {
        Bundle args = new Bundle();
        args.putString("tag", tag);
        RadioFragment fragment = new RadioFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initData() {
        switch (getArguments().getString("tag")) {
            case CommonTags.RADIO_TAB_TYPE.RADIO_TAB_ALL:
                viewModel.mRadio_AllBeans = new ArrayList<>();
                binding.recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
                viewModel.mPersonalConnList_AllAdapter = new PersonalConnListAdapter(viewModel.mRadio_AllBeans);
                viewModel.mPersonalConnList_AllAdapter.bindToRecyclerView(binding.recycler);
                viewModel.mPersonalConnList_AllAdapter.setOnItemChildClickListener(((adapter, view, position) -> {
                    Bundle bundle = new Bundle();
                    bundle.putString("user_id", viewModel.mRadio_AllBeans.get(position).getUser_id());
                    bundle.putString("user_name", viewModel.mRadio_AllBeans.get(position).getNick_name());
                    startActivity(ChatActivity.class,bundle);
                }));
                viewModel.requestList(CommonTags.RADIO_TAB_TYPE.RADIO_TAB_ALL);
                break;
            case CommonTags.RADIO_TAB_TYPE.RADIO_TAB_HOT:
                viewModel.mRadio_HOTBeans = new ArrayList<>();
                binding.recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
                viewModel.mPersonalConnList_HOTAdapter = new PersonalConnListAdapter(viewModel.mRadio_HOTBeans);
                viewModel.mPersonalConnList_HOTAdapter.bindToRecyclerView(binding.recycler);
                viewModel.requestList(CommonTags.RADIO_TAB_TYPE.RADIO_TAB_HOT);
                break;
            case CommonTags.RADIO_TAB_TYPE.RADIO_TAB_NORMAL:
                viewModel.mRadio_NORMALBeans = new ArrayList<>();
                binding.recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
                viewModel.mPersonalConnList_NORMALAdapter = new PersonalConnListAdapter(viewModel.mRadio_NORMALBeans);
                viewModel.mPersonalConnList_NORMALAdapter.bindToRecyclerView(binding.recycler);
                viewModel.requestList(CommonTags.RADIO_TAB_TYPE.RADIO_TAB_NORMAL);
                break;
            case CommonTags.RADIO_TAB_TYPE.RADIO_TAB_NEARBY:
                viewModel.mRadio_NEARBYBeans = new ArrayList<>();
                binding.recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
                viewModel.mPersonalConnList_NEARBYAdapter = new PersonalConnListAdapter(viewModel.mRadio_NEARBYBeans);
                viewModel.mPersonalConnList_NEARBYAdapter.bindToRecyclerView(binding.recycler);
                viewModel.requestList(CommonTags.RADIO_TAB_TYPE.RADIO_TAB_NEARBY);
                break;
        }

    }

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
        return R.layout.fragment_radio;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }
}

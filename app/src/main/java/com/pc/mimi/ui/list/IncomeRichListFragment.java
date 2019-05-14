package com.pc.mimi.ui.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.xlyuns.xunmi.BR;
import com.xlyuns.xunmi.R;
import com.pc.mimi.adapter.IncomeRichListAdapter;
import com.pc.mimi.app.BaseLazyFragment;
import com.pc.mimi.config.CommonTags;
import com.xlyuns.xunmi.databinding.FragmentIncomeRichlistBinding;

import java.util.ArrayList;

public class IncomeRichListFragment extends BaseLazyFragment<FragmentIncomeRichlistBinding, IncomeRichListViewModel> {

    public static IncomeRichListFragment newInstance(String tag) {
        Bundle args = new Bundle();
        args.putString("tag", tag);
        IncomeRichListFragment fragment = new IncomeRichListFragment();
        fragment.setArguments(args);
        return fragment;
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
        return R.layout.fragment_income_richlist;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        viewModel.incomeRechListBeans = new ArrayList<>();
        viewModel.incomeRichListAdapter = new IncomeRichListAdapter(viewModel.incomeRechListBeans);
        binding.recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        viewModel.incomeRichListAdapter.bindToRecyclerView(binding.recycler);
        switch (getArguments().getString("tag")) {
            case CommonTags.LIST_TYPE.IN_COME_LIST:
//                initIncomeListHeader();
                viewModel.requestIncomeListData();

                break;
            case CommonTags.LIST_TYPE.RICH_LIST://富豪榜
//                initRichListHeader();
                viewModel.requestRichListData();
                break;
        }
    }

//    //添加富豪榜header
//    private void initRichListHeader() {
//        for (int i = 0; i < 3; i++) {
//            View richListHeader = LayoutInflater.from(getActivity()).inflate(R.layout.inflater_income_rich_list_header, null);
//            viewModel.mRichListAdapter.addHeaderView(richListHeader);
//        }
//    }
//
//    //添加收入榜header
//    private void initIncomeListHeader() {
//        for (int i = 0; i < 3; i++) {
//            View inComeListHeader = LayoutInflater.from(getActivity()).inflate(R.layout.inflater_income_rich_list_header, null);
//            mIncomeListAdapter.addHeaderView(inComeListHeader);
//        }
//    }
}

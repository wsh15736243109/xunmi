package com.pc.mimi.ui.home;

import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.xlyuns.xunmi.BR;
import com.xlyuns.xunmi.R;
import com.pc.mimi.adapter.PersonalInfoListAdapter;
import com.pc.mimi.app.BaseLazyFragment;
import com.xlyuns.xunmi.databinding.FragmentPyListBinding;
import com.pc.mimi.ui.userdetail.UserDetailActivity;

import java.util.ArrayList;

import me.goldze.mvvmhabit.utils.KLog;

public class HomeListFragment extends BaseLazyFragment<FragmentPyListBinding, HomeContentViewModel> {


    public static HomeListFragment newInstance(String tag) {
        Bundle args = new Bundle();
        args.putString("tag", tag);
        HomeListFragment fragment = new HomeListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onFirstUserInvisible() {

    }

    @Override
    public void onUserVisible() {
        viewModel.requestNearPersonListData(true);
    }

    @Override
    public void onUserInvisible() {

    }


    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_py_list;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        KLog.d("viewModel", viewModel.toString());
        viewModel.personBeanList = new ArrayList<>();
        viewModel.mPersonInfoListAdapter = new PersonalInfoListAdapter(viewModel.personBeanList);
        binding.recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        viewModel.mPersonInfoListAdapter.bindToRecyclerView(binding.recycler);
        viewModel.mPersonInfoListAdapter.setOnItemChildClickListener(((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.isLike:
                    if (viewModel.personBeanList.get(position).getIsLike() == 0) {
                        viewModel.requestLikeIt(position);
                    } else {
                        viewModel.requestDelLikeIt(position);
                    }
                    break;
                case R.id.item:
                    Bundle bundle = new Bundle();
                    bundle.putString("other_user_id", viewModel.personBeanList.get(position).getUser_id());
                    startActivity(UserDetailActivity.class, bundle);
                    break;
            }
        }));
        viewModel.mPersonInfoListAdapter.setOnLoadMoreListener(() -> {
            viewModel.requestNearPersonListData(false);
        });
        viewModel.type = getArguments().getString("tag");
        viewModel.requestNearPersonListData(true);
    }

    @Override
    public void initViewObservable() {
        viewModel.isRefresh.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (getUserVisibleHint() && isViewCreated && isUIVisible) {
                    KLog.d("----------" + getArguments().getString("tag") + "--------------可见----------------");
                    viewModel.requestNearPersonListData(true);
                }
            }
        });
    }
}

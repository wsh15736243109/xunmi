package com.pc.mimi.ui.message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.xlyuns.xunmi.BR;
import com.xlyuns.xunmi.R;
import com.xlyuns.xunmi.databinding.FragmentSysMessageBinding;

import me.goldze.mvvmhabit.base.BaseFragment;

public class SysMessageFragment extends BaseFragment<FragmentSysMessageBinding, SysMessageViewModel> {
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_sys_message;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {

    }
}

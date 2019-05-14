package com.pc.mimi.binding.smartrefreshlayout;

import android.databinding.BindingAdapter;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import me.goldze.mvvmhabit.binding.command.BindingCommand;


public class ViewAdapter {
    @SuppressWarnings("unchecked")
    @BindingAdapter({"setOnRefresh"})
    public static void onRefresh(final SmartRefreshLayout smartRefreshLayout, final BindingCommand onRefreshCommand) {
        OnRefreshListener listener = refreshLayout -> onRefreshCommand.execute(smartRefreshLayout);
        smartRefreshLayout.setOnRefreshListener(listener);
    }

    @SuppressWarnings("unchecked")
    @BindingAdapter({"setOnLoadMore"})
    public static void onLoadMore(final SmartRefreshLayout smartRefreshLayout, final BindingCommand onLoadMoreCommand) {
        OnLoadMoreListener listener = refreshLayout -> onLoadMoreCommand.execute(smartRefreshLayout);
        smartRefreshLayout.setOnLoadMoreListener(listener);
    }
}
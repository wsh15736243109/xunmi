package com.pc.mimi.binding.switchview;

import android.databinding.BindingAdapter;
import android.widget.Switch;

import me.goldze.mvvmhabit.binding.command.BindingCommand;


public class ViewAdapter {
    @SuppressWarnings("unchecked")
    @BindingAdapter({"onSwitchChanged"})
    public static void setOnCheckedChangeListener(final Switch sw, final BindingCommand<Boolean> booleanBindingCommand) {
        sw.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            booleanBindingCommand.execute(isChecked);
        }));
    }


}
package com.pc.mimi.binding.labelsview;

import android.databinding.BindingAdapter;
import android.view.View;

import com.pc.mimi.widget.LabelsView;

import java.util.List;

import me.goldze.mvvmhabit.binding.command.BindingCommand;


public class ViewAdapter {
    @SuppressWarnings("unchecked")
    @BindingAdapter({"setLabelsClickCommand"})
    public static void setLabelsSelectListener(final LabelsView labelsView, final BindingCommand<OnLabelClickEvent> bindingCommand) {

        LabelsView.OnLabelClickListener labelClickListener = new LabelsView.OnLabelClickListener() {
            @Override
            public void onLabelClick(View label, String labelText, int position) {
                if (bindingCommand != null)
                    bindingCommand.execute(new OnLabelClickEvent(label, labelText, position));
            }
        };
        labelsView.setOnLabelClickListener(labelClickListener);

    }

    /**
     * 设置数据源
     */
    @SuppressWarnings("unchecked")
    @BindingAdapter({"setLabels"})
    public static void setLabels(final LabelsView labelsView,final List<String> strings) {
        labelsView.setLabels(strings);
    }

//    /**
//     * 设置数据源
//     */
//    @SuppressWarnings("unchecked")
//    @BindingAdapter({"setLabels"})
//    public static void setLabels(final LabelsView labelsView, String[] strings) {
//        labelsView.setLabels(strings);
//    }

    public static class OnLabelClickEvent {
        public View label;
        public String labelText;
        public int position;

        public OnLabelClickEvent(View label, String labelText, int position) {
            this.label = label;
            this.labelText = labelText;
            this.position = position;
        }
    }

}
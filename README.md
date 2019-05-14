<layout>
    <data>
        <variable
            name=""
            type=""
    </data>
</layout>

Map<String,Object>map = new HashMap<String,Object>(){{
            put("phone", );
        }};


         <com.xlyuns.xunmi.widget.LabelsView
                                    android:id="@+id/flow"
                                    android:layout_width="0dp"
                                    android:layout_height="wrap_content"
                                    android:layout_weight="2"
                                    app:labelBackground="@drawable/shape_strok_radius_3"
                                    app:labelTextPaddingBottom="6dp"
                                    app:labelTextPaddingLeft="12dp"
                                    app:labelTextPaddingRight="12dp"
                                    app:labelTextPaddingTop="6dp"
                                    app:labelTextSize="14sp"
                                    app:lineMargin="8dp"
                                    app:selectType="NONE"
                                    app:setLabels="@{viewModel.areaLabelsData}"
                                    app:wordMargin="8dp" />

         UIChangeObser uc = new UIChangeObser();
            public class UIChangeObser {
                public ObservableBoolean pic = new ObservableBoolean(false);
            }
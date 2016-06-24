package cc.trity.xpuzzle.demo.widget;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import cc.trity.xpuzzle.R;
import cc.trity.xpuzzle.databinding.DialogDeliveryPopBinding;
import cc.trity.xpuzzle.model.BottomSheetModel;
import cc.trity.ttlibrary.event.OnSingleClickListener;
import cc.trity.ttlibrary.ui.adapter.recycler.LoadMoreAdapter;
import cc.trity.ttlibrary.ui.adapter.recycler.SimpleAdapter;
import cc.trity.ttlibrary.ui.dialog.BaseBottomSheetDialog;

/**
 * Created by trity on 22/6/16.
 */
public class BottomSheetDemoDialog extends BaseBottomSheetDialog<DialogDeliveryPopBinding> {
    private SimpleAdapter simpleAdapter;
    public BottomSheetDemoDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.dialog_delivery_pop;
    }

    @Override
    public void initVariables() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        simpleAdapter=new SimpleAdapter(getContext());
        simpleAdapter.setState(LoadMoreAdapter.State.NONE);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setHasFixedSize(true);
//        binding.recyclerView.setAdapter(simpleAdapter);
    }

    @Override
    public void initListener() {
        binding.tvSure.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    public void loadData() {
        for(int i=0;i<6;i++){
            simpleAdapter.add(new BottomSheetModel());
        }
        simpleAdapter.notifyDataSetChanged();
    }

}

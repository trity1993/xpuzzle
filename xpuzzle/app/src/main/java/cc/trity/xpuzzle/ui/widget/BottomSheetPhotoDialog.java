package cc.trity.xpuzzle.ui.widget;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;

import cc.trity.ttlibrary.core.Tasks;
import cc.trity.ttlibrary.ui.adapter.recycler.LoadMoreAdapter;
import cc.trity.ttlibrary.ui.adapter.recycler.SimpleAdapter;
import cc.trity.ttlibrary.ui.dialog.BaseBottomSheetDialog;
import cc.trity.xpuzzle.R;
import cc.trity.xpuzzle.databinding.DialogPhotoBinding;
import cc.trity.xpuzzle.model.PhotoSelectModel;

/**
 * Created by trity on 22/6/16.
 */
public class BottomSheetPhotoDialog extends BaseBottomSheetDialog<DialogPhotoBinding> {
    private SimpleAdapter simpleAdapter;
    public BottomSheetPhotoDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.dialog_photo;
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
        binding.recyclerView.setAdapter(simpleAdapter);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void loadData() {
        Tasks.handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                simpleAdapter.add(new PhotoSelectModel("拍照",R.mipmap.ic_launcher));
                simpleAdapter.add(new PhotoSelectModel("相册",R.mipmap.ic_launcher));
                simpleAdapter.notifyDataSetChanged();
            }
        },3000);

    }

}

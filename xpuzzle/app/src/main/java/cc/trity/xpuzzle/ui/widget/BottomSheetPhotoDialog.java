package cc.trity.xpuzzle.ui.widget;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;

import java.io.File;

import cc.trity.ttlibrary.ui.adapter.recycler.LoadMoreAdapter;
import cc.trity.ttlibrary.ui.adapter.recycler.SimpleAdapter;
import cc.trity.ttlibrary.ui.dialog.BaseBottomSheetDialog;
import cc.trity.xpuzzle.R;
import cc.trity.xpuzzle.databinding.DialogPhotoBinding;
import cc.trity.xpuzzle.viewmodel.PhotoSelectViewModel;

/**
 * Created by trity on 22/6/16.
 */
public class BottomSheetPhotoDialog extends BaseBottomSheetDialog<DialogPhotoBinding> implements PhotoSelectViewModel.OnPhotoListener{
    private SimpleAdapter simpleAdapter;
    private File file;
    private Activity mActivity;
    public BottomSheetPhotoDialog(@NonNull Activity activity) {
        super(activity);
        this.mActivity=activity;
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
        simpleAdapter.add(new PhotoSelectViewModel(mActivity, "拍照",R.drawable.ic_camera_alt_black_24dp,this));
        simpleAdapter.add(new PhotoSelectViewModel(mActivity,"相册",R.drawable.ic_photo_size_select_actual_black_24dp,this));
        simpleAdapter.notifyDataSetChanged();
    }

    @Override
    public void getPhotoRes(PhotoSelectViewModel viewModel) {
        file=viewModel.getImgFile();
    }

    public File getFile() {
        return file;
    }
}

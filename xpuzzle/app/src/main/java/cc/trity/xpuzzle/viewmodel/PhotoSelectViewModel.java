package cc.trity.xpuzzle.viewmodel;

import android.app.Activity;
import android.databinding.BaseObservable;
import android.view.View;

import java.io.File;

import cc.trity.ttlibrary.ui.adapter.viewholder.LayoutId;
import cc.trity.ttlibrary.utils.PhotoHelper;
import cc.trity.xpuzzle.R;
import cc.trity.xpuzzle.ui.widget.GameView;

/**
 * Created by trity on 27/6/16.
 */
public class PhotoSelectViewModel extends BaseObservable implements LayoutId {

    private String tvName;
    private int resFlag;
    private Activity activity;
    private OnPhotoListener listener;
    private File imgFile;

    public PhotoSelectViewModel() {
    }

    public PhotoSelectViewModel(Activity activity,String tvName, int resFlag,OnPhotoListener listener) {
        this.activity=activity;
        this.tvName = tvName;
        this.resFlag = resFlag;
        this.listener=listener;
    }

    public void onViewClick(View view){
        if(tvName.equals("相册")){
            PhotoHelper.pickPhoto(activity);
        }else if(tvName.equals("拍照")){
            imgFile=PhotoHelper.takePhoto(activity, GameView.DEFAULT_PATH);
            if(imgFile!=null&listener!=null)
                listener.getPhotoRes(this);
        }
    }
    public String getTvName() {
        return tvName;
    }

    public void setTvName(String tvName) {
        this.tvName = tvName;
        notifyChange();
    }

    public int getResFlag() {
        return resFlag;
    }

    public void setResFlag(int resFlag) {
        this.resFlag = resFlag;
        notifyChange();
    }

    public File getImgFile() {
        return imgFile;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_photo;
    }

    public interface OnPhotoListener{
        void getPhotoRes(PhotoSelectViewModel viewModel);
    }
}

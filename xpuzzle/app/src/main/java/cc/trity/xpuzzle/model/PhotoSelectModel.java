package cc.trity.xpuzzle.model;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;

import cc.trity.ttlibrary.ui.adapter.viewholder.LayoutId;
import cc.trity.xpuzzle.R;

/**
 * Created by trity on 27/6/16.
 */
public class PhotoSelectModel extends BaseObservable implements LayoutId {

    private String tvName;
    private int resFlag;
    public ObservableField<String> tvOfName=new ObservableField<>();

    public PhotoSelectModel() {
    }

    public PhotoSelectModel(String tvName, int resFlag) {
        this.tvName = tvName;
        this.resFlag = resFlag;
        this.tvOfName.set(tvName);
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

    @Override
    public int getLayoutId() {
        return R.layout.item_photo;
    }
}

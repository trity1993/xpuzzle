package cc.trity.xpuzzle.utils;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

/**
 * Created by trity on 28/6/16.
 */
public class ComBindingAdapter {
    @BindingAdapter("bind:src")
    public static void setImageDrawable(ImageView img,int resInt){
        img.setImageResource(resInt);
    }
}

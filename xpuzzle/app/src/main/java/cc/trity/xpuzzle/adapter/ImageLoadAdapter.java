package cc.trity.xpuzzle.adapter;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import cc.trity.xpuzzle.R;
import cc.trity.xpuzzle.databinding.ItemHeadlayoutBinding;
import cc.trity.ttlibrary.ui.adapter.viewholder.BaseViewHolder;
import cc.trity.ttlibrary.ui.adapter.recycler.SimpleAdapter;

/**
 * Created by trity on 2016/5/26.
 */
public class ImageLoadAdapter extends SimpleAdapter<ItemHeadlayoutBinding> {
    public ImageLoadAdapter(Context context) {
        super(context);
    }

    @Override
    public void onBindViewBinding(BaseViewHolder<ItemHeadlayoutBinding> holder, int position) {
        super.onBindViewBinding(holder, position);
        Glide.with(context)
                .load("http://e.hiphotos.baidu.com/image/pic/item/a1ec08fa513d2697e542494057fbb2fb4316d81e.jpg")
                .placeholder(R.mipmap.topinfo_ban_bg)
                .diskCacheStrategy(DiskCacheStrategy.ALL)//default
                .into(holder.getBinding().ivBg);

    }
}

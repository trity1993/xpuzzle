package cc.trity.ttlibrary.ui.adapter.viewpager;

import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 实现无线循环
 * 使用这个，需要保证最少有4个view
 * 如果1个时不能滑动
 * 如果2,3个时，则循环添加多一次，到四个以上,否则会有bug
 * <p>
 * Created by Trity on 6/20/16.
 */
public class LoopPageAdapter extends CommonPageAdapter {


    private int realIndex;//少于4个的情况，需要标记其真正的索引

    public LoopPageAdapter() {
        super();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = getItem(position);//position无限大，需要进行处理
        try {
            container.addView(view);
        } catch (Exception e) {
        }
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        container.removeView(views.getList(position % views.size()));
    }

    @Override
    public int getCount() {
        if (getRealCount() <= 1) {
            return getRealCount();
        } else if (getRealCount() < 4) {
            realIndex = getRealCount();
            List<View> list = new ArrayList<>();
            Collections.copy(getList(), list);//使用此copy函数才能为深拷贝
            for (View vTmp : list) {//循环添加多一次
                add(vTmp);
            }
        }
        return Integer.MAX_VALUE;
    }

    public int getRealCount() {
        return getList() == null ? 0 : getList().size();
    }

    public int toRealPosition(int position) {
        return position % getRealCount();
    }

    @Override
    public View getItem(int position) {
        return super.getItem(toRealPosition(position));
    }

    /**
     * 设置开始位置，使得能够一开就左滑动
     *
     * @return
     */
    public int getStartPosition() {
        return getRealCount() * 1000;
    }

    /**
     * 少于4个的情况，需要对其索引进行add
     *
     * @param item
     */
    @Override
    public boolean add(View item) {
        if (realIndex <= 0)
            return super.add(item);
        else {
            if (getList() == null)
                setList(new ArrayList<View>());
            getList().add(realIndex++, item);
            return true;
        }
    }
}

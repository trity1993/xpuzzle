package cc.trity.ttlibrary.ui.adapter.viewpager;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cc.trity.ttlibrary.ui.adapter.IListAdapter;

/**
 * 通用View PagerAdapter
 * <p/>
 * Created by Tony on 10/20/14.
 */
public class CommonPageAdapter extends PagerAdapter implements IListAdapter<View> {
    private static final String TAG = CommonPageAdapter.class.getName();
    private List<View> mViews;

    public CommonPageAdapter() {

    }

    public CommonPageAdapter(List<View> views) {
        mViews = views;
    }

    @Override
    public int getCount() {
        return mViews == null ? 0 : mViews.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mViews.get(position);
        try {
            container.addView(view);
        } catch (Exception e) {
        }
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViews.get(position));
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public void setList(List<View> views) {
        mViews = views;
        notifyDataSetChanged();
    }

    @Override
    public List<View> getList() {
        return mViews;
    }

    @Override
    public boolean addAll(List<View> list) {
        if (mViews == null) {
            mViews = new ArrayList<>(list.size());
        }
        return mViews.addAll(list);
    }

    @Override
    public boolean add(View item) {
        if (mViews == null) {
            mViews = new ArrayList<>();
        }
        return mViews.add(item);
    }

    @Override
    public View getItem(int position) {
        return mViews.get(position);
    }

    @Override
    public boolean contains(View item) {
        return mViews == null ? false : mViews.contains(item);
    }

    @Override
    public boolean remove(View item) {
        if (mViews != null) {
            return mViews.remove(item);
        }
        return false;
    }

    @Override
    public View remove(int position) {
        if (mViews != null) {
            return mViews.remove(position);
        }
        return null;
    }

    @Override
    public void clear() {
        if (mViews != null) {
            mViews.clear();
        }
    }

}

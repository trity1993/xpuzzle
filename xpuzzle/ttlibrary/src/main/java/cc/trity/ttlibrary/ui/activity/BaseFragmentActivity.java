package cc.trity.ttlibrary.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import java.util.List;

import cc.trity.ttlibrary.ActivityCollector;
import cc.trity.ttlibrary.ui.BaseInit;
import cc.trity.ttlibrary.ui.fragment.BaseFragment;

/**
 * 当Activity包含fragment的时候使用
 * Created by trity on 2016/5/22.
 */
public abstract class BaseFragmentActivity extends AppCompatActivity implements BaseInit {
    protected final String TAG=getClass().getName();
    private String fragmentTag = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityCollector.addActivity(this);

        initVariables();
        processRestoreInstanceState(savedInstanceState);
        initView(savedInstanceState);
        initListener();
        loadData();
    }

    /**
     * 内存重启的时候进行恢复工作
     * @param savedInstanceState
     */
    private void processRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            List<Fragment> fragments = getSupportFragmentManager().getFragments();

            if (fragments != null && fragments.size() > 0) {

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                for (int i = fragments.size() - 1; i >= 0; i--) {
                    Fragment fragment = fragments.get(i);

                    if (fragment instanceof BaseFragment) {
                        BaseFragment baseFragment = (BaseFragment) fragment;
                        if (baseFragment.ismIsHidden()) {
                            ft.hide(baseFragment);
                        } else {
                            ft.show(baseFragment);
                        }
                    }
                }
                ft.commit();
            }
        }
    }

    /**
     * 先隐藏先前的一个，再展示下一个
     * 这个已经解决多个tab的情况了
     * @param fragment
     */
    protected void showFragment(Fragment fragment,String tag) {
        if(tag==null)
            tag=fragment.getClass().getName();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (getFragmentTag() != null) {
            Fragment lastFragment = fragmentManager.findFragmentByTag(getFragmentTag());
            if (lastFragment != null) {
                transaction.hide(lastFragment);
            }
        }
        if (fragment.isAdded()) {
            transaction.show(fragment);
        } else {
            transaction.add(setContainerId(), fragment, tag).setBreadCrumbShortTitle(tag);
        }
        transaction.commit();
        setFragmentTag(tag);
    }
    protected void showFragment(Fragment fragment){
        showFragment(fragment,null);
    }

    /**
     * 通过findFragmentByTag的方法得到当前的fragment
     * @return
     */
    protected Fragment getCurrentFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        if(getFragmentTag()!=null)
            return fragmentManager.findFragmentByTag(getFragmentTag());
        return null;
    }

    /**
     * 初始化toolbar,默认home为true
     * @param mToolbar
     */
    protected void trySetupToolbar(Toolbar mToolbar) {
        trySetupToolbar(mToolbar,true);
    }

    protected void trySetupToolbar(Toolbar mToolbar,boolean isHome) {
        try {
            setSupportActionBar(mToolbar);
            if(isHome)
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException e) {
            Log.e(getClass().getSimpleName(), "toolbar is null!");
        }
    }

    /**
     * 实现后退按钮
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home://相应 actionbar的后退按钮
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ActivityCollector.removeActivity(this);
    }

    public void setFragmentTag(String fragmentTag) {
        this.fragmentTag = fragmentTag;
    }

    public String getFragmentTag() {
        return fragmentTag;
    }

    /**
     * Set Container's id
     */
    protected abstract int setContainerId();
}

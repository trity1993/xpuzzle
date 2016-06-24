package cc.trity.ttlibrary.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import cc.trity.ttlibrary.ActivityCollector;
import cc.trity.ttlibrary.ui.BaseInit;

/**
 * Created by trity on 2016/5/15.
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseInit {
    protected final String TAG=getClass().getName();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityCollector.addActivity(this);

        initVariables();
        initView(savedInstanceState);
        initListener();
        loadData();
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
}

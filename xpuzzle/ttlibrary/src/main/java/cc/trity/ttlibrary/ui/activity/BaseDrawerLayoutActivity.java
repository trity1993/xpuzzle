package cc.trity.ttlibrary.ui.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;



import cc.trity.ttlibrary.R;

/**
 * 封装 Animate the Hamburger Icon的效果
 * 封住 navigation click 监听事件
 * Created by trity on 1/4/16.
 */
public abstract class BaseDrawerLayoutActivity extends BaseFragmentActivity {

    protected ActionBarDrawerToggle drawerToggle;
    protected DrawerLayout drawerLayout;//注意这个值必须要复制，否则相应的操作无法执行

    private int gravity= GravityCompat.START;//侧栏的方向,默认在左边

    /**
     * 侧栏点击监听器
     */
    protected final NavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener=new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            selectDrawerItem(item);
            return true;
        }
    };

    protected ActionBarDrawerToggle setupDrawerToggle(DrawerLayout drawerLayout, android.support.v7.widget.Toolbar toolbar){
        return new ActionBarDrawerToggle(this,drawerLayout,toolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if(drawerToggle!=null)
            drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(drawerToggle!=null)
            drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                if(drawerLayout!=null)
                    drawerLayout.openDrawer(gravity);
                return true;
        }
        if(drawerToggle!=null){
            if(drawerToggle.onOptionsItemSelected(item)){
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(gravity)){
            drawerLayout.closeDrawer(gravity);
            return;
        }
        super.onBackPressed();
    }

    /**
     * 封装navigation的select item
     * 需要进行判断null的操作
     * @param item
     */
    public abstract void selectDrawerItem(MenuItem item);

    /**
     * 用以设置Gravity的显示位置
     * @param gravity
     */
    public void setGravity(int gravity) {
        this.gravity = gravity;
    }
}


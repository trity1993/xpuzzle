package cc.trity.xpuzzle.demo.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import cc.trity.xpuzzle.R;
import cc.trity.xpuzzle.databinding.ActivityDrawerDemoBinding;
import cc.trity.ttlibrary.ui.activity.BaseDrawerLayoutActivity;
import cc.trity.ttlibrary.ui.fragment.DefaultWebViewFragment;

/**
 * Created by trity on 2016/5/17.
 */
public class DrawerLayoutDemoActivity  extends BaseDrawerLayoutActivity{
    ActivityDrawerDemoBinding binding;

    private TextView tvHeader;
    private Button btnHeader;
    private Toolbar toolbar;
    @Override
    public void selectDrawerItem(MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_read:
                break;
            case R.id.nav_share:
                break;
            case R.id.about_support:
                break;
            case R.id.about_website:
                break;
        }
        item.setChecked(true);
        binding.drawerLayout.closeDrawers();
    }

    @Override
    public void initVariables() {
        binding= DataBindingUtil.setContentView(this, R.layout.activity_drawer_demo);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        initHeaderLayout();
        drawerLayout=binding.drawerLayout;//记得赋值
        toolbar=(Toolbar)(binding.tbLayout.findViewById(R.id.tb_layout));

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerToggle = setupDrawerToggle(binding.drawerLayout, toolbar);

    }

    /**
     * 初始化HeaderLayout中的view
     */
    private void initHeaderLayout(){
        tvHeader=(TextView) binding.nvView.getHeaderView(0).findViewById(R.id.tv_header);
        btnHeader=(Button) binding.nvView.getHeaderView(0).findViewById(R.id.btn_header_login);
    }

    @Override
    public void initListener() {
        binding.drawerLayout.addDrawerListener(drawerToggle);
        binding.nvView.setNavigationItemSelectedListener(onNavigationItemSelectedListener);
    }

    @Override
    public void loadData() {
        showFragment(DefaultWebViewFragment.newInstance("http://hexo.trity.cc/",null,null));
    }
   public static void makeIntent(Context context){
       Intent i=new Intent(context,DrawerLayoutDemoActivity.class);
       context.startActivity(i);
   }

    @Override
    protected int setContainerId() {
        return R.id.fl_content;
    }
}

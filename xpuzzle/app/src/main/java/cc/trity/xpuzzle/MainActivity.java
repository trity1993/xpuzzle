package cc.trity.xpuzzle;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import cc.trity.xpuzzle.databinding.ActivityMainBinding;
import cc.trity.xpuzzle.demo.activity.BottomSheetActivity;
import cc.trity.xpuzzle.demo.activity.DrawerLayoutDemoActivity;
import cc.trity.xpuzzle.demo.activity.RecyclerDemoActivity;
import cc.trity.xpuzzle.demo.activity.SettingDemoActivity;
import cc.trity.xpuzzle.demo.activity.WebViewDemoActivity;
import cc.trity.ttlibrary.ui.activity.BaseActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    ActivityMainBinding binding;

    @Override
    public void initVariables() {
        binding=DataBindingUtil.setContentView(this,R.layout.activity_main);
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void initListener() {
        binding.btnWebview.setOnClickListener(this);
        binding.btnDrawerLayout.setOnClickListener(this);
        binding.btnSetting.setOnClickListener(this);
        binding.btnRecycler.setOnClickListener(this);
        binding.btnBottomSheet.setOnClickListener(this);
    }

    @Override
    public void loadData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_webview:
                WebViewDemoActivity.makeIntent(MainActivity.this);
                break;
            case R.id.btn_drawerLayout:
                DrawerLayoutDemoActivity.makeIntent(MainActivity.this);
                break;
            case R.id.btn_setting:
                SettingDemoActivity.makeIntent(MainActivity.this);
                break;
            case R.id.btn_recycler:
                RecyclerDemoActivity.makeIntent(MainActivity.this);
                break;
            case R.id.btn_bottom_sheet:
                startActivity(BottomSheetActivity.makeIntent(MainActivity.this));
                break;
        }
    }
}

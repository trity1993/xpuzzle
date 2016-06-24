package cc.trity.xpuzzle.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import cc.trity.ttlibrary.ui.activity.BaseActivity;
import cc.trity.xpuzzle.R;

/**
 * Created by trity on 2016-6-24
 */
public class MainGameActivity extends BaseActivity {
    cc.trity.xpuzzle.ActivityMainGameBinding binding;

    @Override
    public void initVariables() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_game);
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void loadData() {

    }
}

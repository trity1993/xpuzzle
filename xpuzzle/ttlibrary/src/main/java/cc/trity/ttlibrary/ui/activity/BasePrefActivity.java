package cc.trity.ttlibrary.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v7.widget.Toolbar;

import cc.trity.ttlibrary.R;
import cc.trity.ttlibrary.databinding.ActivitySettingsBaseBinding;

/**
 * 设置页面的基类
 * PreferenceFragment 继承的是app.fragment 暂时不知怎么处理
 * Created by trity on 21/3/16.
 */
public abstract class BasePrefActivity extends BaseActivity implements OnPreferenceListener  {
    private static final String RES_ID="resId";
    protected BasePreferenceFragment bpfragment;
    private ActivitySettingsBaseBinding binding;
    protected Toolbar toolbar;
    @Override
    protected void onResume() {
        super.onResume();
        initPreference();//注意这里必须在onResume里初始化，否则空指针的异常
    }

    @Override
    public void initVariables() {
        binding=DataBindingUtil.setContentView(this, R.layout.activity_settings_base);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        toolbar=(Toolbar) binding.tbLayout.findViewById(R.id.tb_layout);
        trySetupToolbar(toolbar);

        bpfragment= BasePreferenceFragment.newInstance(this);
        getFragmentManager().beginTransaction().replace(R.id.fl_layout, bpfragment).commit();
    }

    public static class BasePreferenceFragment extends PreferenceFragment {
        public static BasePreferenceFragment newInstance(OnPreferenceListener onPreferenceListener){
            Bundle bundle=new Bundle();
            bundle.putInt(RES_ID, onPreferenceListener.getPreferencesResId());
            BasePreferenceFragment basePreferenceFragment=new BasePreferenceFragment();
            basePreferenceFragment.setArguments(bundle);
            return basePreferenceFragment;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Bundle bundle=getArguments();
            int resId=bundle.getInt(RES_ID);
            addPreferencesFromResource(resId);//加载preference的xml
        }
    }
    @Override
    public void initListener() {
    }

    @Override
    public void loadData() {
    }
    /**
     * 初始化Preference的view
     */
    public abstract void initPreference();
}

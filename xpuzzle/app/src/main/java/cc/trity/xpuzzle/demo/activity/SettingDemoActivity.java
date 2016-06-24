package cc.trity.xpuzzle.demo.activity;

import android.content.Context;
import android.content.Intent;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceManager;

import cc.trity.xpuzzle.R;
import cc.trity.ttlibrary.ui.activity.BasePrefActivity;

/**
 * Created by trity on 2016/5/19.
 */
public class SettingDemoActivity extends BasePrefActivity implements Preference.OnPreferenceChangeListener,Preference.OnPreferenceClickListener {
    private CheckBoxPreference checkboxPrefs;
    private Preference prefClearCache;

    @Override
    public void initPreference() {
        PreferenceManager mPreferenceManager=bpfragment.getPreferenceManager();
        checkboxPrefs=(CheckBoxPreference)mPreferenceManager.findPreference("check_update_preference");//通过xml布局中的key进行获取
        prefClearCache=mPreferenceManager.findPreference("pref_clear");//通过xml布局中的key进行获取
        checkboxPrefs.setOnPreferenceChangeListener(this);
        prefClearCache.setOnPreferenceClickListener(this);
    }



    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if(preference==checkboxPrefs){
            boolean isStartService=(Boolean)newValue;
            //执行对应的操作
        }
        return true;
    }

    @Override
    public int getPreferencesResId() {
        return R.xml.pref_setting;
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        if(preference==prefClearCache){
            //执行对应的操作
        }
        return true;
    }
    public static void makeIntent(Context context){
        Intent i=new Intent(context,SettingDemoActivity.class);
        context.startActivity(i);
    }
}

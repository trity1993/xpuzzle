package cc.trity.xpuzzle.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.util.Log;

import cc.trity.ttlibrary.ui.activity.BasePrefActivity;
import cc.trity.xpuzzle.R;

/**
 * Created by trity on 2016/5/19.
 */
public class SettingActivity extends BasePrefActivity implements Preference.OnPreferenceChangeListener,Preference.OnPreferenceClickListener {
    private Preference prefClearCache;
    private ListPreference listPreference;

    @Override
    public void initPreference() {
        PreferenceManager mPreferenceManager=bpfragment.getPreferenceManager();
        prefClearCache=mPreferenceManager.findPreference("pref_clear");//通过xml布局中的key进行获取
        listPreference=(ListPreference) mPreferenceManager.findPreference("list_level_mode");

        prefClearCache.setOnPreferenceClickListener(this);
        listPreference.setOnPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if(preference==listPreference){
            //直接通过newValue得到最新的值
            Log.d(TAG, "onPreferenceChange: value="+listPreference.getValue()+"entryvalue"+listPreference.getEntry().toString()+"newvalue"+newValue.toString());
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
        Intent i=new Intent(context,SettingActivity.class);
        context.startActivity(i);
    }
}

package cc.trity.xpuzzle.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceManager;

import cc.trity.ttlibrary.core.Tasks;
import cc.trity.ttlibrary.helper.SharedPrefHelper;
import cc.trity.ttlibrary.io.Config;
import cc.trity.ttlibrary.ui.activity.BasePrefActivity;
import cc.trity.xpuzzle.R;
import cc.trity.xpuzzle.bean.Constants;
import cc.trity.xpuzzle.ui.widget.GameView;

/**
 * Created by trity on 2016/5/19.
 */
public class SettingActivity extends BasePrefActivity implements Preference.OnPreferenceChangeListener,Preference.OnPreferenceClickListener {
    private Preference prefClearCache;
    private ListPreference listLevelPref,listTimePref;
    private CheckBoxPreference cbAnimPref;

    @Override
    public void initPreference() {
        PreferenceManager mPreferenceManager=bpfragment.getPreferenceManager();
        prefClearCache=mPreferenceManager.findPreference("pref_clear");//通过xml布局中的key进行获取
        listLevelPref=(ListPreference) mPreferenceManager.findPreference("list_level_mode");
        listTimePref=(ListPreference) mPreferenceManager.findPreference("list_time_mode");
        cbAnimPref=(CheckBoxPreference) mPreferenceManager.findPreference("anim_checkout");

        prefClearCache.setOnPreferenceClickListener(this);
        listLevelPref.setOnPreferenceChangeListener(this);
        listTimePref.setOnPreferenceChangeListener(this);
        cbAnimPref.setOnPreferenceChangeListener(this);

        prefClearCache.setSummary(Config.getDataSize());

    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if(preference==listLevelPref){
            //直接通过newValue得到最新的值
            if(newValue.toString().equals("幼稚模式")){

            }else if(newValue.toString().equals("普通模式")){

            }
        }else if(preference==listTimePref){
            if(newValue.toString().equals("极限100秒")){
                MainGameActivity.isPassModel=false;
                SharedPrefHelper.putBoolean(Constants.SETTING_TIME_MODEL,false);
            }else if(newValue.toString().equals("极限闯关")){
                MainGameActivity.isPassModel=true;
                SharedPrefHelper.putBoolean(Constants.SETTING_TIME_MODEL,true);
            }
        }else if(preference==cbAnimPref){
            Boolean isAnim=(Boolean)newValue;
            GameView.isShowAnim=isAnim;
            SharedPrefHelper.putBoolean(Constants.SETTING_ANIM,isAnim);
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
            Tasks.runOnThreadPool(new Runnable() {
                @Override
                public void run() {
                    Config.clearData();
                    Tasks.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //更新UI
                            prefClearCache.setSummary(Config.getDataSize());
                        }
                    });
                }
            });
        }
        return true;
    }
    public static void makeIntent(Context context){
        Intent i=new Intent(context,SettingActivity.class);
        context.startActivity(i);
    }
}

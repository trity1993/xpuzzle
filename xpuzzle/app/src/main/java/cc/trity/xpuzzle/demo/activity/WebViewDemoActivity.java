package cc.trity.xpuzzle.demo.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.webkit.WebView;

import cc.trity.xpuzzle.R;
import cc.trity.xpuzzle.databinding.ActivityWebviewDemoBinding;
import cc.trity.ttlibrary.ui.activity.BaseFragmentActivity;
import cc.trity.ttlibrary.ui.fragment.DefaultWebViewFragment;

/**
 * Created by trity on 2016/5/16.
 */
public class WebViewDemoActivity extends BaseFragmentActivity implements DefaultWebViewFragment.WebViewChangeListener {
    ActivityWebviewDemoBinding binding;
    DefaultWebViewFragment webViewFragment;
    @Override
    public void initVariables() {
        binding=DataBindingUtil.setContentView(this, R.layout.activity_webview_demo);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        showFragment(webViewFragment=DefaultWebViewFragment.newInstance("http://hexo.trity.cc/",null,this));
    }

    @Override
    public void initListener() {

    }

    @Override
    public void loadData() {

    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        if(!binding.clpbLoading.isShown()){
            binding.clpbLoading.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        binding.clpbLoading.hide();
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        binding.clpbLoading.hide();
    }

    @Override
    public Object getJsInterface() {
        return null;
    }

    @Override
    public void onBackPressed() {
        Fragment fragment =getCurrentFragment();
        if(!((DefaultWebViewFragment)fragment).goHistoryBack())
            super.onBackPressed();
//        if(!webViewFragment.goHistoryBack())
//            super.onBackPressed();

    }

    public static void makeIntent(Context context){
        Intent i=new Intent(context,WebViewDemoActivity.class);
        context.startActivity(i);
    }

    @Override
    protected int setContainerId() {
        return R.id.fl_layout;
    }
}

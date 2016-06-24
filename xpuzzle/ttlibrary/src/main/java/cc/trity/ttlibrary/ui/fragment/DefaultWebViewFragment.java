package cc.trity.ttlibrary.ui.fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ZoomButtonsController;

import cc.trity.ttlibrary.R;
import cc.trity.ttlibrary.databinding.FragmentWebviewBinding;

/**
 * 针对WebView的默认事例封装
 * 使用接口的方式进行回调
 * Created by trity on 5/15.
 */
public class DefaultWebViewFragment extends BaseFragment<FragmentWebviewBinding> {
    public static final String LOAD_URL="LOAD_URL";
    public static final String JS_NAME="JS_NAME";
    private String url,urlTitle,jsName;
    private WebViewChangeListener listener;

    public static DefaultWebViewFragment newInstance(String url,String jsName,WebViewChangeListener listener) {

        Bundle args = new Bundle();
        args.putString(LOAD_URL,url);
        args.putString(JS_NAME,jsName);

        DefaultWebViewFragment fragment = new DefaultWebViewFragment();
        fragment.setArguments(args);
        fragment.setListener(listener);
        return fragment;
    }
    private WebViewClient webViewClient=new WebViewClient(){
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            view.setVisibility(View.VISIBLE);
            //加载网页完毕的操作
            if(listener!=null)
                listener.onPageFinished(view,url);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            //可以通过获取errorCode进行指定的操作
            if(listener!=null){
                listener.onReceivedError(view,errorCode,description,failingUrl);
            }
        }
    };

    private WebChromeClient webChromeClient=new WebChromeClient(){
        @Override
        public void onReceivedTitle(WebView view, String title) {
            //得到网页的title
            urlTitle=title;
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            //进度条改变的操作
            if(listener!=null)
                listener.onProgressChanged(view,newProgress);
        }
    };


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_webview;
    }

    @Override
    public void initVariables() {
        Bundle bundle=getArguments();
        url=bundle.getString(LOAD_URL);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        initWebView(binding.webView);
        binding.webView.loadUrl(url);
        if(listener!=null)
            binding.webView.addJavascriptInterface(listener.getJsInterface(),jsName);
    }

    @Override
    public void initListener() {
        binding.webView.setWebViewClient(webViewClient);
        binding.webView.setWebChromeClient(webChromeClient);
    }

    @Override
    public void loadData() {

    }
    /**
     * 默认初始化webview的设置
     * @param webView
     */
    public void initWebView(WebView webView) {
        WebSettings settings = webView.getSettings();
        settings.setDefaultFontSize(15);
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//优先使用缓存
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        int sysVersion = Build.VERSION.SDK_INT;
        if (sysVersion >= 11) {//android 版本的判断，以后换成一个类进行
            settings.setDisplayZoomControls(false);
        } else {
            ZoomButtonsController zbc = new ZoomButtonsController(webView);
            zbc.getZoomControls().setVisibility(View.GONE);
        }
    }

    /**
     * 依靠是否有浏览历史进行后退
     */
    public boolean goHistoryBack(){
        if(binding.webView.canGoBack()){
            binding.webView.goBack();
            return true;
        }
        return false;
    }

    public String getUrlTitle() {
        return urlTitle;
    }

    public void setUrlTitle(String urlTitle) {
        this.urlTitle = urlTitle;
    }

    public WebViewClient getWebViewClient() {
        return webViewClient;
    }

    public void setWebViewClient(WebViewClient webViewClient) {
        this.webViewClient = webViewClient;
    }

    public WebChromeClient getWebChromeClient() {
        return webChromeClient;
    }

    public void setWebChromeClient(WebChromeClient webChromeClient) {
        this.webChromeClient = webChromeClient;
    }

    public WebViewChangeListener getListener() {
        return listener;
    }

    public void setListener(WebViewChangeListener listener) {
        this.listener = listener;
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.webView.onResume();
        binding.webView.resumeTimers();
    }

    @Override
    public void onPause() {
        super.onPause();
        binding.webView.onPause();
        binding.webView.pauseTimers();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        binding.webView.clearCache(true);//清除缓存
        binding.webView.destroy();
    }

    public interface WebViewChangeListener{
        void onProgressChanged(WebView view, int newProgress);
        void onPageFinished(WebView view, String url);
        void onReceivedError(WebView view, int errorCode, String description, String failingUrl);
        Object getJsInterface();
    }
}

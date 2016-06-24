package cc.trity.xpuzzle.demo.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.bumptech.glide.Glide;

import cc.trity.xpuzzle.R;
import cc.trity.xpuzzle.adapter.ImageLoadAdapter;
import cc.trity.xpuzzle.databinding.ActivityRecyclerDemoBinding;
import cc.trity.xpuzzle.model.RecyclerModel;
import cc.trity.ttlibrary.ui.activity.BaseActivity;
import cc.trity.ttlibrary.ui.adapter.recycler.LoadMoreAdapter;
import cc.trity.ttlibrary.ui.adapter.recycler.LoadMoreListener;

/**
 * 通过监听scroll来实现加载更多：http://www.jianshu.com/p/4feb0c16d1b5
 * 关于滚动不进行加载图片：http://blog.csdn.net/gao_chun/article/details/48550117
 *     真的比之前的耗的资源更优吗?涉及到glide的加载图片库的知识不再这里扩展
 * Created by trity on 2016/5/24.
 */
public class RecyclerDemoActivity extends BaseActivity {
    private ImageLoadAdapter simpleAdapter;
    private ActivityRecyclerDemoBinding binding;
    boolean isLoadingMore=true;
    @Override
    public void initVariables() {
        binding= DataBindingUtil.setContentView(this,R.layout.activity_recycler_demo);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        simpleAdapter=new ImageLoadAdapter(RecyclerDemoActivity.this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(simpleAdapter);
        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState){
                    case RecyclerView.SCROLL_STATE_IDLE:
                        Glide.with(RecyclerDemoActivity.this).resumeRequests();
                        break;
                    case RecyclerView.SCROLL_STATE_DRAGGING:
                        Glide.with(RecyclerDemoActivity.this).pauseRequests();
                        break;
                    case RecyclerView.SCROLL_STATE_SETTLING:
                        Glide.with(RecyclerDemoActivity.this).pauseRequests();
                        break;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView,dx,dy);
                RecyclerView.LayoutManager layoutManager=recyclerView.getLayoutManager();
                int lastVisibleItem = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                int totalItemCount = layoutManager.getItemCount();
                //lastVisibleItem >= totalItemCount - 4 表示剩下4个item自动加载，各位自由选择
                // dy>0 表示向下滑动
                if(lastVisibleItem>=totalItemCount-4&&dy>0){
                    if(isLoadingMore){
                        Log.d(TAG, "onScrolled: ");
                        isLoadingMore=false;
                        //执行对应的异步方法，在回调中吧isLoadingMore=true，好让下一次加载更多的时候刷新
                    }
                }
            }
        });
    }

    @Override
    public void initListener() {
        binding.sflLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                simpleAdapter.clear();
//                loadData();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        simpleAdapter.setState(LoadMoreAdapter.State.ERROR);
                        simpleAdapter.notifyDataSetChanged();
                    }
                },500);
                binding.sflLayout.setRefreshing(false);
            }
        });
        simpleAdapter.setLoadMoreListener(new LoadMoreListener() {
            @Override
            public void onErrorReFresh() {
                loadData();
            }

            @Override
            public void onLoadMore() {
                loadData();
//                testNoMore();
            }
        });
    }

    @Override
    public void loadData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                simpleAdapter.add(new RecyclerModel());
                simpleAdapter.add(new RecyclerModel());
                simpleAdapter.add(new RecyclerModel());
                simpleAdapter.add(new RecyclerModel());
                simpleAdapter.add(new RecyclerModel());
                simpleAdapter.add(new RecyclerModel());
                simpleAdapter.add(new RecyclerModel());
                simpleAdapter.add(new RecyclerModel());
                simpleAdapter.add(new RecyclerModel());
                simpleAdapter.notifyDataSetChanged();
            }
        },500);
    }

    private void testNoMore(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                simpleAdapter.setState(LoadMoreAdapter.State.NO_MORE);
                simpleAdapter.notifyDataSetChanged();
            }
        },500);
    }
    private void testERROR(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                simpleAdapter.setState(LoadMoreAdapter.State.ERROR);
                simpleAdapter.notifyDataSetChanged();
            }
        },500);
    }

    public static void makeIntent(Context context){
        Intent i=new Intent(context,RecyclerDemoActivity.class);
        context.startActivity(i);
    }
}

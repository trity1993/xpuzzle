package cc.trity.ttlibrary.ui.adapter.recycler;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.View;
import android.view.ViewGroup;

import cc.trity.ttlibrary.R;
import cc.trity.ttlibrary.databinding.RecyclerStateBinding;
import cc.trity.ttlibrary.ui.adapter.viewholder.BaseViewHolder;

/**
 * Created by trity on 2016/5/23.
 */
public abstract class LoadMoreAdapter<T, B extends ViewDataBinding> extends ListAdapter<T, B> {
    private RecyclerStateBinding binding;
    private LoadMoreListener loadMoreListener;
    private State state=State.MORE;

    public LoadMoreAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType== R.layout.recycler_state){
            binding= DataBindingUtil.inflate(inflater,R.layout.recycler_state,parent,false);
            return new BaseViewHolder<>(binding);
        }
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder<B> holder, int position) {
        if(holder.getItemViewType()==R.layout.recycler_state){
            if(binding==null){
                binding=(RecyclerStateBinding)holder.getBinding();
            }
            handleStateChange();
        }else{
            super.onBindViewHolder(holder,position);
        }
    }

    @Override
    public int getItemCount() {
        return size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return R.layout.recycler_state;
        }
        return super.getItemViewType(position);
    }
    private void handleStateChange(){
        switch (state){
            case MORE:
                binding.clpbLoading.setVisibility(View.VISIBLE);
                binding.tvState.setVisibility(View.GONE);
                loadMore();
                break;
            case NO_MORE:
                binding.clpbLoading.setVisibility(View.GONE);
                binding.tvState.setText(R.string.state_no_more);
                binding.tvState.setVisibility(View.VISIBLE);
                break;
            case ERROR:
                binding.clpbLoading.setVisibility(View.GONE);
                binding.tvState.setText(R.string.state_error);
                binding.tvState.setVisibility(View.VISIBLE);
                binding.tvState.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        binding.tvState.setVisibility(View.GONE);
                        binding.clpbLoading.setVisibility(View.VISIBLE);
                        if(loadMoreListener!=null)
                            loadMoreListener.onErrorReFresh();
                    }
                });
                break;
            case NONE:
                binding.clpbLoading.setVisibility(View.GONE);
                binding.tvState.setVisibility(View.GONE);
                break;
        }
    }

    private void loadMore(){
        if(loadMoreListener!=null) {
            loadMoreListener.onLoadMore();//这里需要延迟操作来显示view
        }
    }

    public void setLoadMoreListener(LoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }

    public void setState(State state) {
        this.state = state;
    }

    public enum State{//设置不同的状态
        MORE,NO_MORE,ERROR,NONE
    }
}

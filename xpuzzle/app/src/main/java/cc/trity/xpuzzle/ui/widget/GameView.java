package cc.trity.xpuzzle.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Collections;
import java.util.List;

import cc.trity.ttlibrary.utils.DisplayUtils;
import cc.trity.xpuzzle.R;
import cc.trity.xpuzzle.model.ImagePiece;
import cc.trity.xpuzzle.utils.ImageSplitUtils;

/**
 * Created by trity on 24/6/16.
 */
public class GameView extends RelativeLayout implements View.OnClickListener {

    private int mColumn=3;//多少*多少形成的宫格数
    //容器的内边距
    private int mPadding;
    //小图的距离 dp
    private int mMagin = 3;
    private ImageView[] mGameOintuItems;
    private int mItemWidth;
    //源图片
    private Bitmap mBitmap;
    //切图后存储
    private List<ImagePiece> mItemBitmaps;
    //标记是否为第一次
    private boolean once;

    //容器的一个宽度
    private int mWidth;

    public GameView(Context context) {
        this(context,null);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init(){
        mMagin= DisplayUtils.dpToPx(getContext(),mMagin);
        mPadding=min(getPaddingBottom(),getPaddingTop(),getPaddingLeft(),getPaddingRight());
    }

    /**
     * 将当前的布局大小设置总是为正方形
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth=Math.max(getMeasuredHeight(),getMeasuredWidth());

        if(!once){
            //进行切图和乱序
            initBitmap();
            initItem();
            once=true;
        }

        setMeasuredDimension(mWidth,mWidth);//重设宽度
    }

    private void initBitmap(){
        // 判断是否存在此图片
        if(mBitmap==null)
            mBitmap= BitmapFactory.decodeResource(getResources(), R.mipmap.ic_girl);
        //进行裁剪
        mItemBitmaps= ImageSplitUtils.splitImage(mBitmap,mColumn);

        //打乱
        Collections.shuffle(mItemBitmaps);
    }

    /**
     * 设置imageview(item)宽高等属性
     */
    private void initItem(){
        //（ 容器的宽度 - 内边距 * 2  - 间距  ） /  裁剪的数量
        mItemWidth = (mWidth - mPadding * 2 - mMagin * (mColumn - 1)) / mColumn;
        //几 * 几
        mGameOintuItems = new ImageView[mColumn * mColumn];
        //设置itemImg的大小
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(mItemWidth, mItemWidth);

        for (int i = 0; i < mGameOintuItems.length; i++) {
            ImageView itemImg=new ImageView(getContext());
            itemImg.setOnClickListener(this);
            //设置图片
            itemImg.setImageBitmap(mItemBitmaps.get(i).getBitmap());
            //保持
            mGameOintuItems[i]=itemImg;
            //设置ID为下面的相对位置做准备
            itemImg.setId(i+1);
            //设置tag
            itemImg.setTag(i + "_" + mItemBitmaps.get(i).getIndex());

            //判断不是最后一列
            if (i + 1 % mColumn != 0) {
                lp.rightMargin = mMagin;
            }

            //判断不是第一列，因为i-1会报错
            if (i % mColumn != 0) {
                lp.addRule(RelativeLayout.RIGHT_OF, mGameOintuItems[i - 1].getId());
            }

            //判断如果不是第一行
            if ((i + 1) > mColumn) {
                lp.topMargin = mMagin;
                lp.addRule(RelativeLayout.BELOW, mGameOintuItems[i - mColumn].getId());
            }
            addView(itemImg, lp);
        }
    }


    @Override
    public void onClick(View v) {

    }

    /**
     * 多个参数下，找到最小的
     * @param params
     * @return
     */
    private int min(int... params){
        int min=params[0];
        for (int i = 0; i < params.length; i++) {
            if(min<params[i])
                min=params[i];
        }
        return min;
    }
}

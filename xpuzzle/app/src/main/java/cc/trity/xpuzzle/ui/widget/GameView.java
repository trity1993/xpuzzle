package cc.trity.xpuzzle.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.io.File;
import java.util.Collections;
import java.util.List;

import cc.trity.ttlibrary.event.AnimationAdapter;
import cc.trity.ttlibrary.helper.TipHelper;
import cc.trity.ttlibrary.io.Config;
import cc.trity.ttlibrary.utils.BitmapUtils;
import cc.trity.ttlibrary.utils.DisplayUtils;
import cc.trity.xpuzzle.R;
import cc.trity.xpuzzle.model.ImagePiece;
import cc.trity.xpuzzle.utils.ImageSplitUtils;

/**
 * Created by trity on 24/6/16.
 */
public class GameView extends RelativeLayout implements View.OnClickListener {
    public static final String DEFAULT_PATH=Config.getImageCachePath()+ File.separator+"game_temp.jpg";//暂时先固定自定义图片的路径
    private int mColumn = 3;//多少*多少形成的宫格数
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

    private ImageView firstImg, secondImg;

    private boolean isAniming;//动画运行的标志位
    private RelativeLayout mAnimLayout;//动画层

    private onGameChangeListener listener;

    private File newFileImg=new File(DEFAULT_PATH);

    public GameView(Context context) {
        this(context, null);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init() {
        mMagin = DisplayUtils.dpToPx(getContext(), mMagin);
        mPadding = min(getPaddingBottom(), getPaddingTop(), getPaddingLeft(), getPaddingRight());
    }

    /**
     * 将当前的布局大小设置总是为正方形
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = Math.min(getMeasuredHeight(), getMeasuredWidth());

        if (!once) {
            //进行切图和乱序
            initBitmap();
            initItem();
            once = true;
        }

        setMeasuredDimension(mWidth, mWidth);//重设宽度
    }

    private void initBitmap() {
        if(newFileImg==null||!newFileImg.exists()){//判断是否使用新设置的图片
            // 判断是否存在此图片
            if (mBitmap == null)
                mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_girl);
        }else{
            mBitmap= BitmapUtils.pathToBitmap(getContext(),newFileImg);
        }

        //进行裁剪
        mItemBitmaps = ImageSplitUtils.splitImage(mBitmap, mColumn);

        //打乱
        Collections.shuffle(mItemBitmaps);
    }
    private void initBitmap(Bitmap bitmap){
        if(bitmap==null){
            initBitmap();
            return;
        }
        //进行裁剪
        mItemBitmaps = ImageSplitUtils.splitImage(bitmap, mColumn);
        //打乱
        Collections.shuffle(mItemBitmaps);
    }

    /**
     * 设置imageview(item)宽高等属性
     */
    private void initItem() {
        //（ 容器的宽度 - 内边距 * 2  - 间距  ） /  裁剪的数量
        mItemWidth = (mWidth - mPadding * 2 - mMagin * (mColumn - 1)) / mColumn;
        //几 * 几
        mGameOintuItems = new ImageView[mColumn * mColumn];


        for (int i = 0; i < mGameOintuItems.length; i++) {
            ImageView itemImg = new ImageView(getContext());
            itemImg.setOnClickListener(this);
            //设置图片
            itemImg.setImageBitmap(mItemBitmaps.get(i).getBitmap());
            //保持
            mGameOintuItems[i] = itemImg;
            //设置ID为下面的相对位置做准备
            itemImg.setId(i + 1);
            //设置tag
            itemImg.setTag(i + "_" + mItemBitmaps.get(i).getIndex());

            //设置itemImg的大小
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(mItemWidth, mItemWidth);

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
        if(isAniming)//动画完成前进行屏蔽
            return;
        //重复点击
        if (firstImg == v) {
            firstImg.setColorFilter(null);
            firstImg = null;
            return;
        }

        if (firstImg == null) {
            firstImg = (ImageView) v;
            //设置选中效果
            firstImg.setColorFilter(Color.parseColor("#55FF0000"));
        } else {//第二次点击其他view
            secondImg = (ImageView) v;
            //交换
//            exchangeView();
            exchangeViewByAnim();
        }
    }

    /**
     * 使用TranslateAnimation进行设置交换动画
     * 需要添加一个动画层，否则直接进行切换会有一闪而过的bug
     * 原因：交换是直接重设item的图片，而不是改变其索引，
     */
    private void exchangeViewByAnim() {
        //先去掉颜色
        firstImg.setColorFilter(null);
        setUpAnimLayout();//创建动画层

        //添加firstAnim
        final ImageView firstAnimImg = new ImageView(getContext());
        firstAnimImg.setImageBitmap(mItemBitmaps.get(getImageIdByTag(firstImg.getTag().toString())).getBitmap());
        LayoutParams lp = new LayoutParams(mItemWidth, mItemWidth);
        lp.leftMargin = firstImg.getLeft() - mPadding;
        lp.topMargin = firstImg.getTop() - mPadding;
        firstAnimImg.setLayoutParams(lp);
        mAnimLayout.addView(firstAnimImg);

        // 添加SecondView
        ImageView secondAnimImg = new ImageView(getContext());
        secondAnimImg.setImageBitmap(mItemBitmaps
                .get(getImageIdByTag((String) secondImg.getTag())).getBitmap());
        LayoutParams lp2 = new LayoutParams(mItemWidth, mItemWidth);
        lp2.leftMargin = secondImg.getLeft() - mPadding;
        lp2.topMargin = secondImg.getTop() - mPadding;
        secondAnimImg.setLayoutParams(lp2);
        mAnimLayout.addView(secondAnimImg);

        //设置动画
        final TranslateAnimation tranAnimFirst = new TranslateAnimation(0, secondImg.getLeft() - firstImg.getLeft(), 0, secondImg.getTop() - firstImg.getTop());
        tranAnimFirst.setFillAfter(true);
        tranAnimFirst.setDuration(300);
        firstAnimImg.startAnimation(tranAnimFirst);

        TranslateAnimation tranAnimSecond = new TranslateAnimation(0, firstImg.getLeft() - secondImg.getLeft(), 0, firstImg.getTop() - secondImg.getTop());
        tranAnimSecond.setFillAfter(true);
        tranAnimSecond.setDuration(300);//注意这里不能不设置，还以为是默认设置的
        secondAnimImg.startAnimation(tranAnimSecond);

        tranAnimFirst.setAnimationListener(new AnimationAdapter() {
            @Override
            public void onAnimationStart(Animation animation) {
                isAniming = true;
                if (firstImg != null && secondImg != null) {
                    firstImg.setVisibility(INVISIBLE);
                    secondImg.setVisibility(INVISIBLE);
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                exchangeView();
                if(mAnimLayout!=null)
                    mAnimLayout.removeAllViews();
                isAniming = false;
            }
        });

    }

    /**
     * 以first,second的imageview为载体进行交换修改
     */
    private void exchangeView() {
        String firstTag = (String) firstImg.getTag();
        String secondTag = (String) secondImg.getTag();

        //获取到bitmap并且替换
        secondImg.setImageBitmap(mItemBitmaps.get(getImageIdByTag(firstTag)).getBitmap());
        firstImg.setImageBitmap(mItemBitmaps.get(getImageIdByTag(secondTag)).getBitmap());

        secondImg.setTag(firstTag);
        firstImg.setTag(secondTag);

        //为了兼容动画
        secondImg.setVisibility(VISIBLE);
        firstImg.setVisibility(VISIBLE);
        //回到最初始的状态
        firstImg = secondImg = null;
        if(listener!=null)
            listener.getMoveNum();
        //没交换一次，进行检测一下
        if (checkSuccess()) {
            nextLevel(true,null);
        }
    }

    /**
     * 创建动画层
     */
    private void setUpAnimLayout() {
        if (mAnimLayout == null) {
            mAnimLayout = new RelativeLayout(getContext());
            addView(mAnimLayout);
        }
    }

    /**
     * 通过tag得到对应imageview的id
     * 此id按照顺序添加的viewgroup中
     */
    private int getImageIdByTag(String tag) {
        String[] split = tag.split("_");
        return Integer.parseInt(split[0]);
    }

    /**
     * 通过tag得到对应切割image的list（打乱）的索引
     */
    private int getImageIndexByTag(String tag) {
        String[] split = tag.split("_");
        return Integer.parseInt(split[1]);
    }

    /**
     * 检测是否完成拼图
     */
    private boolean checkSuccess() {
        boolean isSuccess = true;
        for (int i = 0; i < mGameOintuItems.length; i++) {//用一维表示二维
            ImageView imageView = mGameOintuItems[i];
            if (getImageIndexByTag((String) imageView.getTag()) != i) {
                isSuccess = false;
                return isSuccess;
            }
        }
        if (isSuccess) {
            if(listener!=null)
                listener.getNextLevel();
            TipHelper.showSnackbar(this, "成功,进入下一关！");
        }

        return isSuccess;
    }

    public void nextLevel(boolean isNextLevel,Bitmap bitmap) {
        this.removeAllViews();
        mAnimLayout = null;
        if(isNextLevel)
            mColumn++;
        initBitmap(bitmap);
        initItem();
    }

    /**
     * 多个参数下，找到最小的
     *
     * @param params
     * @return
     */
    private int min(int... params) {
        int min = params[0];
        for (int i = 0; i < params.length; i++) {
            if (min < params[i])
                min = params[i];
        }
        return min;
    }

    public void setListener(onGameChangeListener listener) {
        this.listener = listener;
    }

    /**
     * 回调监听接口
     */
    public interface onGameChangeListener{
        int getNextLevel();

        int getMoveNum();
    }
}

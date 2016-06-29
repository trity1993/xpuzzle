package cc.trity.xpuzzle.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;

import cc.trity.ttlibrary.ActivityCollector;
import cc.trity.ttlibrary.ui.activity.BaseActivity;
import cc.trity.ttlibrary.utils.BitmapUtils;
import cc.trity.ttlibrary.utils.PhotoHelper;
import cc.trity.ttlibrary.utils.SystemUtils;
import cc.trity.xpuzzle.R;
import cc.trity.xpuzzle.ui.widget.BottomSheetPhotoDialog;
import cc.trity.xpuzzle.ui.widget.GameView;

/**
 * Created by trity on 2016-6-24
 */
public class MainGameActivity extends BaseActivity implements GameView.onGameChangeListener {
    private final static float RATIO = 0.75f;//照片压缩比例

    cc.trity.xpuzzle.ActivityMainGameBinding binding;
    private CountDownTimer countdownTimer;
    private AlertDialog dialog;
    public static final int TIME_DEFAULT=100;
    private int level=1;
    private int moveNum=0;
    private BottomSheetPhotoDialog bottomDialog;
    private long timeAll=TIME_DEFAULT;//初始化为100s
    private boolean isPause=false;
    private File imgFile;

    @Override
    public void initVariables() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_game);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        trySetupToolbar(binding.tbLayout);
        binding.tvLevel.setText("当前关卡："+ level);
        binding.tvMove.setText("当前步数："+ moveNum);
    }

    @Override
    public void initListener() {
        binding.gameView.setListener(this);
    }

    @Override
    public void loadData() {
        createTimeDown();
        countdownTimer.start();
    }

    private void createTimeDown(){
        countdownTimer=new CountDownTimer(timeAll*1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeAll=millisUntilFinished/1000;
                binding.tvTime.setText("还剩下："+timeAll+"秒");
            }

            @Override
            public void onFinish() {
                timeAll=TIME_DEFAULT;
                dialog=new AlertDialog
                        .Builder(MainGameActivity.this)
                        .setTitle("游戏结束")
                        .setMessage("很遗憾未能将图片复原")
                        .setNegativeButton("重玩", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                binding.gameView.nextLevel(false,null);
                                timeAll= TIME_DEFAULT;
                                createTimeDown();
                                countdownTimer.start();
                            }
                        })
                        .setPositiveButton("退出", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCollector.finishAll();
                            }
                        })
                        .create();
                if(dialog!=null)
                    dialog.show();

            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_camera:
                if(bottomDialog!=null)
                    bottomDialog.show();
                else{
                    bottomDialog=new BottomSheetPhotoDialog(MainGameActivity.this);
                    bottomDialog.show();
                }
                break;
            case R.id.menu_setting:
                SettingActivity.makeIntent(MainGameActivity.this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isPause){
            isPause=false;
            if(countdownTimer!=null){
                countdownTimer.cancel();
            }
            if(dialog==null||!dialog.isShowing())
                dialog=new AlertDialog
                        .Builder(MainGameActivity.this)
                        .setTitle("游戏暂停")
                        .setMessage("请点击进行恢复")
                        .setPositiveButton(R.string.btn_sure, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                createTimeDown();
                                if(countdownTimer!=null)
                                    countdownTimer.start();
                            }
                        }).create();
            dialog.show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        isPause=true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode== PhotoHelper.REQUEST_CODE_CAMERA){
                //相机回调处理
                imgFile=bottomDialog.getFile();
                if(imgFile!=null){
                    //进行压缩重置角度的处理
                    binding.gameView.nextLevel(false,resetCompressAngle(imgFile));
                }

            }else if(requestCode== PhotoHelper.REQUEST_CODE_PICK){
                //相册回调处理
                if(data!=null&&data.getData()!=null){

                }

            }else if(requestCode== PhotoHelper.REQUEST_CODE_CROP){
                //裁剪回调处理
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(bottomDialog!=null)
            bottomDialog.dismiss();
        if(dialog!=null)
            dialog.dismiss();
        if(countdownTimer!=null)
            countdownTimer.cancel();
    }
    /**
     * 重置旋转角度并显示
     */
    private Bitmap resetCompressAngle(File file){
        int requstWidth=(int) (SystemUtils.getScreenWidth(this)*RATIO);
        int requestHeight=(int) (SystemUtils.getScreenHeight(this)*RATIO);
        Bitmap bitmap= BitmapUtils.decodeBitmapFromFile(file.getAbsolutePath(),requstWidth,requestHeight);
        if(bitmap!=null){
            int degree=PhotoHelper.readPictureDegree(file.getAbsolutePath());
            if(degree!=0)
                bitmap=BitmapUtils.rotatingImageView(degree,bitmap);
        }
        return bitmap;
    }
    @Override
    public int getNextLevel() {
        binding.tvLevel.setText("当前关卡："+ ++level);
        return level;
    }

    @Override
    public int getMoveNum() {
        binding.tvMove.setText("当前步数："+ ++moveNum);
        return moveNum;
    }
}

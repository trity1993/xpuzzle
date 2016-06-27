package cc.trity.xpuzzle.ui.activity;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;

import cc.trity.ttlibrary.ActivityCollector;
import cc.trity.ttlibrary.ui.activity.BaseActivity;
import cc.trity.xpuzzle.R;
import cc.trity.xpuzzle.ui.widget.GameView;

/**
 * Created by trity on 2016-6-24
 */
public class MainGameActivity extends BaseActivity implements GameView.onGameChangeListener {
    cc.trity.xpuzzle.ActivityMainGameBinding binding;
    private CountDownTimer countdownTimer;
    private AlertDialog dialog;
    public static final int TIME_DEFAULT=100;
    private int level=1;
    private int moveNum=0;

    private long timeAll=TIME_DEFAULT;//初始化为100s


    @Override
    public void initVariables() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_game);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
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
                                binding.gameView.nextLevel(false);
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
                        .show();
                if(dialog!=null)
                    dialog.show();

            }
        };
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(countdownTimer!=null){
            countdownTimer.cancel();
        }
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
                }).show();
        if(dialog!=null)
            dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(dialog!=null)
            dialog.dismiss();
        if(countdownTimer!=null)
            countdownTimer.cancel();
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

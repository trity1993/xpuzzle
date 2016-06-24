package cc.trity.xpuzzle.demo.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cc.trity.xpuzzle.ActivityBottomSheetBinding;
import cc.trity.xpuzzle.R;
import cc.trity.xpuzzle.demo.widget.BottomSheetDemoDialog;

/**
 * Created by trity on 2016-6-22
 */
public class BottomSheetActivity extends AppCompatActivity {
	ActivityBottomSheetBinding binding;
    BottomSheetDialog dialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	    binding=DataBindingUtil.setContentView(this, R.layout.activity_bottom_sheet);
        binding.btnSheetDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dialog!=null){
                    dialog.show();
                    return;
                }
                dialog=new BottomSheetDemoDialog(BottomSheetActivity.this);
                dialog.show();
            }
        });
    }
    public static Intent makeIntent(Context context){
        Intent intent=new Intent(context,BottomSheetActivity.class);
        return intent;
    }
}

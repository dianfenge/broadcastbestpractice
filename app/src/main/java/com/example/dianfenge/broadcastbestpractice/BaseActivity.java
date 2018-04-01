package com.example.dianfenge.broadcastbestpractice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by dianfenge on 2018/3/24.
 */

public class BaseActivity extends AppCompatActivity {

    private ForceOffLineReceiver receiver;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
    }
    @Override
    protected void onResume(){
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.dianfenge.broadcastbestpractice.FORCE_OFFLINE");
        receiver = new ForceOffLineReceiver();
        registerReceiver(receiver,intentFilter);
    }
    @Override
    protected void onPause(){
        super.onPause();
        if (receiver != null){
            unregisterReceiver(receiver);
            receiver = null;
        }
    }



    @Override
    protected void onDestroy(){
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    class ForceOffLineReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(final Context context, Intent intent){
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Warning");
            builder.setMessage("you are forced to be offline.please try to login again.");
            builder.setCancelable(false);
            builder.setPositiveButton("OK",new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog,int which){
                    ActivityCollector.finishAll();//销毁所有活动
                    Intent intent = new Intent(context,LoginActivity.class);
                    context.startActivity(intent);//重新启动loginactiv
                }
            });
            builder.show();
        }
    }
}

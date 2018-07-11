package com.musk.norregister;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        try {
            AMSHooker.hookActivityManagerNative();
            AMSHooker.hookActivityThreadHandler();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button button=new Button(this);
        button.setText("启动Target");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test();
            }
        });
        setContentView(button);
    }

    private void test() {
        startActivity(new Intent(this,TargetActivity.class));
    }
}
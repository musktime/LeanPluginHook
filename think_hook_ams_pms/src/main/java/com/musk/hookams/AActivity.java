package com.musk.hookams;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.musk.hookams.test.TestHook;


public class AActivity extends Activity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        try {
            TestHook.hookAMS();
            TestHook.hookPms(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button button=new Button(this, null);
        setContentView(button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test();
            }
        });
    }

    void test(){
        getPackageManager().getInstalledApplications(0);
        //startActivity(new Intent(this,BActivity.class));
    }
}

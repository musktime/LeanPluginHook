package com.musk.hook;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class HookActivity extends Activity {


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
      /*  try {
            HookHelper.attachAppContext();
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("musk","--attachBaseContext--Exception:--"+e.getMessage());
        }*/
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        testAttach();
        Button but=new Button(this, null);
        but.setText("开始测试");
        setContentView(but);

        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test();
            }
        });
    }

    private void test(){
        Intent intent=new Intent(HookActivity.this,EmptyActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        //getApplicationContext().startActivity(intent);
    }

    /**
     * hook Activity的字段mInstrumentation
     */
    private void testAttach(){
        try {
            Class clazz=this.getClass().getSuperclass();
            Field mInstrumentationField=clazz.getDeclaredField("mInstrumentation");
            mInstrumentationField.setAccessible(true);

            Constructor constructor=clazz.getConstructor();
            Object activityInstance=constructor.newInstance();

            Instrumentation baseInstrumentation= (Instrumentation) mInstrumentationField.get(activityInstance);
            Instrumentation proxyInstrumentation=new ProxyInstrumentation(baseInstrumentation);

            mInstrumentationField.set(activityInstance,proxyInstrumentation);
            Log.i("musk","-----testAttach:----");
        } catch (Exception e) {
            Log.i("musk","-----EX:----"+e.getMessage());
        }
    }
}
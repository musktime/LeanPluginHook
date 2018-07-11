package com.musk.hook;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import java.lang.reflect.Method;

public class ProxyInstrumentation extends Instrumentation {

    Instrumentation mBase;

    public ProxyInstrumentation(Instrumentation base){
        mBase=base;
    }

    public ActivityResult execStartActivity(Context who, IBinder contextThread, IBinder token, Activity target,
                                           Intent intent, int requestCode, Bundle options){
        Log.i("musk","---------------hook之前：---------------");
        Log.i("musk","==startActivity执行参数：==\n"+"who = [" + who + "], " +
                "\ncontextThread = [" + contextThread + "], \ntoken = [" + token + "], " +
                "\ntarget = [" + target + "], \nintent = [" + intent +
                "], \nrequestCode = [" + requestCode + "], \noptions = [" + options + "]");
        try {
            Method execStartActivity=Instrumentation.class.getDeclaredMethod("execStartActivity",Context.class,IBinder.class,IBinder.class,
                    Activity.class,Intent.class,int.class,Bundle.class);
            execStartActivity.setAccessible(true);
            ActivityResult result= (ActivityResult) execStartActivity.invoke(mBase,who,contextThread,token,target,intent,requestCode,options);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("musk","---------------hook：Exception---------------"+e.getMessage());
        }
        return null;
    }

}

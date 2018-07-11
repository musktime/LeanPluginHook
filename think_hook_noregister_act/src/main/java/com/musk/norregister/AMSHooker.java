package com.musk.norregister;

import android.os.Build;
import android.os.Handler;
import android.util.Log;

import com.musk.norregister.come.ActivityThreadHandlerCallBack;
import com.musk.norregister.go.IActivityManagerProxy;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

public class AMSHooker {

    public static final String EXTRA_TARGET_INTENT="extra.target.intent";
    /**
     * go AMS
     * @throws Exception
     */
    public static void hookActivityManagerNative() throws Exception {
        //拿到gDefault字段
        Field gDefaultField=null;
        if(Build.VERSION.SDK_INT>=26){
            Class ActivityManager=Class.forName("android.app.ActivityManager");
            gDefaultField=ActivityManager.getDeclaredField("IActivityManagerSingleton");
        }else {
            Class actManagerNative=Class.forName("android.app.ActivityManagerNative");
            gDefaultField=actManagerNative.getDeclaredField("gDefault");
        }
        gDefaultField.setAccessible(true);
        Object gDefault=gDefaultField.get(null);

        //拿到原始IActivityManager对象
        Class singletonClass=Class.forName("android.util.Singleton");
        Field mInstanceField=singletonClass.getDeclaredField("mInstance");
        mInstanceField.setAccessible(true);
        Object rawIActivityManager=mInstanceField.get(gDefault);

        //创建代理对象并替换掉原始对象
        Class IActivityManagerInterface=Class.forName("android.app.IActivityManager");
        Object proxy= Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),new Class[]{IActivityManagerInterface},
                new IActivityManagerProxy(rawIActivityManager));
        mInstanceField.set(gDefault,proxy);
        Log.i("musk","------hookActivityManagerNative  over-----");
    }

    /**
     * come to APP
     */
    public static void hookActivityThreadHandler() throws Exception {
        Class activityThreadClass=Class.forName("android.app.ActivityThread");
        Field sCurrentActivityThreadField=activityThreadClass.getDeclaredField("sCurrentActivityThread");
        sCurrentActivityThreadField.setAccessible(true);
        Object ActivityThread=sCurrentActivityThreadField.get(null);

        Field mHField=activityThreadClass.getDeclaredField("mH");
        mHField.setAccessible(true);
        Handler mH= (Handler) mHField.get(ActivityThread);

        Field mCallBackField=Handler.class.getDeclaredField("mCallback");
        mCallBackField.setAccessible(true);

        mCallBackField.set(mH,new ActivityThreadHandlerCallBack(mH));
        Log.i("musk","------hookActivityThreadHandler  over-----");
    }
}

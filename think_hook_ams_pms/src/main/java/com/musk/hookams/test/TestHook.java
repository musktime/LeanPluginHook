package com.musk.hookams.test;

import android.content.Context;
import android.content.pm.PackageManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class TestHook {

    public static void hookAMS() throws Exception {

        //第一步：拿到gDefault字段
        Class ActivityManagernativeClass=Class.forName("android.app.ActivityManagerNative");
        Field gDefaultField=ActivityManagernativeClass.getDeclaredField("gDefault");
        gDefaultField.setAccessible(true);
        Object gDefault=gDefaultField.get(null);

        //第二步：取出单例里面的字段
        Class singletonClass=Class.forName("android.util.Singleton");
        Field mInstanceField=singletonClass.getDeclaredField("mInstance");
        mInstanceField.setAccessible(true);
        Object rawIActivityManager=mInstanceField.get(gDefault);

        //第三步：创建代理对象并替换
        Class IActivityManagerInterface=Class.forName("android.app.IActivityManager");
        Object proxyObj=Proxy.newProxyInstance(ActivityManagernativeClass.getClassLoader(),new Class[]
                {IActivityManagerInterface},new IActivityManagerProxy(rawIActivityManager));
        mInstanceField.set(gDefault,proxyObj);
    }

    public static void hookPms(Context context) throws Exception {
        //第一步：拿到ActivityThread对象
        Class activityThreadClass=Class.forName("android.app.ActivityThread");
        Method currentActivityThread=activityThreadClass.getDeclaredMethod("currentActivityThread");
        Object activityThread=currentActivityThread.invoke(null);

        //第二步：拿到和替换sPackageManager字段
        Field sPackageManagerField=activityThreadClass.getDeclaredField("sPackageManager");
        sPackageManagerField.setAccessible(true);
        Object sPackageManager=sPackageManagerField.get(activityThread);//传递对象和null有什么区别？

        //第三步：通过代理对象替换sPackageManager字段
        Class IPacakgeManagerInterface=Class.forName("android.content.pm.IPackageManager");
        Object proxy=Proxy.newProxyInstance(activityThreadClass.getClassLoader(),new Class[]{IPacakgeManagerInterface},
                new IActivityManagerProxy(sPackageManager));
        sPackageManagerField.set(activityThread,proxy);

        //第四步：通过代理对象替换ApplicationPackageManager的字段mPm
        PackageManager packageManager=context.getPackageManager();
        Field mPmField=packageManager.getClass().getDeclaredField("mPM");
        mPmField.setAccessible(true);
        mPmField.set(packageManager,proxy);
    }
}

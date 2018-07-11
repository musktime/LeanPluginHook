package com.musk.hookams.core;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

public class HookHelper {

    public static void hookAMS() throws Exception {
        Class activityManagerNativeClass=Class.forName("android.app.ActivityManagerNative");

        Field gDefaultField=activityManagerNativeClass.getDeclaredField("gDefault");
        gDefaultField.setAccessible(true);
        Object gDefault=gDefaultField.get(null);

        Class singleton=Class.forName("android.util.Singleton");
        Field mInstanceField=singleton.getDeclaredField("mInstance");
        mInstanceField.setAccessible(true);

        //获取原始IActivityManager对象
        Object rawIActivityManager=mInstanceField.get(gDefault);

        //准备替换
        Class iActivityManagerInterface=Class.forName("android.app.IActivityManager");
        Object proxy= Proxy.newProxyInstance(activityManagerNativeClass.getClassLoader(),new Class[]{iActivityManagerInterface},
                new IActivityManagerHandler(rawIActivityManager));
        mInstanceField.set(gDefault,proxy);
    }
}

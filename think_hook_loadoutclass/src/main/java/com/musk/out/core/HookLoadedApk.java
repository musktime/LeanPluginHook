package com.musk.out.core;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

public class HookLoadedApk {

    public static void hookLoadedApk() throws Exception {
        //第一步：获取当前ActivityThread对象
        Class activityThreadClass=Class.forName("android.app.ActivityThread");
        Method currentActivityThreadMethod=activityThreadClass.getDeclaredMethod("currentActivityThread");
        currentActivityThreadMethod.setAccessible(true);
        Object activityThreadObj=currentActivityThreadMethod.invoke(null);

        //第二步：获取mPackages缓存字段
        Field mPackagesField=activityThreadClass.getDeclaredField("mPackages");
        mPackagesField.setAccessible(true);
        Map mPackages= (Map) mPackagesField.get(activityThreadObj);

        //第三步：添加插件信息到缓存字段，需要<包名 + LoadedApk对象>
        //1.如何构造LoadedApk对象??
        //         直接反射需要构造参数！
        //2.
    }
}

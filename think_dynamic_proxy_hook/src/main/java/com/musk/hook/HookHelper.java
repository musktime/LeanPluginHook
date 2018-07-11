package com.musk.hook;

import android.app.Instrumentation;
import android.util.Log;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Hook简单理解为：修改参数和返回值：
 *
 * 1.寻找Hook点【静态变量或单例】
 * 2.选择代理方式
 *      动态代理-----接口
 *      静态代理-----类【手动写代理】或cglib【可以实现类的动态代理】
 * 3.用代理对象替换原始对象
 */
public class HookHelper {

    public static void attachAppContext() throws Exception {
        Log.i("musk","--attachBaseContext--");
        //获取当前ActivityThread对象
        Class<?>activityThreadClass=Class.forName("android.app.ActivityThread");
        //获取静态方法currentActivityThread
        Method currentActivityThreadMethod=activityThreadClass.getDeclaredMethod("currentActivityThread");
        currentActivityThreadMethod.setAccessible(true);
        Object activityThread=currentActivityThreadMethod.invoke(null);

        //拿到原始的mInstrumentation字段
        Field mInstrumentationField=activityThreadClass.getDeclaredField("mInstrumentation");
        mInstrumentationField.setAccessible(true);
        Instrumentation mInstrumentation= (Instrumentation) mInstrumentationField.get(activityThread);

        //创建代理对象
        Instrumentation myInstrumentaction=new ProxyInstrumentation(mInstrumentation);

        //替换
        mInstrumentationField.set(activityThread,myInstrumentaction);
    }
}
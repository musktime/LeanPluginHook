package com.musk.hookbinder.test222;

import android.os.IBinder;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

public class Test222 {
    public static void hookClipboardService() throws Exception {

        final String CLIP_SERVICE="clipboard";

        Class serviceManager=Class.forName("android.os.ServiceManager");
        Method getService=serviceManager.getDeclaredMethod("getService",String.class);

        IBinder rawBinder= (IBinder) getService.invoke(null,CLIP_SERVICE);

        //替换
        IBinder hookedBinder= (IBinder) Proxy.newProxyInstance(serviceManager.getClassLoader(),new Class[]
                {IBinder.class},new ClicpProxy(rawBinder));

        //保存
        Field sCache=serviceManager.getDeclaredField("sCache");
        sCache.setAccessible(true);
        Map<String,IBinder>cache= (Map<String, IBinder>) sCache.get(null);
        cache.put(CLIP_SERVICE,hookedBinder);
    }
}
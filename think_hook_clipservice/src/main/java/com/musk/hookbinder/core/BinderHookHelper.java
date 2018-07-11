package com.musk.hookbinder.core;

import android.os.IBinder;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

public class BinderHookHelper {

    public static void hookClipboardService() throws Exception {
        final String CLIPBOARD_SERVICE="clipboard";

        //ServiceManager.getService("clipboard");
        Class<?>serviceManager=Class.forName("android.os.ServiceManager");
        Method getService=serviceManager.getDeclaredMethod("getService",String.class);

        //serviceManager里面管理的原始Clipboard Binder对象，一般是Binder代理对象
        IBinder rawBinder= (IBinder) getService.invoke(null,CLIPBOARD_SERVICE);

        //hook掉这个Binder代理对象的queryLocalInterface方法
        IBinder hookedBinder= (IBinder) Proxy.newProxyInstance(serviceManager.getClassLoader(),new Class[]
                {IBinder.class},new BinderProxyHookHandler(rawBinder));

        //把hook过的Binder对象放进ServiceManager的缓存cache中
        Field cacheField=serviceManager.getDeclaredField("sCache");
        cacheField.setAccessible(true);
        Map<String,IBinder>cache= (Map) cacheField.get(null);
        cache.put(CLIPBOARD_SERVICE,hookedBinder);
    }
}

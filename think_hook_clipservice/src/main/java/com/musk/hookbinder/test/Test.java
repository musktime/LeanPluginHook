package com.musk.hookbinder.test;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.IBinder;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

public class Test {

    public static void hookClipService() throws Exception {

        final String CLIP_SERVICE="clipboard";

        Class<?>serviceManager=Class.forName("android.os.ServiceManager");
        Method getService=serviceManager.getDeclaredMethod("getService",String.class);

        //ServiceManager.getService(name)获得IBinder接口
        IBinder rawBinder= (IBinder) getService.invoke(null,CLIP_SERVICE);

        //通过asInterface把Binder替换成代理Binder
        IBinder hookedBinder= (IBinder) Proxy.newProxyInstance(serviceManager.getClassLoader(),new Class[]{IBinder.class},
                new ClipBinderProxy(rawBinder));

        //把代理Binder设置给缓存
        Field sCacheField=serviceManager.getDeclaredField("sCache");
        sCacheField.setAccessible(true);
        Map<String,IBinder>cache= (Map<String, IBinder>) sCacheField.get(null);
        cache.put(CLIP_SERVICE,hookedBinder);
    }

    private static void useClipService(Context context){
        ClipboardManager manager= (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        manager.getPrimaryClip();
    }
}

package com.musk.hookbinder.core;

import android.content.ClipData;
import android.os.IBinder;
import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 伪造系统剪切板服务对象
 *
 * 1.getService方法返回的IBinder实际上是一个裸Binder代理对象，它只有与驱动打交道的能力，但是它并不能独立工作，需要人指挥它
 *
 * 2.asInterface方法返回的IClipboard.Stub.Proxy类的对象通过操纵这个裸BinderProxy对象从而实现了具体的IClipboard接口定义的操作。
 */
public class BinderHookHandler implements InvocationHandler {

    private static final String TAG=BinderHookHandler.class.getSimpleName();

    //原始的service对象(IInterface)
    Object mBase;

    /**
     * 构造调用：IClipboard.Stub.asInterface(base)
     */
    public BinderHookHandler(IBinder base, Class<?>stubClass) {
        try {
            Method asInterfaceMethod=stubClass.getDeclaredMethod("asInterface",IBinder.class);
            //IClipboard.Stub.asInterface(base)
            mBase=asInterfaceMethod.invoke(null,base);
        }  catch (Exception e) {
            throw new RuntimeException("hooked failed!"+e.getMessage());
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //把剪切板内容替换为"you are hooked"
        if("getPrimaryClip".equals(method.getName())){
            Log.i("musk","----getPrimaryClip----");
            return ClipData.newPlainText(null,"you are hooked");
        }
        //欺骗系统使系统认为剪切板一直有内容
        if("hasPrimaryClip".equals(method.getName())){
            return true;
        }
        return method.invoke(mBase,args);
    }
}
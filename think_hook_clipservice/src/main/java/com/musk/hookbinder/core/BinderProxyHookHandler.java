package com.musk.hookbinder.core;

import android.os.IBinder;
import android.os.IInterface;
import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 伪造IBinder对象
 *
 * 由于ServiceManager里面的sCache里面存储的 IBinder类型基本上都是BinderProxy
 *
 * 上一步伪造好系统服务对象
 * 现在想办法让asInterface返回伪造的系统服务对象
 * 所以要伪造一个IBinder对象
 */
public class BinderProxyHookHandler implements InvocationHandler {

    private static final String TAG="BinderProxyHookHandler";

    IBinder mBase;
    Class<?>mStub;
    Class<?>iinterface;

    public BinderProxyHookHandler(IBinder base) {
        mBase = base;
        try {
            mStub=Class.forName("android.content.IClipboard$Stub");
            iinterface=Class.forName("android.content.IClipboard");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if("queryLocalInterface".equals(method.getName())){
            Log.i("musk","----queryLocalInterface-----");
            //这里直接返回被hook掉的service接口
            return Proxy.newProxyInstance(proxy.getClass().getClassLoader(),new Class[]{IBinder.class,
                    IInterface.class,iinterface},new BinderHookHandler(mBase,mStub));
        }
        return method.invoke(proxy,method,args);
    }
}

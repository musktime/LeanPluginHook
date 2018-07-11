package com.musk.hookbinder.test;

import android.os.IBinder;

import com.musk.hookbinder.core.BinderHookHandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ClipBinderProxy implements InvocationHandler{

    IBinder base;
    Class stub;
    Class iinterface;

    public ClipBinderProxy(IBinder base) {
        this.base=base;
        try {
            stub=Class.forName("android.content.IClipboard$Stub");
            iinterface=Class.forName("android.content.IClipboard");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if("queryLocalInterface".equals(method.getName())){
            return Proxy.newProxyInstance(Proxy.class.getClass().getClassLoader(),new Class[]{iinterface},
                    new ClipBoard(stub,base));
        }
        return method.invoke(base,args);
    }
}
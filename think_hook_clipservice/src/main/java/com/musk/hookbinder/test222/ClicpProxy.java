package com.musk.hookbinder.test222;

import android.os.IBinder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ClicpProxy implements InvocationHandler{

    IBinder base;
    Class stub;
    Class iinterface;
    public ClicpProxy(IBinder base) {
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
            return Proxy.newProxyInstance(stub.getClassLoader(),new Class[]{iinterface},new Clip(base,stub));
        }
        return method.invoke(base,args);
    }
}

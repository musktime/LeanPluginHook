package com.musk.hookbinder.test;

import android.content.ClipData;
import android.os.IBinder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ClipBoard implements InvocationHandler {

    Object base;

    public ClipBoard(Class stub, IBinder base) {
        try {
            Method asInterfaceMethod=stub.getDeclaredMethod("asInterface");
            this.base=asInterfaceMethod.invoke(null,base);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if("getPrimaryClip".equals(method.getName())){
            return ClipData.newPlainText(null,"fadlkkxm,fxzxcsdsa");
        }
        if("hasPrimaryClip".equals(method.getName())){
            return true;
        }
        return method.invoke(base,args);
    }
}
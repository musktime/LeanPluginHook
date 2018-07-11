package com.musk.hookams.test;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

class IActivityManagerProxy implements InvocationHandler {

    public Object base;

    public IActivityManagerProxy(Object base) {
        this.base=base;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Log.i("musk","---------method---------"+method.getName());
        return method.invoke(base,args);
    }
}

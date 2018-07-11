package com.musk.hookams.core;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

public class IActivityManagerHandler implements InvocationHandler {

    final String TAG=IActivityManagerHandler.class.getSimpleName();

    Object base;

    public IActivityManagerHandler(Object base) {
        this.base=base;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Log.i(TAG,"---method---"+method.getName());
        Log.i(TAG,"---args---"+ Arrays.toString(args));
        return method.invoke(base,args);
    }
}

package com.musk.hookbinder.test222;

import android.content.ClipData;
import android.os.IBinder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * asInterface
 */
public class Clip implements InvocationHandler {

    Object base;

    public Clip(IBinder base,Class stub) {
        try {
            Method asInterfaceMethod=stub.getDeclaredMethod("asInterface",IBinder.class);
            this.base=asInterfaceMethod.invoke(null,base);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if("getPrimaryClip".equals(method.getName())){
            return ClipData.newPlainText(null,"dsafsddagfd");
        }
        if("hasPrimaryClip".equals(method.getName())){
            return true;
        }
        return method.invoke(base,args);
    }
}

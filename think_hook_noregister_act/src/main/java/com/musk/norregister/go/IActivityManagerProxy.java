package com.musk.norregister.go;

import android.content.ComponentName;
import android.content.Intent;
import android.util.Log;
import com.musk.norregister.AMSHooker;
import com.musk.norregister.StubActivity;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class IActivityManagerProxy implements InvocationHandler {

    Object base;

    public IActivityManagerProxy(Object rawIActivityManager) {
        base=rawIActivityManager;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if("startActivity".equals(method.getName())){

            //拿到原始Intent信息
            int index=0;
            for (int i=0;i<args.length;i++){
                if(args[i] instanceof Intent){
                    index=i;
                }
            }
            Intent rawIntent= (Intent) args[index];

            //保存原来启动的activity信息
            Intent newIntent=new Intent();
            newIntent.putExtra(AMSHooker.EXTRA_TARGET_INTENT,rawIntent);

            //临时替换为替身Activity信息
            String stubPackage="com.musk.norregister";
            ComponentName componentName=new ComponentName(stubPackage, StubActivity.class.getName());
            newIntent.setComponent(componentName);
            args[index]=newIntent;
            Log.i("musk","-----hook OK-----");
            return method.invoke(base,args);
        }
        return method.invoke(base,args);
    }
}

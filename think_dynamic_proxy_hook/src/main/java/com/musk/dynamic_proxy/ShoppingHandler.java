package com.musk.dynamic_proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ShoppingHandler implements InvocationHandler {

    Object mBase;

    public ShoppingHandler(Object base){
        mBase=base;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if("doShopping".equals(method.getName())){
            //此处代理shopping接口的对象
            long money=(long)args[0];

            //修改参数
            long readCost=(long)(money*0.5);
            System.out.println("代理购买花费："+readCost);

            //买东西
            Object[]things=(Object[]) method.invoke(mBase,readCost);

            //修改结果
            if(things!=null&&things.length>0){
                things[0]="代理替换的东西";
            }
            return things;
        }

        //做别的事情
        if("doSomeThing".equals(method.getName())){

            return null;
        }
        return null;
    }
}

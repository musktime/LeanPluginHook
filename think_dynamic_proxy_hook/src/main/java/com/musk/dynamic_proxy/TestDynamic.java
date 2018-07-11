package com.musk.dynamic_proxy;

import com.musk.IShopping;
import com.musk.ShoppingImpl;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 *JDK动态代理：需要处理InvokeHandler和Proxy
 *
 * JVM在运行时帮我们动态生成一系列代理类
 * 而且JDK动态代理只支持接口
 */
public class TestDynamic {
    public static void main(String[]args){
        IShopping woman=new ShoppingImpl();

        //原始购买
        Object[]things=woman.doShopping(100);
        System.out.println("原始购买："+ Arrays.toString(things));

        //换代理
        woman= (IShopping) Proxy.newProxyInstance(IShopping.class.getClassLoader(),woman.getClass().getInterfaces(),new ShoppingHandler(woman));

        //代理购买
        things=woman.doShopping(100);
        System.out.println("代理购买："+Arrays.toString(things));
    }
}

package com.musk.static_proxy;

import com.musk.IShopping;
import com.musk.ShoppingImpl;
import java.util.Arrays;
/**
 * 代理可以实现方法增强、方法拦截、修改原方法的参数和返回结果
 * <p/>
 * 1.针对类或接口，构造代理对象
 * 2.用代理对象替换原始对象
 *
 * <p/>
 * 静态代理的缺点：需要为每个类手写每一个代理类
 */
public class TestStatic {
    public static void main(String[]args){
        //原始购买者
        IShopping woman=new ShoppingImpl();
        //原始购买
        Object[]results=woman.doShopping(100);
        System.out.println("原始购买的东西："+Arrays.toString(results));

        //换成代理购买
        woman=new ProxyShopping(woman);
        //代理购买
        results=woman.doShopping(100);
        System.out.println("代理购买的东西："+Arrays.toString(results));
    }
}

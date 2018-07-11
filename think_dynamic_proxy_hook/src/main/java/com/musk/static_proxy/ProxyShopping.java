package com.musk.static_proxy;

import com.musk.IShopping;

public class ProxyShopping implements IShopping{

    IShopping mBase;
    public ProxyShopping(IShopping base){
        mBase=base;
    }

    @Override
    public Object[] doShopping(long money) {
        //修改参数
        long readCost=(long)(money*0.5);

        //执行购买
        Object[]things=mBase.doShopping(readCost);

        //修改结果
        if(things!=null&&things.length>0){
            things[0]="调包买别的东西";
        }
        return things;
    }
}
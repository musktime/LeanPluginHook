package com.musk;

import com.musk.static_proxy.TestStatic;

public class ShoppingImpl implements IShopping {
    @Override
    public Object[] doShopping(long money) {
        System.out.println("==SHoppingImpl==花费：=="+money);
        return new Object[]{"衣服","鞋子","零食"};
    }
}

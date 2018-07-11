package com.musk.think_class;


public class Test {
    public static void main(String[]args){
//        MyClassLoader loader=new MyClassLoader();
//
//        PathClassLoader loader1;

        test(Test.class.getClassLoader());
    }

    public static void test(ClassLoader sourceLoader){
        ClassLoader loader = sourceLoader;    //获得加载ClassLoaderTest.class这个类的类加载器
        while(loader != null) {
            System.out.println("==1=="+loader);
            loader = loader.getParent();    //获得父类加载器的引用
        }
        System.out.println("==2=="+loader);
    }
}

package com.musk.think_class;

public class MyClassLoader extends ClassLoader {
    @Override
    protected Class<?> findClass(String name) {
        Class clazz=null;
        byte[]data=getClassData(name);
        clazz=defineClass(name,data,0,data.length);
        return clazz;
    }

    private byte[]getClassData(String name){

        return new byte[1024];
    }
}

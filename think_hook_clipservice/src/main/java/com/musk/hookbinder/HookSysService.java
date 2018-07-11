package com.musk.hookbinder;

/**
 * 系统服务的获取：
 * 系统各种远程Service对象都是以Binder形式存在的，Binder有管理者ServiceManager，Hook系统服务从ServiceManager入手。
 *
 * 1.ContextImpl.getSystemService("service_name")
 * 2.所有的Service对象保存在HashMap中SYSTEM_SERVICE_MAP。
 * 3.ContextImpl静态代码块里注册了系统服务
 */

/**
 * 系统服务的使用：
 *
 * 1.获得原始的Binder对象
 *      IBinder b=ServiceManager.getSystemService("service_name")
 * 2.转化为service接口
 *      IXXInterface in=IXXInterface.stub.asInterface(b)
 *
 *      -----------------------asInterface(android.os.IBinder obj)----------------------
 *      if ((obj == null)) {
            return null;
        }

        android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR); // Hook点

        if (((iin != null) && (iin instanceof android.content.IClipboard))) {
            return ((android.content.IClipboard) iin);
        }
        return new android.content.IClipboard.Stub.Proxy(obj);
        -----------------------asInterface(android.os.IBinder obj)----------------------
 * 3.确认Hook点：
 *      修改asInterface的返回对象为修改后的对象
 */
public class HookSysService {
}

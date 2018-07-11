package com.musk.norregister.come;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.musk.norregister.AMSHooker;
import java.lang.reflect.Field;

public class ActivityThreadHandlerCallBack implements Handler.Callback {

    Handler base;

    public ActivityThreadHandlerCallBack(Handler rawHandler) {
        base=rawHandler;
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what){
            case 100:
                handleLaunchActivity(msg);
                break;
        }
        base.handleMessage(msg);
        return true;
    }

    private void handleLaunchActivity(Message msg){
        Object obj=msg.obj;
        try {
            Field intent=obj.getClass().getDeclaredField("intent");
            intent.setAccessible(true);
            Intent raw= (Intent) intent.get(obj);
            Intent target=raw.getParcelableExtra(AMSHooker.EXTRA_TARGET_INTENT);
            raw.setComponent(target.getComponent());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}

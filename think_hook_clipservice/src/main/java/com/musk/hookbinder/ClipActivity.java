package com.musk.hookbinder;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;
import com.musk.hookbinder.core.BinderHookHelper;
import com.musk.hookbinder.test.Test;
import com.musk.hookbinder.test222.Test222;

public class ClipActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            //BinderHookHelper.hookClipboardService();
            //Test222.hookClipboardService();
            Test.hookClipService();
        } catch (Exception e) {
            e.printStackTrace();
        }
        EditText text=new EditText(this);
        setContentView(text);
    }
}

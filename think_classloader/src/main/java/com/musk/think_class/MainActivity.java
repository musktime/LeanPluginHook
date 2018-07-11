package com.musk.think_class;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import dalvik.system.PathClassLoader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ClassLoader classLoader=getClassLoader();
        PathClassLoader loader;
        Log.i("musk","==classLoader=="+classLoader.toString());
        while (classLoader!=null){
            Log.i("musk","==parent=="+classLoader.toString());
            classLoader=classLoader.getParent();
        }
        Test.test(MainActivity.class.getClassLoader());
    }
}

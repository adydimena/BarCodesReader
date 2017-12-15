package com.example.ady.barcodesreader;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by Ady on 12/14/2017.
 */

public class LeaksdectectionsClass extends Application {

    public RefWatcher refWatcher;

    public static RefWatcher getRefWatcher(Context context) {
        LeaksdectectionsClass application = (LeaksdectectionsClass) context.getApplicationContext();
        return application.refWatcher;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        enabledStrictMode();
        refWatcher = LeakCanary.install(this);
        // Normal app init code...
    }
       // refWatcher = LeakCanary.install(this);
   // }
       public static void enabledStrictMode() {
           StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder() //
                   .detectAll() //
                   .penaltyLog() //
                   .penaltyDeath() //
                   .build());
       }

}

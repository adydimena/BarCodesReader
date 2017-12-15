package com.example.ady.barcodesreader;

import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by Ady on 12/14/2017.
 */

public class SingletonSavesContext {
    private Context context;
    private static SingletonSavesContext instance;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public static SingletonSavesContext getInstance() {
        if (instance == null) {
            instance = new SingletonSavesContext();
        }
        return instance;
    }

}

package com.tt.frameworkdemo;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by TT on 2017/1/2.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //init
        Fresco.initialize(this);

        Realm.init(this);
        // The Realm file will be located in Context.getFilesDir() with name "default.realm"
        //RealmConfiguration config = new RealmConfiguration.Builder().build();


        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(config);
    }
}

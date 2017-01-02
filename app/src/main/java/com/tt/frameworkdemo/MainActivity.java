package com.tt.frameworkdemo;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.facebook.drawee.view.SimpleDraweeView;
import com.tt.frameworkdemo.entity.UserInfo;

import java.util.function.Consumer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmModel;
import io.realm.RealmResults;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.img)SimpleDraweeView img;
    @BindView(R.id.query)Button query;
    RealmResults<UserInfo> us;
    Realm realm = Realm.getDefaultInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //butterknife bind
        ButterKnife.bind(this);

        Uri uri = Uri.parse("res://"+getPackageName()+"/"+R.drawable.defaulticon);
        img.setImageURI(uri);


        realm.where(UserInfo.class).equalTo("id",1).findAll()
                .asObservable()
                .doOnNext(uuu ->
                {
                    System.out.println("---检测到更新");
                    for(UserInfo u:uuu){
                        System.out.println(u);
                 }
                })
                .subscribe(uuu -> {for(UserInfo u:uuu){
                    System.out.println(u);
        }});

        /*realm.where(UserInfo.class).equalTo("id",1).findAllAsync()
                .addChangeListener(new RealmChangeListener<RealmResults<UserInfo>>() {
            @Override
            public void onChange(RealmResults<UserInfo> element) {
                System.out.println("---检测到更新");
                us = element;
                for(UserInfo u:element){
                    System.out.println(u);
                }
            }
        });
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                us.get(0).setName("dddd");
            }
        });*/

        /*realm.executeTransactionAsync(
                r->{
                    System.out.println(us==null?"null---before":us.get(0)+"-----before");},
                ()->realm.close());

        us.addChangeListener(new RealmChangeListener<RealmResults<UserInfo>>() {
            @Override
            public void onChange( RealmResults<UserInfo> us) {
                System.out.println("----检测读取完毕--------");
                us.get(0).setName("ccc");
            }
        });*/


    }
    @OnClick(R.id.query)
    public void q(){
        RealmResults<UserInfo> results = realm.where(UserInfo.class).findAll();

        for(UserInfo u:results){
            System.out.println(u);
        }
    }
}

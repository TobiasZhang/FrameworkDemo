package com.tt.frameworkdemo.entity;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by TT on 2017/1/2.
 */
public class UserInfo extends RealmObject {
    @PrimaryKey
    private Integer id;
    @SerializedName(value = "name",alternate = {"nickname"})
    private String nickname;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}

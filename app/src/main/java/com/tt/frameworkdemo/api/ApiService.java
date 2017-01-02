package com.tt.frameworkdemo.api;

import com.tt.frameworkdemo.entity.UserInfo;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by TT on 2016/11/29.
 */
public interface ApiService {
    //原生retrofit2
    @GET("old")
    Call<ResponseBody> getOld(@Query("id") int id);
    //retrofit2 + rxjava
    /*@POST("mix/getTuiJian")
    Observable<ApiResult<List<TuiJian>>> getTuiJian(@Field("uid") int id);*/
    @POST("user/getall/")
    Observable<ApiResult<List<UserInfo>>> getTest();
}

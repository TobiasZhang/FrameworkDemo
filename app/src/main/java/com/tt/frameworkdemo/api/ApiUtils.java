package com.tt.frameworkdemo.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by TT on 2017/01/02.
 */
public class ApiUtils {
    public static final String BASE_URL = "http://192.168.1.102:8080";
    private static final String RESULT_OK = "ok";
    private static final int DEFAULT_TIMEOUT = 5;

    private ApiService apiServiceImpl;
    public ApiService getApiServiceImpl() {
        return apiServiceImpl;
    }

    //构造方法私有
    private ApiUtils(){
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        apiServiceImpl = retrofit.create(ApiService.class);
    }
    //在访问HttpMethods时创建单例
    private static final ApiUtils INSTANCE = new ApiUtils();
    //获取单例
    public static ApiUtils getInstance(){
        return INSTANCE;
    }

    /**
     * 请求Api数据
     * @param o getApiServiceImpl().xxx()
     * @param <T>   data type
     */
    public<T> void getApiData(Observable<ApiResult<T>> o,Subscriber<T> s){
        addThreadSchedulers(o.map(new HttpResultFunc<>())).subscribe(s);
    }
    public<T> void getApiData(Observable<ApiResult<T>> o,Action1<T> onNext){
        addThreadSchedulers(o.map(new HttpResultFunc<>())).subscribe(onNext);
    }
    public<T> void getApiData(Observable<ApiResult<T>> o,Action1<T> onNext,Action1<Throwable> onError){
        addThreadSchedulers(o.map(new HttpResultFunc<>())).subscribe(onNext,onError);
    }

    /**
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     *
     * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
    private class HttpResultFunc<T> implements Func1<ApiResult<T>, T> {

        @Override
        public T call(ApiResult<T> apiResult) {
            if (!apiResult.getResult().equals(RESULT_OK)) {
                throw new ApiException(apiResult.getResult());
            }
            return apiResult.getData();
        }
    }

    //添加线程管理
    private<T> Observable<T> addThreadSchedulers(Observable<T> o){
        return o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}

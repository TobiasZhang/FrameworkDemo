package com.tt.frameworkdemo;

import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        //assertEquals(4, 2 + 2);

        Observable.OnSubscribe<String> subscribe = sub -> sub.onNext("123");
        Observable.create(subscribe)
                .map(s -> Integer.parseInt(s))
                .subscribe(System.out::println);
    }

    @Test
    public void test1(){
        System.out.println("============start================");
        Observable.create(subscriber -> {
            try {
                Thread.sleep(1000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int i = 0;
            for(int x = 0; x < 20; x++){
                i++;
                System.out.println("a---process---"+i);
            }
            subscriber.onNext(i);
        })
                .subscribeOn(Schedulers.io())
                .subscribe(integer -> {
            System.out.println("a---ok---");
        });
        Observable.create(subscriber -> {
            int i = 0;
            for(int x = 0; x < 2000; x++){
                i++;
                System.out.println("b---process---"+i);
            }
            subscriber.onNext(i);
        })
                .subscribeOn(Schedulers.io())
                .subscribe(integer -> {
            System.out.println("b---ok---");
        });
    }
    @Test
    public void test2(){
        FutureTask<Integer> ft = new FutureTask<Integer>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                int i = 0;
                Thread.sleep(2000l);
                for(; i < 200; i++){}
                return i;
            }
        });
        new Thread(
           ft
        ).start();

        System.out.println("-------mainThread--------");
        try {
            System.out.println(ft.get()+"---------");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
    @Test
    public void test3() throws ExecutionException, InterruptedException {
        System.out.println("-----start-----------");
        ExecutorService es = Executors.newFixedThreadPool(5);
        Future<Integer> future = es.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Thread.sleep(5000);
                return 1;
            }
        });
        System.out.println("main---thread----------------");

        System.out.println("main---thread-"+future.get());

        System.out.println("-----end-----------");

    }
    @Test
    public void test4() throws ExecutionException, InterruptedException {
        System.out.println("-----start-----------");
        ExecutorService es = Executors.newFixedThreadPool(5);
        CompletionService<Integer> cs = new ExecutorCompletionService<>(es);
        for(int i = 0; i < 5 ; i++){
            int target = i;
            cs.submit(() -> {
                System.out.println("|-----in-----|-"+target);Thread.sleep(50-target*10);return target;});
        }
        for(int i = 0; i < 5 ;){
//            Future<Integer> future1 = cs.take();
//            System.out.println(future1.get()+"-----------");
//            i++;
            Future<Integer> future1 = cs.poll();
            if(future1 == null){
                System.out.println("------获取中----------");
            }else{
                System.out.println("-------ok------------============================"+future1.get());
                i++;
            }
        }
        System.out.println("-----end-----------");

    }

    @Test
    public void a(){
        int a = 0;
    }
    @Test
    public void func(){
        /*Observable.just(getInt())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        System.out.println("获的结果---"+integer);
                    }
                });*/
        Observable.just(hehe())
                .subscribe(pVoid -> System.out.println("执行结束"));
    }
    int getInt() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 10;
    }
    Void hehe() {
        System.out.println("--do something--");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
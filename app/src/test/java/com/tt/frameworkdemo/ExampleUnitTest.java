package com.tt.frameworkdemo;

import org.junit.Test;

import rx.Observable;
import rx.Subscriber;

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
}
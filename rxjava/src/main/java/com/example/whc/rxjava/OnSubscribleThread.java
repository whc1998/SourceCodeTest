package com.example.whc.rxjava;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by WSY on 2018/5/12.
 */

public class OnSubscribleThread<T> implements OnSubscrible<T> {

    private static ExecutorService executorService= Executors.newCachedThreadPool();
    Observable<T> source;

    public OnSubscribleThread(Observable<T> source) {
        this.source = source;
    }

    @Override
    public void call(final Subscrible<? super T> subscrible) {
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                source.subscrible(subscrible);
            }
        };
        executorService.execute(runnable);
    }
}

package com.example.whc.rxjava;

/**
 * Created by WSY on 2018/5/12.
 * 黑屋子  平安夜
 * T 代表看电影
 */

public class Observable<T> {
    /**
     * 男生
     */
    private OnSubscrible<T> onSubscrible;

    public Observable(OnSubscrible<T> onSubscrible) {
        this.onSubscrible = onSubscrible;
    }

    public static <T> Observable<T> create(OnSubscrible<T> onSubscrible) {
        return new Observable<T>(onSubscrible);
    }

    public void subscrible(Subscrible<? super T> subscrible) {
        onSubscrible.call(subscrible);
    }

    public <R> Observable<R> map(Func1<? super T, ? extends R> func1) {
        return new Observable<R>(new OnSubscribleLift<T,R>(onSubscrible, func1));
    }

    public Observable<T> subscribOnIo() {
        return create(new OnSubscribleThread<T>(this));
    }

}

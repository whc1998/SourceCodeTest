package com.example.whc.rxjava;

/**
 * Created by WSY on 2018/5/12.
 *
 * 男生
 *
 * T   看电影
 * Subscrible 女生
 * Subscrible<? super T> 看电影的女生
 *
 * super  用于参数类型限定，不能用于返回参数的的限定 void get(? super T)
 * extends 用于返回参数的限定，不能用于参数类型限定
 *
 */

public interface OnSubscrible<T> extends Action1<Subscrible<? super T>> {



}

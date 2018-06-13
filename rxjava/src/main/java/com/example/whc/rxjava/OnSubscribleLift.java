package com.example.whc.rxjava;

/**
 * Created by WSY on 2018/5/12.
 * 好兄弟
 * 事件变换
 * 故事：找好兄弟接老婆，他老婆把闺蜜拉下水
 *
 * T 男生想找一个看电影的女生
 * R 可以看电影的女生
 */

public class OnSubscribleLift<T,R>  implements OnSubscrible<R> {

    /**
     * 男生
     */
    OnSubscrible<T> boy;
    private Func1<? super T,? extends R> transfromer;

    public OnSubscribleLift(OnSubscrible<T> boy, Func1<? super T, ? extends R> transfromer) {
        this.boy = boy;
        this.transfromer = transfromer;
    }

    /**
     * 想看电影的女生
     * @param subscrible
     */
    @Override
    public void call(Subscrible<? super R> subscrible) {
        Subscrible<? super T> wife=new OperaChange<>(subscrible,transfromer);
        boy.call(wife);
    }

    /**
     * 好兄弟老婆
     * @param <T>
     * @param <R>
     */
    class OperaChange<T,R> extends Subscrible<T>{

        Subscrible<? super R> actual;
        private Func1<? super T,? extends R> transfrom;

        public OperaChange(Subscrible<? super R> actual, Func1<? super T, ? extends R> transfrom) {
            this.actual = actual;
            this.transfrom = transfrom;
        }

        @Override
        public void onNext(T t) {
            /**
             * 自己替换闺蜜
             */
            R r=this.transfrom.call(t);
            actual.onNext(r);
        }
    }

}

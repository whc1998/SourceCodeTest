package com.example.whc.testanimation;

import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.bt_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performAnimation(view);
            }
        });

        findViewById(R.id.bt_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performAmation(view,100,500);
            }
        });

    }

    private void performAnimation(View view){
        ViewWrapper viewWrapper=new ViewWrapper(view);
        ObjectAnimator.ofInt(viewWrapper,"width",500).setDuration(5000).start();
    }

    private void performAmation(final View target,final int start,final int end){
        ValueAnimator valueAnimator=ValueAnimator.ofInt(1,100);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            private IntEvaluator mEvaluator=new IntEvaluator();
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //持有一个IntEvaluator对象，方便下面估值的时候使用
                int currentValue= (int) valueAnimator.getAnimatedValue();
                Log.d("test","current value:"+currentValue);
                float fraction=valueAnimator.getAnimatedFraction();
                target.getLayoutParams().width=mEvaluator.evaluate(fraction,start,end);
                target.requestLayout();
            }
        });

        valueAnimator.setDuration(5000).start();
    }

    private static class ViewWrapper{

        private View mTarget;

        public ViewWrapper(View mTarget){
            this.mTarget=mTarget;
        }

        public int getWidth(){
            return mTarget.getLayoutParams().width;
        }

        public void setWidth(int width){
            mTarget.getLayoutParams().width=width;
            mTarget.requestLayout();
        }

    }

}

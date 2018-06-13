package com.example.whc.hotfix.Test;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by WSY on 2018/4/9.
 */

public class MyTestClass {

    public void testfix(Context context){
        int i=10;
        int a=0;
        Toast.makeText(context, "shit : "+i/a, Toast.LENGTH_SHORT).show();
    }

}

package com.example.whc.hotfix.FixUtil;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.HashSet;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

/**
 * Created by WSY on 2018/4/9.
 */

public class FixDexUtils {

    private static HashSet<File> loadedDex = new HashSet<>();

    static {
        loadedDex.clear();
    }

    public static void loadFixedDex(Context context) {
        if (context == null) {
            return;
        }
        File fileDir = context.getDir(MyConstants.DEX_DIR, Context.MODE_PRIVATE);
        File[] files = fileDir.listFiles();
        for (File file : files) {
            Log.d("test",file.getName());
            if (file.getName().startsWith("MyTestClass") && file.getName().endsWith(".dex")) {
                loadedDex.add(file);
            }
        }
        //和之前apk里面的dex合并
        doDexInject(context, fileDir, loadedDex);
        Toast.makeText(context, "修复成功", Toast.LENGTH_SHORT).show();
    }

    private static void doDexInject(Context context, File filesDir, HashSet<File> loadedDex) {
        String optimizeDir = filesDir.getAbsolutePath() + File.separator + "opt_dex";
        File fopt = new File(optimizeDir);
        if (!fopt.exists()) {
            fopt.mkdirs();
        }
        //加载应用程序的dex
        //1.拿到系统的dex
        PathClassLoader pathloader = (PathClassLoader) context.getClassLoader();
        //2.拿到自己的dex
        for (File dex : loadedDex) {
            DexClassLoader classLoader = new DexClassLoader(dex.getAbsolutePath(),
                    fopt.getAbsolutePath(), null, pathloader);
            //合并
            /**
             * BaseDexClassLoader-->DexPathList
             * DexPathList-->Element[] dexElements
             * 把Element[] dexElements改了一一合并
             */

            try {
                Object dexObj = getPathList(classLoader);
                Object pathObj = getPathList(pathloader);
                Object mDexElementsList=getDexElements(dexObj);
                Object pathDexElementsList=getDexElements(pathObj);

                //合并
                Object dexElements = combineArray(mDexElementsList, pathDexElementsList);
                //需要重新赋值给Element[] dexElements
                Object pathList=getPathList(pathloader);
                setField(pathList,pathList.getClass(),"dexElements",dexElements);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private static Object getDexElements(Object dexObj) throws NoSuchFieldException, IllegalAccessException {
        return getField(dexObj,dexObj.getClass(),"dexElements");
    }

    private static Object getField(Object obj, Class<?> cl, String field)
            throws NoSuchFieldException, IllegalAccessException {
        //获取到baseDexClassLoader里面名字叫做field的成员
        Field localField = cl.getDeclaredField(field);
        localField.setAccessible(true);
        return localField.get(obj);
    }

    private static void setField(Object obj,Class<?> cl,String field,Object value)
            throws NoSuchFieldException, IllegalAccessException {
        Field localField=cl.getDeclaredField(field);
        localField.setAccessible(true);
        localField.set(obj,value);
    }

    private static Object getPathList(Object baseDexClassLoader) throws Exception {
        return getField(baseDexClassLoader,Class.forName("dalvik.system.BaseDexClassLoader"),"pathList");
    }

    //两个集合的合并
    private static Object combineArray(Object arryLhs,Object arryRhs){
        Class<?> localClass=arryLhs.getClass().getComponentType();
        int i= Array.getLength(arryLhs);
        int j=i+Array.getLength(arryRhs);
        Object result=Array.newInstance(localClass,j);
        for (int k=0;k<j;++k){
            if (k<i){
                Array.set(result,k,Array.get(arryLhs,k));
            }else{
                Array.set(result,k,Array.get(arryRhs,k-i));
            }
        }
        return result;
    }

}

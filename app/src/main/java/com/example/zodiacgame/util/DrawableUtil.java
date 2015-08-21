package com.example.zodiacgame.util;

import com.example.zodiacgame.R;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2015/8/21.
 */
public class DrawableUtil {

    public static int getResID(String name){
        int resID = 0;
        Class drawable = R.mipmap.class;
        Field field = null;
        try {
            field = drawable.getField(name);
            resID = field.getInt(field.getName());
        }catch (Exception e){}
        return resID;
    }
}

package com.example.zodiacgame.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2015/8/21.
 */
public class ScoreUtil {

    private static final String SP_NAME = "score";

    private static Context mContext;
    private static SharedPreferences mPreferences;
    private static SharedPreferences.Editor mEditor;
    public static void init(Context context){
        mContext = context;
        mPreferences = mContext.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();
    }
    public static int getCurrentScore(){
        return mPreferences.getInt("current",0);
    }

    public static void setCurrentScore(int score){
        mEditor.putInt("current",score);
        mEditor.commit();
    }

    public static int getMaxScore(){
        return mPreferences.getInt("max",0);
    }

    public static void setMaxScore(int score){
        int maxScore = getMaxScore();
        if(maxScore>score)
            return;
        mEditor.putInt("max",score);
        mEditor.commit();
    }
}

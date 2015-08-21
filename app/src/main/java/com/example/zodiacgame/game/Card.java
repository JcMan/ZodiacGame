package com.example.zodiacgame.game;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zodiacgame.util.DrawableUtil;

/**
 * Created by Administrator on 2015/8/21.
 */
public class Card extends FrameLayout{

    private static final int COLOR_0 = Color.parseColor("#DAE7EF");
    private static final int COLOR_1 = Color.parseColor("#EEE4DA");
    private static final int COLOR_2 = Color.parseColor("#EDE0C8");
    private static final int COLOR_3 = Color.parseColor("#F59E74");
    private static final int COLOR_4 = Color.parseColor("#F94762");
    private static final int COLOR_5 = Color.parseColor("#F57B5E");
    private static final int COLOR_6 = Color.parseColor("#F65C39");
    private static final int COLOR_7 = Color.parseColor("#EDCF72");
    private static final int COLOR_8 = Color.parseColor("#EDCC61");
    private static final int COLOR_9 = Color.parseColor("#EEE4DA");
    private static final int COLOR_10 = Color.parseColor("#EDC850");
    private static final int COLOR_11 = Color.parseColor("#B884AC");
    private static final int COLOR_12 = Color.parseColor("#B26EA9");

    private int mNum;
    private TextView mLabel;
    private ImageView mLabelView;
    private int mType = 1;

    public Card(Context context){
        super(context);
        mLabel = new TextView(getContext());
        mLabelView = new ImageView(getContext());
        mLabelView.setScaleType(ImageView.ScaleType.FIT_XY);
        mLabelView.setBackgroundColor(0xafDAE7EF);
        LayoutParams params = new LayoutParams(-1,-1);
        params.setMargins(10,10,0,0);
        addView(mLabelView, params);
        setNum(0);
    }

    public ImageView getLabel(){
        return mLabelView;
    }

    public int getNum(){
        return mNum;
    }

    public void setNum(int num){
        mNum = num;
        String name = "ic_"+mType+"_"+mNum;
        mLabelView.setImageResource(DrawableUtil.getResID(name));
        setBgColor(num);
    }

    private void setBgColor(int num){
        int color ;
        switch (num){
            case 1:
                color = COLOR_1;
                break;
            case 2:
                color = COLOR_2;
                break;
            case 3:
                color = COLOR_3;
                break;
            case 4:
                color = COLOR_4;
                break;
            case 5:
                color = COLOR_5;
                break;
            case 6:
                color = COLOR_6;
                break;
            case 7:
                color = COLOR_7;
                break;
            case 8:
                color = COLOR_8;
                break;
            case 9:
                color = COLOR_9;
                break;
            case 10:
                color = COLOR_10;
                break;
            case 11:
                color = COLOR_11;
                break;
            case 12:
                color = COLOR_12;
                break;
            default:
                color = COLOR_0;
                break;
        }
        mLabelView.setBackgroundColor(color);
    }

    public boolean equals(Card o) {
        return getNum() == o.getNum();
    }
}

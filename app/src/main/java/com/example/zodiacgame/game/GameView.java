package com.example.zodiacgame.game;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import com.app.tool.logger.Logger;
import com.example.zodiacgame.MainActivity;
import com.example.zodiacgame.R;
import com.example.zodiacgame.util.ScoreUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.CRC32;

/**
 * Created by Administrator on 2015/8/21.
 */
public class GameView extends GridLayout {


    private Card mCardsMap[][] = new Card[4][4];
    private int mWidth;
    private List<Point> mEmptyPoints  = new ArrayList<Point>();
    private TextView mCurrentScoreView,mMaxScoreView;
    private boolean flag;

    public GameView(Context context){
        super(context);
        initGameView();
    }

    public GameView(Context context, AttributeSet attrs){
        super(context, attrs);
        initGameView();
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initGameView();
    }

    private void initGameView(){
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                initView();
            }
        },100);
        setColumnCount(4);
        setBackgroundColor(Color.parseColor("#3a535f"));
        setOnTouchListener(new OnTouchListener() {

            private float startX, startY, offsetX, offsetY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        flag = false;
                        offsetX = event.getX() - startX;
                        offsetY = event.getY() - startY;
                        //水平滑动
                        if (Math.abs(offsetX) > Math.abs(offsetY)) {

                            if (offsetX < -10) { //向左滑
                                swipeLeft();
                            } else if (offsetX > 10) { //向右滑
                                swipeRight();
                            }
                        } else { //垂直滑动
                            if (offsetY < -10) { //向上滑
                                swipeUp();
                            } else if (offsetY > 10) {  //向下滑
                                swipeDown();
                            }
                        }
                        if (flag) {
                            addRandNum();
                            checkComplete();
                        }
                        break;
                }
                return true;
            }
        });
    }

    private void initView(){
        MainActivity activity = MainActivity.getMainActivity();
        mCurrentScoreView = (TextView) activity.findViewById(R.id.tv_current_score);
        mMaxScoreView = (TextView) activity.findViewById(R.id.tv_max_score);
        ScoreUtil.setCurrentScore(0);
        updateScoreView();
    }

    private void swipeLeft(){
            for (int y = 0; y < Config.LINES; y++) {
                for (int x = 0; x < Config.LINES; x++) {
                    for (int x1 = x+1; x1 < Config.LINES; x1++) {
                        if (mCardsMap[x1][y].getNum()>0){
                            if (mCardsMap[x][y].getNum()<=0){
                                MainActivity.getMainActivity().getAnimLayer().createMoveAnim(mCardsMap[x1][y],
                                        mCardsMap[x][y], x1, x, y, y);
                                mCardsMap[x][y].setNum(mCardsMap[x1][y].getNum());
                                mCardsMap[x1][y].setNum(0);
                                x--;
                                flag = true;
                            }else if (mCardsMap[x][y].equals(mCardsMap[x1][y])&&mCardsMap[x][y].getNum()<12){
                                MainActivity.getMainActivity().getAnimLayer().createMoveAnim(mCardsMap[x1][y],
                                        mCardsMap[x][y],x1, x, y, y);
                                mCardsMap[x][y].setNum(mCardsMap[x][y].getNum()+1);
                                mCardsMap[x1][y].setNum(0);
                                setScore(mCardsMap[x][y].getNum());
                                flag = true;
                            }
                            break;
                        }
                    }
                }
            }
    }

    private boolean checkLeft(int x,int start,int end){
        boolean notExist = true;
        for(int y = start;y<=end;y++){
            if(mCardsMap[x][y].getNum()>0){
                notExist = false;
            }
        }
        return notExist;
    }


    private void swipeRight(){
        for (int y = 0; y < Config.LINES; y++) {
            for (int x = Config.LINES-1; x >=0; x--) {
                for (int x1 = x-1; x1 >=0; x1--){
                    if (mCardsMap[x1][y].getNum()>0){
                        if (mCardsMap[x][y].getNum()<=0){
                            MainActivity.getMainActivity().getAnimLayer().createMoveAnim(mCardsMap[x1][y],
                                    mCardsMap[x][y],x1, x, y, y);
                            mCardsMap[x][y].setNum(mCardsMap[x1][y].getNum());
                            mCardsMap[x1][y].setNum(0);
                            x++;
                            flag = true;
                        }else if (mCardsMap[x][y].equals(mCardsMap[x1][y])&&mCardsMap[x][y].getNum()<12) {
                            MainActivity.getMainActivity().getAnimLayer().createMoveAnim(mCardsMap[x1][y],
                                    mCardsMap[x][y],x1, x, y, y);
                            mCardsMap[x][y].setNum(mCardsMap[x][y].getNum()+1);
                            mCardsMap[x1][y].setNum(0);
                            setScore(mCardsMap[x][y].getNum());
                            flag = true;
                        }

                        break;
                    }
                }
            }
        }
    }

    private boolean checkRight(int x,int start,int end){
        boolean notExist = true;
        for(int y = start;y<=end;y++){
            if(mCardsMap[x][y].getNum()>0){
                notExist = false;
            }
        }
        return notExist;
    }

    private void swipeUp(){
        for (int x = 0; x < Config.LINES; x++) {
            for (int y = 0; y < Config.LINES; y++) {
                for (int y1 = y+1; y1 < Config.LINES; y1++) {
                    if (mCardsMap[x][y1].getNum()>0) {
                        if (mCardsMap[x][y].getNum()<=0) {
                            MainActivity.getMainActivity().getAnimLayer().createMoveAnim(mCardsMap[x][y1],
                                    mCardsMap[x][y], x, x, y1, y);
                            mCardsMap[x][y].setNum(mCardsMap[x][y1].getNum());
                            mCardsMap[x][y1].setNum(0);
                            y--;
                            flag = true;
                        }else if (mCardsMap[x][y].equals(mCardsMap[x][y1])&&mCardsMap[x][y].getNum()<12) {
                            MainActivity.getMainActivity().getAnimLayer().createMoveAnim(mCardsMap[x][y1],
                                    mCardsMap[x][y], x, x, y1, y);
                            mCardsMap[x][y].setNum(mCardsMap[x][y].getNum() + 1);
                            mCardsMap[x][y1].setNum(0);
                            setScore(mCardsMap[x][y].getNum());
                            flag = true;
                        }
                        break;
                    }
                }
            }
        }
    }

    private boolean checkUp(int y,int start,int end){
        boolean notExist = true;
        for(int x = start;x<=end;x++){
            if(mCardsMap[x][y].getNum()>0){
                notExist = false;
            }
        }
        return notExist;
    }

    private void swipeDown(){
        for (int x = 0; x < Config.LINES; x++) {
            for (int y = Config.LINES-1; y >=0; y--) {

                for (int y1 = y-1; y1 >=0; y1--) {
                    if (mCardsMap[x][y1].getNum()>0) {
                        if (mCardsMap[x][y].getNum()<=0) {
                            MainActivity.getMainActivity().getAnimLayer().createMoveAnim(mCardsMap[x][y1],
                                    mCardsMap[x][y], x, x, y1, y);
                            mCardsMap[x][y].setNum(mCardsMap[x][y1].getNum());
                            mCardsMap[x][y1].setNum(0);
                            y++;
                            flag = true;
                        }else if (mCardsMap[x][y].equals(mCardsMap[x][y1])&&mCardsMap[x][y].getNum()<12){
                            MainActivity.getMainActivity().getAnimLayer().createMoveAnim(mCardsMap[x][y1],
                                    mCardsMap[x][y], x, x, y1, y);
                            mCardsMap[x][y].setNum(mCardsMap[x][y].getNum()+1);
                            mCardsMap[x][y1].setNum(0);
                            setScore(mCardsMap[x][y].getNum());
                            flag = true;
                        }
                        break;
                    }
                }
            }
        }
    }
    private boolean checkDown(int y,int start,int end){
        boolean notExist = true;
        for(int x = start;x<=end;x++){
            if(mCardsMap[x][y].getNum()>0){
                notExist = false;
            }
        }
        return notExist;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Config.CARD_WIDTH = (w-10)/4;
        addCards(Config.CARD_WIDTH);
        startGame();
    }

    private void addCards(int cardWidth){
        removeAllViews();
        Card c;
        for (int y = 0; y < 4 ; y++){
            for (int x = 0; x <4 ; x++){
                c = new Card(getContext());
                c.setNum(0);
                addView(c, cardWidth, cardWidth);
                Config.CARD_WIDTH = cardWidth;
                mCardsMap[x][y] = c;
            }
        }
    }

    private void addRandNum(){
        mEmptyPoints.clear();
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++) {
                if(mCardsMap[i][j].getNum()<=0){
                    mEmptyPoints.add(new Point(i,j));
                }
            }
        }
        if(mEmptyPoints.size()>0){
            Point p = mEmptyPoints.remove((int)(Math.random()*mEmptyPoints.size()));
            mCardsMap[p.x][p.y].setNum(Math.random() > 0.1 ? 1 : 2);
            MainActivity.getMainActivity().getAnimLayer().createScaleTo1(mCardsMap[p.x][p.y]);
        }
    }

    public void startGame(){
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                mCardsMap[i][j].setNum(0);
            }
        }
        addRandNum();
        addRandNum();

    }

    private void checkComplete(){
        boolean complete = true;
        ALL:
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                if(mCardsMap[x][y].getNum()==0||
                        (x>0&&mCardsMap[x][y].equals(mCardsMap[x-1][y]))||
                        (x<3&&mCardsMap[x][y].equals(mCardsMap[x+1][y]))||
                        (y>0&&mCardsMap[x][y].equals(mCardsMap[x][y-1]))||
                        (y<3&&mCardsMap[x][y].equals(mCardsMap[x][y+1]))){
                    complete  = false;
                    break ALL;
                }
            }
        }
        if(complete){
            new AlertDialog.Builder(getContext()).setTitle("游戏结束").setPositiveButton("重新开始",
                    new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startGame();
                    ScoreUtil.setCurrentScore(0);
                    updateScoreView();
                }
            }).create().show();
        }
    }

    private void setScore(int score){
        int _Score = ScoreUtil.getCurrentScore()+score;
        ScoreUtil.setCurrentScore(_Score);
        ScoreUtil.setMaxScore(_Score);
        updateScoreView();
    }

    private void updateScoreView(){
        mCurrentScoreView.setText(ScoreUtil.getCurrentScore()+"");
        mMaxScoreView.setText(ScoreUtil.getMaxScore()+"");
    }
}

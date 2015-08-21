package com.example.zodiacgame;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import com.example.zodiacgame.game.AnimLayer;
import com.example.zodiacgame.game.GameView;
import com.example.zodiacgame.util.ScoreUtil;

public class MainActivity extends ActionBarActivity{

    private GameView mGameView;
    private static MainActivity mActivity;
    private AnimLayer mAnimLayer;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActivity = this;
        ScoreUtil.init(this);
        initView();
//        startGame();
    }

    private void initView() {
        mGameView = (GameView) findViewById(R.id.gameView);
        mAnimLayer = (AnimLayer) findViewById(R.id.animLayer);
    }

    private void startGame(){
        mGameView.startGame();
    }

    public static MainActivity getMainActivity(){
        return mActivity;
    }

    public AnimLayer getAnimLayer(){
        return mAnimLayer;
    }
}

package com.example.quizapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class takeQuizPackActivity extends AppCompatActivity {

    private int correctNum;
    private FragmentTransaction transaction;
    public mainApplication mainApp;

    //Activity起動時, パック選択画面を表示するメソッド
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_quiz_pack);

        transaction = getSupportFragmentManager().beginTransaction();

        //メニューボタン作成
        Button btnMenu = findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(v -> finish());

        this.selectPack();
    }

    //パック作成画面を表示するメソッド
    public void selectPack(){
        selectPackFragment selectPackFragment = new selectPackFragment();
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container, selectPackFragment);
        transaction.commit();
    }

    //クイズ回答画面を表示するメソッド
    public void takeQuiz(){
        this.correctNum = 0;
        takeQuizFragment takeQuizFragment = new takeQuizFragment();
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container, takeQuizFragment);
        transaction.commit();
    }

    //結果を表示するメソッド
    public void result(){
        resultFragment resultFragment = new resultFragment();
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container, resultFragment);
        transaction.commit();
    }

    //遷移先のFragmentからメニューボタンを再表示させるメソッド
    public LinearLayout getLinearLayout() {
        LinearLayout layout = null;
        //ボタン設定
        Button button = new Button(this);
        button.setTextSize(10);
        button.setText("メニュー");
        button.setOnClickListener(backMainActivity);
        LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        button.setLayoutParams(buttonLayoutParams);
        layout.addView(button);

        return layout;
    }
    //再作成したメニューボタン用の処理
    View.OnClickListener backMainActivity = new View.OnClickListener(){
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onClick(View v){
            finish();
        }
    };

    public mainApplication getData(){
        return mainApp;
    }

    //correctNumのgetterとsetter
    public int getCorrectNum() {
        return correctNum;
    }
    public void setCorrectNum(int correctNum){
        this.correctNum = correctNum;
    }
}
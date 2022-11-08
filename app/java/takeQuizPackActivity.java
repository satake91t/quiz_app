package com.example.sample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Button;

public class takeQuizPackActivity extends AppCompatActivity {

    private int correctNum;
    private FragmentTransaction transaction;
    //private mainApplication mainApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_quiz_pack);

        transaction = getSupportFragmentManager().beginTransaction();

        Button btnMenu = findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(v -> finish());

        this.selectPack();
    }

    //処理の内容
    // 検索　javaコーディング規約
    public void selectPack(){
        selectPackFragment selectPackFragment = new selectPackFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container, selectPackFragment);
        transaction.commit();
    }

    public void takeQuiz(){
        this.correctNum = 0;
        takeQuizFragment takeQuizFragment = new takeQuizFragment;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container, takeQuizFragment);
        transaction.commit();
    }

    public void result(){
        resultFragment resultFragment = new resultFragment;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container, resultFragment);
        transaction.commit();
    }

    public int getCorrectNum() {
        return correctNum;
    }
    public void setCorrectNum(int correctNum){
        this.correctNum = correctNum;
    }
}
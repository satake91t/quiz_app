package com.example.quizapp;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

public class showExplanationFragment extends Fragment {
    //フィールド変数の宣言
    LinearLayout layout;
    takeQuizPackActivity tqActivity;
    mainApplication mainApplication;
    int quizNum;
    String puckId;
    String[] quizDatas;
    List<String> quizList=new ArrayList<>();


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_show_explanation,container,false);
    }

    //フィールド変数に値を代入、Viewの生成
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        //フィールド変数に値を代入
        //takeQuizPackActivityのメソッドをつくためにインスタンス化
        tqActivity=(takeQuizPackActivity)getActivity();

        //mainApplicationを使えるようにActivityからApplicationを受け取る
        mainApplication=tqActivity.getData();

        //パックIDをmainApplicationから受け取る
        puckId =mainApplication.getPuckId();

        //パックIDに該当するファイルを読み込み各行をリストとして受け取る
        quizList=mainApplication.readFileAsList(puckId);

        //mainApplicationの問題番号を受け取る
        quizNum=mainApplication.getQuizNum();

        //クイズが入っているリストを","区切りで配列に代入
        setQuizDatas(quizList);

        //Viewの生成
        createView();

    }


    //Viewの生成をするメソッド
    @SuppressLint("ResourceType")
    public void createView(){
        //レイアウトの設定
        layout=tqActivity.getLinearLayout();
        tqActivity.setContentView(layout);


        //ボタンの設定
        Button button = new Button(tqActivity);
        button.setTextSize(15);
        button.setText("解説を閉じる");
        button.setOnClickListener(backFragment);
        //ボタンの幅、高さの設定
        LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        button.setLayoutParams(buttonLayoutParams);
        //ボタンの表示
        layout.addView(button);

        //テキストの設定
        for(int i=1;i<=3;i++){
            TextView textView=new TextView(tqActivity);
            textView.setTextSize(30);
            switch (i){
                case 1:
                    textView.setText("問題文\n"+quizDatas[i]);
                    break;
                case 2:
                    textView.setText("解説\n"+quizDatas[i]);
                    break;
                case 3:
                    textView.setText("正解の選択し\n"+quizDatas[i]);
                    break;
            }

            //テキストの幅、高さの設定
            LinearLayout.LayoutParams textLayoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            textView.setLayoutParams(textLayoutParams);
            //テキストの表示
            layout.addView(textView);
        }
    }


    //ボタンが押された時の処理
    View.OnClickListener backFragment =new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onClick(View v) {
            if(mainApplication.getFromTakeQuizFragment()){
                replaceFragment(new takeQuizFragment());
            }else{
                replaceFragment(new resultFragment());
            }
        }
    };


    //指定したフラグメントを表示
    private void replaceFragment(Fragment fragment){
        // フラグメントマネージャーの取得
        FragmentManager manager = tqActivity.getSupportFragmentManager(); // アクティビティではgetSupportFragmentManager()?
        // フラグメントトランザクションの開始
        FragmentTransaction transaction = manager.beginTransaction();
        // レイアウトをfragmentに置き換え（追加）
        transaction.replace(layout.getId(),fragment);
        // 置き換えのトランザクションをバックスタックに保存する
        transaction.addToBackStack(null);
        // フラグメントを表示する
        transaction.commit();
    }

    //選ばれた問題番号の問題文、解説、選択しを入れておく配列に値を入れる
    public void setQuizDatas(List<String> list) {
        //問題番号に該当する、問題の行を","区切りで配列に代入（問題番号1は行酢でいえば0行目にあたる）
        quizDatas = list.get(quizNum-1).split(",");
    }
}

package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

public class resultFragment extends Fragment {

    //フィールド変数の宣言
    takeQuizPackActivity tqActivity;
    mainApplication data;
    int puckNum;
    int quizNumProblem;
    String[] listData;
    List<String> allList=new ArrayList<>();
    Button button;
    LinearLayout layout;



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_result,container,false);
    }


    //フラグメントが生成されたときに行う処理、主に結果の表示とボタンの生成
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        //フィールド変数に値を代入
        tqActivity=(takeQuizPackActivity)getActivity();  //Activityをインスタンス化して、Activityのメソッドが使えるようにする
        data=tqActivity.getData();
        puckNum=data.getPuckNum();

        //Applicationクラスにあるパックの情報をリストに代入
        data.setAllList(); //リストに値を入れるためいったん記載している
        allList=data.getAllList();

        //配列に値を代入
        setListData(allList);

        //問題数をint型に変換
        quizNumProblem=toListDataQuizNumProblems(listData[2]);

        //最終結果の表示とボタンの生成を行う
        createView(quizNumProblem);


    }


    //リストの情報を","区切りでパックの情報を入れる配列に代入するメソッド
    public void setListData(List<String> allList){
        listData=allList.get(puckNum).split(",");
    }


    //問題数をint型に変換するメソッド
    public int toListDataQuizNumProblems(String listData){
        int change=Integer.parseInt(listData);
        return change;
    }


    //Viewの作成
    @SuppressLint("ResourceType")
    public void createView(int quizNumProblem){

        //スクロールバーの生成
        //スクロールバーの設定
        ScrollView scrollView=new ScrollView(tqActivity);
        tqActivity.setContentView(scrollView);

        //リニアレイアウトの生成
        //リニアレイアウトのの設定
        layout=tqActivity.getLinearLayout();
        //リニアレイアウトの表示
        scrollView.addView(layout);


        //テキストの生成
        //テキストの設定
        TextView textView=new TextView(tqActivity);
        textView.setTextSize(30);
        textView.setText("最終結果："+tqActivity.getCorrectNum()+"/"+quizNumProblem);
        //テキストの幅、高さの設定
        LinearLayout.LayoutParams textLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(textLayoutParams);
        //テキストの表示
        layout.addView(textView);


        //ボタンが押された時の処理
        View.OnClickListener imgBtnListenerImpl =new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                data.setQuizNum((int)view.getTag());  //押されたボタンのタグを問題番号を入れている変数に渡す
                layout.removeAllViews();
                replaceFragment(new showExplanationFragment() );
            }
        };


        //ボタンの生成
        for(int i=1;i<=quizNumProblem;i++){
            //ボタンの設定
            button = new Button(tqActivity);
            button.setText("問題番号"+i+"番");
            button.setTag(i);
            button.setOnClickListener(imgBtnListenerImpl);
            //ボタンの幅、高さの設定
            LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            button.setLayoutParams(buttonLayoutParams);
            //ボタンの表示
            layout.addView(button);
        }

    }


    //フラグメントの呼び出しをするメソッド
    private void replaceFragment(Fragment fragment){
        // フラグメントマネージャーの取得
        FragmentManager manager = tqActivity.getSupportFragmentManager(); // アクティビティではgetSupportFragmentManager()?
        // フラグメントトランザクションの開始
        FragmentTransaction transaction = manager.beginTransaction();
        // レイアウトをfragmentに置き換え（追加）
        transaction.replace(layout.getId(),fragment);
        // 置き換えのトランザクションをバックスタックに保存する
        transaction.addToBackStack(null);
        // フラグメントトランザクションをコミット
        transaction.commit();
    }


}



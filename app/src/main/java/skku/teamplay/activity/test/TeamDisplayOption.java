package skku.teamplay.activity.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import skku.teamplay.R;

/**
 * Created by wantyouring on 2018-05-03.
 */

public class TeamDisplayOption extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_display2);

//        Button memberSet = (Button)findViewById(R.id.button1);  //구성원 설정 버튼
//        Button ruleSet = (Button)findViewById(R.id.button2);    //룰 설정 버튼
//        Button penaltySet = (Button)findViewById(R.id.button3); //페널티 설정 버튼
/*
        //구성원설정 activity 활성화
        memberSet.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(TeamDisplayOption.this, 여기에 구성원설정 클래스이름.class);
                startActivity(intent);
            }
        });
        //룰설정 activity 활성화
        memberSet.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(TeamDisplayOption.this, 여기에 구성원설정 클래스이름.class);
                startActivity(intent);
            }
        });
        //패널티설정 activity 활성화
        memberSet.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(TeamDisplayOption.this, 여기에 구성원설정 클래스이름.class);
                startActivity(intent);
            }
        });
        */

    };
}
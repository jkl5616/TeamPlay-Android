package skku.teamplay.activity.test;

/**
 * Created by wantyouring on 2018-05-01.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import skku.teamplay.R;

public class TeamDisplay extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_display);

        Button option = (Button)findViewById(R.id.button1);
        Button kanban = (Button)findViewById(R.id.button2);

        //칸반보드 activity 활성화
        kanban.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(TeamDisplay.this, KanbanBoardTestActivity.class);
                startActivity(intent);
            }
        });
        //TeamDisplayOption activity 활성화
        option.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(TeamDisplay.this, TeamDisplayOption.class);
                startActivity(intent);
            }
        });

        };
}

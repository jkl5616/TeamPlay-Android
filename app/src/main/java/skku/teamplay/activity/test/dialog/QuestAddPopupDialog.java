package skku.teamplay.activity.test.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import skku.teamplay.R;

/**
 * Created by ddjdd on 2018-05-07.
 */

public class QuestAddPopupDialog extends AppCompatActivity {

    TextView textTitle, textDescription, textStartAt, textDueAt, textType, textScore;
    EditText editTitle, editDescription, editStartAt, editDueAt, editType, editReward;
    Button btnRemove, btnFinish, btnAdd;

    boolean isNew;
    int pos = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setFinishOnTouchOutside(false);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.quest_add_popup);

        textTitle = (TextView)findViewById(R.id.textTitle);
        textDescription = (TextView)findViewById(R.id.textDescription);
        textStartAt = (TextView)findViewById(R.id.textStartAt);
        textDueAt = (TextView)findViewById(R.id.textDueAt);
        textType = (TextView)findViewById(R.id.textType);
        textScore = (TextView)findViewById(R.id.textReward);

        editTitle = (EditText)findViewById(R.id.editTitle);
        editDescription = (EditText)findViewById(R.id.editDescription);
        editStartAt = (EditText)findViewById(R.id.editStartAt);
        editDueAt = (EditText)findViewById(R.id.editDueAt);
        editType = (EditText)findViewById(R.id.editType);
        editReward = (EditText)findViewById(R.id.editReward);

        btnAdd = (Button)findViewById(R.id.btnAdd);
        btnRemove = (Button)findViewById(R.id.btnRemove);
        btnFinish = (Button)findViewById(R.id.btnFinish);


        Intent intent = new Intent(this.getIntent());
        isNew = intent.getBooleanExtra("isNew", false);

        String title, description, startAt, dueAt;
        int type, reward;

        if(!isNew) {
            btnAdd.setText("변경");
            pos = intent.getIntExtra("pos", -1);
        }
        title = intent.getStringExtra("title");
        description = intent.getStringExtra("description");
        startAt = intent.getStringExtra("startAt");
        dueAt = intent.getStringExtra("dueAt");
        type = intent.getIntExtra("type", 0);
        reward = intent.getIntExtra("reward", 0);

        editTitle.setText(title);
        editDescription.setText(description);
        editStartAt.setText(startAt);
        editDueAt.setText(dueAt);
        editType.setText(Integer.toString(type));
        editReward.setText(Integer.toString(reward));

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent retIntent = new Intent();
                retIntent.putExtra("pos", pos);
                setResult(10, retIntent);
                finish();
            }
        });

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent retIntent = new Intent();
                retIntent.putExtra("pos", pos);
                setResult(100, retIntent);
                finish();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent retIntent = new Intent();

                retIntent.putExtra("title", editTitle.getText().toString());
                retIntent.putExtra("description", editDescription.getText().toString());
                retIntent.putExtra("startAt", editStartAt.getText().toString());
                retIntent.putExtra("dueAt", editDueAt.getText().toString());
                retIntent.putExtra("type", editType.getText().toString());
                retIntent.putExtra("reward", editReward.getText().toString());


                if(isNew) {
                    setResult(1000, retIntent);
                    finish();
                }
                else {
                    retIntent.putExtra("pos", pos);
                    setResult(2000, retIntent);
                    finish();
                }
            }
        });
    }
}
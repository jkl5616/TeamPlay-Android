package skku.teamplay.activity.dialog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import skku.teamplay.R;

/**
 * Created by ddjdd on 2018-05-07.
 */

public class QuestPopupDialog extends Activity {
    @BindView(R.id.textTitle) TextView textTitle;
    @BindView(R.id.textDescription) TextView textDescription;
    @BindView(R.id.textStartAt) TextView textStartAt;
    @BindView(R.id.textDueAt) TextView textDueAt;
    @BindView(R.id.textType) TextView textType;
    @BindView(R.id.textReward) TextView textScore;

    @BindView(R.id.editTitle) EditText editTitle;
    @BindView(R.id.editDescription) EditText editDescription;
    @BindView(R.id.editStartAt) EditText editStartAt;
    @BindView(R.id.editDueAt) EditText editDueAt;
    @BindView(R.id.editType) EditText editType;
    @BindView(R.id.editReward) EditText editReward;

    @BindView(R.id.btnRemove) Button btnRemove;
    @BindView(R.id.btnFinish) Button btnFinish;
    @BindView(R.id.btnAdd) Button btnAdd;

    boolean isNew;
    int pos = -1, page = -1;
    String id, mainQuestId, title, description, startAt, dueAt, owner, type, reward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setFinishOnTouchOutside(false);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_kanban_popup);
        ButterKnife.bind(this);

        Intent intent = new Intent(this.getIntent());
        isNew = intent.getBooleanExtra("isNew", false);

        if(!isNew) {
            btnAdd.setText("변경");
            pos = intent.getIntExtra("pos", -1);
        }


        id = intent.getStringExtra("id");
        mainQuestId = intent.getStringExtra("mainQuestId");
        title = intent.getStringExtra("title");
        description = intent.getStringExtra("description");
        startAt = intent.getStringExtra("startAt");
        dueAt = intent.getStringExtra("dueAt");
        type = intent.getStringExtra("type");
        reward = intent.getStringExtra("reward");
        owner = intent.getStringExtra("owner");

        page = intent.getIntExtra("page", -1);

        if(!isNew) {
            editTitle.setText(title);
            editDescription.setText(description);
            editStartAt.setText(startAt);
            editDueAt.setText(dueAt);
            editType.setText(type);
            editReward.setText(reward);
        }
    }


    @OnClick (R.id.btnRemove)
    void onBtnRemoveClick() {
        Intent retIntent = new Intent();
        retIntent.putExtra("pos", pos);
        retIntent.putExtra("id", id);
        retIntent.putExtra("mainQuestId", mainQuestId);
        retIntent.putExtra("page", page);

        retIntent.putExtra("owner", owner);

        setResult(10, retIntent);
        finish();
    }

    @OnClick (R.id.btnFinish)
    void onBtnFinishedClick() {
        Intent retIntent = new Intent();
        retIntent.putExtra("pos", pos);
        retIntent.putExtra("id", id);
        retIntent.putExtra("mainQuestId", mainQuestId);
        retIntent.putExtra("page", page);
        retIntent.putExtra("owner", owner);

        setResult(100, retIntent);
        finish();
    }

    @OnClick (R.id.btnAdd)
    void onBtnAddClick() {
        Intent retIntent = new Intent();

        retIntent.putExtra("id", id);
        retIntent.putExtra("mainQuestId", mainQuestId);
        retIntent.putExtra("page", page);
        retIntent.putExtra("owner", owner);

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

}
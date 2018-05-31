package skku.teamplay.activity.dialog;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import skku.teamplay.R;
import skku.teamplay.activity.adapter.RewardSpinnerAdapter;

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
    @BindView(R.id.editStartAt) TextView editStartAt;
    @BindView(R.id.editDueAt) TextView editDueAt;
    @BindView(R.id.editReward) EditText editReward;

    @BindView(R.id.spinnerRewardType) Spinner spinnerRewardType;

    @BindView(R.id.btnRemove) Button btnRemove;
    @BindView(R.id.btnFinish) Button btnFinish;
    @BindView(R.id.btnAdd) Button btnAdd;

    boolean isNew;
    int pos = -1, page = -1;
    Date startAt, dueAt;
    int startAtYear = 2016, startAtMonth = 10, startAtDay = 3;
    int dueAtYear, dueAtMonth, dueAtDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setFinishOnTouchOutside(false);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_kanban_popup);
        ButterKnife.bind(this);

        List<String> data = new ArrayList<>();
        data.add("지갑");        data.add("전투력");        data.add("서포트");
        RewardSpinnerAdapter spinnerAdapter = new RewardSpinnerAdapter(this, data);
        spinnerRewardType.setAdapter(spinnerAdapter);

        SimpleDateFormat form = new SimpleDateFormat("yyyy/MM/dd");


        Intent intent = new Intent(this.getIntent());
        isNew = intent.getBooleanExtra("isNew", false);

        if (!isNew) {
            btnAdd.setText("변경");
            pos = intent.getIntExtra("pos", -1);
        }



//        questId = intent.getStringExtra("questId");
//        mainQuestId = intent.getStringExtra("mainQuestId");
//        title = intent.getStringExtra("title");
//        description = intent.getStringExtra("description");
//        startAt = intent.getStringExtra("startAt");
//        dueAt = intent.getStringExtra("dueAt");
//        type = intent.getStringExtra("type");
//        reward = intent.getStringExtra("reward");
//        ownerId = intent.getStringExtra("ownerId");

        page = intent.getIntExtra("page", -1);

        int time = startAtYear*10000 + startAtMonth*100 + startAtDay;
        editStartAt.setText(String.valueOf(time));

        if(!isNew) {
//            editTitle.setText(title);
//            editDescription.setText(description);
//            editStartAt.setText(startAt);
//            editDueAt.setText(dueAt);
////            editType.setText(type);
//            editReward.setText(reward);
        }
    }

    @OnClick (R.id.editStartAt)
    void onEditStartAtClick(){
        DatePickerDialog dialog = new DatePickerDialog(QuestPopupDialog.this, dateSetListener, startAtYear, startAtMonth, startAtDay);
        dialog.show();
    }

    @OnClick (R.id.editDueAt)
    void onEditDueAtClick(){
        DatePickerDialog dialog = new DatePickerDialog(QuestPopupDialog.this, dateSetListener, dueAtYear, dueAtMonth, dueAtDay);
        dialog.show();
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            String msg = String.format("%d / %d / %d", year, month+1, day);

            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        }
    };



    @OnClick (R.id.btnRemove)
    void onBtnRemoveClick() {
        Intent retIntent = new Intent();
//        retIntent.putExtra("pos", pos);
//        retIntent.putExtra("questId", questId);
//        retIntent.putExtra("mainQuestId", mainQuestId);
//        retIntent.putExtra("page", page);
//        retIntent.putExtra("ownerId", ownerId);

        setResult(10, retIntent);
        finish();
    }

    @OnClick (R.id.btnFinish)
    void onBtnFinishedClick() {
        Intent retIntent = new Intent();
//        retIntent.putExtra("pos", pos);
//        retIntent.putExtra("questId", questId);
//        retIntent.putExtra("mainQuestId", mainQuestId);
//        retIntent.putExtra("page", page);
//        retIntent.putExtra("ownerId", ownerId);

        setResult(100, retIntent);
        finish();
    }

    @OnClick (R.id.btnAdd)
    void onBtnAddClick() {
        Intent retIntent = new Intent();

//        retIntent.putExtra("questId", questId);
//        retIntent.putExtra("mainQuestId", mainQuestId);
//        retIntent.putExtra("page", page);
//        retIntent.putExtra("ownerId", ownerId);
//
//        retIntent.putExtra("title", editTitle.getText().toString());
//        retIntent.putExtra("description", editDescription.getText().toString());
//        retIntent.putExtra("startAt", editStartAt.getText().toString());
//        retIntent.putExtra("dueAt", editDueAt.getText().toString());
////        retIntent.putExtra("type", editType.getText().toString());
//        retIntent.putExtra("reward", editReward.getText().toString());


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
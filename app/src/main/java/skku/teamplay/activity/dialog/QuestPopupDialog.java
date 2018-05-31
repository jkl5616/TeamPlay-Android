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
import skku.teamplay.model.Quest;

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

    RewardSpinnerAdapter spinnerAdapter;


    int pos = -1, page = -1, startAtYear = 2016, startAtMonth = 10, startAtDay = 3, dueAtYear, dueAtMonth, dueAtDay;
    Date startAt, dueAt;

    Quest quest;

    Intent retIntent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setFinishOnTouchOutside(false);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_kanban_popup);
        ButterKnife.bind(this);
        setSpinner();

        Intent intent = new Intent(this.getIntent());
        quest = (Quest)intent.getSerializableExtra("quest");
        pos = intent.getIntExtra("pos", -1);
        page = intent.getIntExtra("page", -1);

        if(pos != -1) {
            btnAdd.setText("변경");
        }

        editTitle.setText(quest.getTitle());
        editDescription.setText(quest.getDescription());

        // startAt, dueAt 미구현
        int time = startAtYear*10000 + startAtMonth*100 + startAtDay;
        editStartAt.setText(String.valueOf(time));
        editDueAt.setText(String.valueOf(time));

        spinnerRewardType.setSelection(quest.getRewardType());
        editReward.setText(String.valueOf(quest.getReward()));
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

    @OnClick (R.id.btnRemove)
    void onBtnRemoveClick() {
        fillRetIntent();
        setResult(10, retIntent);
        finish();
    }

    @OnClick (R.id.btnFinish)
    void onBtnFinishedClick() {
        quest.makeFinish();
        fillRetIntent();
        setResult(100, retIntent);
        finish();
    }

    @OnClick (R.id.btnAdd)
    void onBtnAddClick() {
        quest.setTitle(editTitle.getText().toString());
        quest.setDescription(editDescription.getText().toString());
        // startAt, dueAt 미구현
        quest.setRewardType(spinnerRewardType.getSelectedItemPosition());
        quest.setReward(Integer.parseInt(editReward.getText().toString()));

        if(pos != -1) {
            fillRetIntent();
            setResult(1000, retIntent);
            finish();
        }
        else {
            retIntent.putExtra("pos", pos);
            retIntent.putExtra("page", page);
            retIntent.putExtra("quest", quest);
            setResult(2000, retIntent);
            finish();
        }
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
//            String msg = String.format("%d / %d / %d", year, month+1, day);

//            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        }
    };

    private void setSpinner() {
        List<String> data = new ArrayList<>();
        data.add("전투력");        data.add("지갑");        data.add("서포트");
        spinnerAdapter = new RewardSpinnerAdapter(this, data);
        spinnerRewardType.setAdapter(spinnerAdapter);
    }

    private void fillRetIntent() {
        retIntent.putExtra("pos", -1);
        retIntent.putExtra("page", page);
        retIntent.putExtra("quest", quest);
    }
}
package skku.teamplay.activity.dialog;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import skku.teamplay.R;
import skku.teamplay.adapter.RewardSpinnerAdapter;
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


    int pos = -1, page = -1;

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
        editStartAt.setText(quest.getStartAtSimple());
        editDueAt.setText(quest.getDueAtSimple());
        spinnerRewardType.setSelection(quest.getRewardType());
        editReward.setText(String.valueOf(quest.getReward()));
    }

    @OnClick (R.id.editStartAt)
    void onEditStartAtClick(){
        int year, month, day;
        year = quest.getStartAtYear();
        month = quest.getStartAtMonth();
        day = quest.getStartAtDay();
        DatePickerDialog dialog = new DatePickerDialog(QuestPopupDialog.this, dateSetListener1, year, month-1, day);
        dialog.show();
    }

    @OnClick (R.id.editDueAt)
    void onEditDueAtClick(){
        int year, month, day;
        year = quest.getDueAtYear();
        month = quest.getDueAtMonth();
        day = quest.getDueAtDay();
        DatePickerDialog dialog = new DatePickerDialog(QuestPopupDialog.this, dateSetListener2, year, month-1, day);
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

        quest.setStartAtSimple(editStartAt.getText().toString());
        quest.setDueAtSimple(editDueAt.getText().toString());

        quest.setRewardType(spinnerRewardType.getSelectedItemPosition());
        int reward = Integer.parseInt(editReward.getText().toString());

        if(reward <= 0){
            Toast.makeText(getApplicationContext(), "보상은 0보다 커야 합니다.", Toast.LENGTH_LONG).show();
        }
        else {
            quest.setReward(reward);

            if(pos == -1) {
                fillRetIntent();
                setResult(1000, retIntent);
                finish();
            }
            else {
                fillRetIntent();
                setResult(2000, retIntent);
                finish();
            }
        }

    }

    private DatePickerDialog.OnDateSetListener dateSetListener1 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            String tmp = (Integer.toString(year%100)+'.'+String.format("%02d", month+1)+'.'+String.format("%02d",day));

            if(tmp.compareTo(editDueAt.getText().toString()) >= 0){
                Toast.makeText(getApplicationContext(), "종료일보다 앞의 시간을 입력해 주세요", Toast.LENGTH_LONG).show();
            }
            else {
                editStartAt.setText(tmp);
            }
        }
    };

    private DatePickerDialog.OnDateSetListener dateSetListener2 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            String tmp = (Integer.toString(year%100)+'.'+String.format("%02d", month+1)+'.'+String.format("%02d",day));

            if(tmp.compareTo(editStartAt.getText().toString()) <= 0){
                Toast.makeText(getApplicationContext(), "시작일보다 뒤의 시간을 입력해 주세요", Toast.LENGTH_LONG).show();
            }
            else {
                editDueAt.setText(tmp);
            }
        }
    };

    private void setSpinner() {
        List<String> data = new ArrayList<>();
        data.add("전투력");        data.add("지갑");        data.add("서포트");
        spinnerAdapter = new RewardSpinnerAdapter(this, data);
        spinnerRewardType.setAdapter(spinnerAdapter);
    }

    private void fillRetIntent() {
        retIntent.putExtra("pos", pos);
        retIntent.putExtra("page", page);
        retIntent.putExtra("quest", quest);
    }
}
package skku.teamplay.activity.dialog;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
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
import skku.teamplay.adapter.RuleSpinnerAdapter;
import skku.teamplay.api.OnRestApiListener;
import skku.teamplay.api.RestApiResult;
import skku.teamplay.api.RestApiTask;
import skku.teamplay.api.impl.GetRulesByTeam;
import skku.teamplay.api.impl.res.RuleListResult;
import skku.teamplay.app.TeamPlayApp;
import skku.teamplay.model.KanbanPost;
import skku.teamplay.model.Rule;
import skku.teamplay.model.Team;
import skku.teamplay.model.User;

/**
 * Created by ddjdd on 2018-05-07.
 */

public class QuestPopupDialog extends Activity implements OnRestApiListener {
    @BindView(R.id.Title) TextView Title;
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

    @BindView(R.id.spinnerRule) Spinner spinnerRule;
    @BindView(R.id.spinnerRewardType) Spinner spinnerRewardType;

    @BindView(R.id.btnRemove) Button btnRemove;
    @BindView(R.id.btnFinish) Button btnFinish;
    @BindView(R.id.btnAdd) Button btnAdd;

    RewardSpinnerAdapter spinnerAdapter;
    RuleSpinnerAdapter ruleSpinnerAdapter;
    User user;
    Team team;
    int pos = -1, page = -1;

    KanbanPost kanbanPost;

    Intent retIntent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setFinishOnTouchOutside(false);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_kanban_popup);
        ButterKnife.bind(this);
        setSpinner();

        user = TeamPlayApp.getAppInstance().getUser();
        team = TeamPlayApp.getAppInstance().getTeam();

//        team = new Team(); team.setId(1);
//        user = new User("진호", 2);
//        Date ne = new Date();
//        kanbanPost = new KanbanPost(1, 1, "tt", "tt", 0, ne, ne, 1,1, 1,10);

        Intent intent = new Intent(this.getIntent());
        kanbanPost = (KanbanPost)intent.getSerializableExtra("kanbanPost");
        pos = intent.getIntExtra("pos", -1);
        page = intent.getIntExtra("page", -1);

        if(pos != -1) {
            Title.setText("수정하기");
            btnAdd.setText("변경");
            btnRemove.setText("제거");
        }
        else {
            Title.setText("추가하기");
            btnFinish.setEnabled(false);
        }

        if(kanbanPost.getOwner_id() == -1) {
            btnFinish.setText("내꺼");
        }
        else if(kanbanPost.getOwner_id() != user.getId()) {
            btnFinish.setEnabled(false);
        }

        editTitle.setText(kanbanPost.getTitle());
        editDescription.setText(kanbanPost.getDescription());
        editStartAt.setText(kanbanPost.getStartAtSimple());
        editDueAt.setText(kanbanPost.getDueAtSimple());

        new RestApiTask(this).execute(new GetRulesByTeam(team.getId()));
    }

    @Override
    public void onBackPressed() {
        fillRetIntent();
        setResult(0, retIntent);
        finish();
    }

    @OnClick(R.id.btnCancel)
    void onCancelclick() {
        fillRetIntent();
        setResult(0, retIntent);
        finish();
    }

    @OnClick (R.id.editStartAt)
    void onEditStartAtClick(){
        int year, month, day;
        year = kanbanPost.getStartAtYear();
        month = kanbanPost.getStartAtMonth();
        day = kanbanPost.getStartAtDay();
        DatePickerDialog dialog = new DatePickerDialog(QuestPopupDialog.this, dateSetListener1, year, month-1, day);
        dialog.show();
    }

    @OnClick (R.id.editDueAt)
    void onEditDueAtClick(){
        int year, month, day;
        year = kanbanPost.getDueAtYear();
        month = kanbanPost.getDueAtMonth();
        day = kanbanPost.getDueAtDay();
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
        if(kanbanPost.getOwner_id() == -1) {
            kanbanPost.setOwner(user.getId());
            fillRetIntent();
            setResult(80, retIntent);
        }
        else {
            kanbanPost.makeFinish();
            fillRetIntent();
            setResult(100, retIntent);
        }
        finish();
    }

    @OnClick (R.id.btnAdd)
    void onBtnAddClick() {
        kanbanPost.setTitle(editTitle.getText().toString());
        kanbanPost.setDescription(editDescription.getText().toString());
        kanbanPost.setStartAtSimple(editStartAt.getText().toString());
        kanbanPost.setDueAtSimple(editDueAt.getText().toString());
        kanbanPost.setReward_type(spinnerRewardType.getSelectedItemPosition());

        int reward = Integer.parseInt(editReward.getText().toString());
        kanbanPost.setReward(reward);

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
        retIntent.putExtra("kanbanPost", kanbanPost);
    }

    @Override
    public void onRestApiDone(RestApiResult restApiResult) {
        switch (restApiResult.getApiName()) {
            case "getrulesbyteam":
                RuleListResult data = (RuleListResult) restApiResult;
                ArrayList<Rule> ruleList = data.getRuleList();
                Rule defaultRule = new Rule();
                defaultRule.setName("직접 입력");
                defaultRule.setType(kanbanPost.getReward_type());
                defaultRule.setScore(kanbanPost.getReward());
                ruleList.add(0, defaultRule);
                ruleSpinnerAdapter = new RuleSpinnerAdapter(this, ruleList);
                spinnerRule.setAdapter(ruleSpinnerAdapter);
                spinnerRule.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Rule currentRule = ruleSpinnerAdapter.getItem(i);
                        spinnerRewardType.setSelection(currentRule.getType());
                        editReward.setText(String.valueOf(currentRule.getScore()));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        Rule currentRule = ruleSpinnerAdapter.getItem(0);
                        spinnerRewardType.setSelection(currentRule.getType());
                        editReward.setText(String.valueOf(currentRule.getScore()));
                    }
                });

        }
    }
}
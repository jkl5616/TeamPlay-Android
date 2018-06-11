package skku.teamplay.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;
import skku.teamplay.R;
import skku.teamplay.adapter.RuleCardAdapter;
import skku.teamplay.api.OnRestApiListener;
import skku.teamplay.api.RestApiResult;
import skku.teamplay.api.RestApiTask;
import skku.teamplay.api.impl.AddRule;
import skku.teamplay.api.impl.GetRulesByTeam;
import skku.teamplay.api.impl.res.RuleListResult;
import skku.teamplay.app.TeamPlayApp;
import skku.teamplay.model.Team;

public class RuleManageActivity extends AppCompatActivity implements OnRestApiListener {

    @BindView(R.id.lv_rule_list)
    ListView lv_ruleList;

    @BindView(R.id.fab_main_profile)
    FabSpeedDial fab;

    int type = 0;

    Team team;

    BroadcastReceiver receiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rule_manage);
        ButterKnife.bind(this);
        team = TeamPlayApp.getAppInstance().getTeam();
        new RestApiTask(this).execute(new GetRulesByTeam(team.getId()));

        fab.setMenuListener(new SimpleMenuListenerAdapter() {
            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                showRuleAddDialog();
                return false;
            }
        });

        initReceiver();
    }

    private void initReceiver() {
        if(receiver == null) {
            receiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    new RestApiTask(RuleManageActivity.this).execute(new GetRulesByTeam(team.getId()));
                }
            };
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("skku.teamplay.UPDATE_RULE");
            registerReceiver(receiver, intentFilter);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(receiver != null) unregisterReceiver(receiver);
    }

    Spinner sp_ruletype;

    private void showRuleAddDialog() {
        final MaterialDialog dialog =
                new MaterialDialog.Builder(this)
                        .title("규칙 추가")
                        .customView(R.layout.dialog_add_rule, true)
                        .positiveText(android.R.string.ok)
                        .negativeText(android.R.string.cancel)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                EditText rule_name = (EditText) dialog.findViewById(R.id.edittext_rule_name);
                                EditText rule_description = (EditText) dialog.findViewById(R.id.edittext_rule_description);
                                EditText rule_score = (EditText) dialog.findViewById(R.id.edittext_rule_score);
                                new RestApiTask(RuleManageActivity.this).execute(new AddRule(team.getId(), Integer.parseInt(rule_score.getText().toString()), type, rule_description.getText().toString(), rule_name.getText().toString()));
                            }
                        })
                        .build();


        dialog.show();
        sp_ruletype = dialog.getCustomView().findViewById(R.id.spinner_rule_type);

        sp_ruletype.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, RuleCardAdapter.typeString));
        sp_ruletype.setSelection(0);
        sp_ruletype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                RuleManageActivity.this.type = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public void onRestApiDone(RestApiResult restApiResult) {
        switch(restApiResult.getApiName()) {
            case "getrulesbyteam":
                RuleListResult result = (RuleListResult) restApiResult;
                lv_ruleList.setAdapter(new RuleCardAdapter(result.getRuleList()));
                break;
            case "addrule":
                new RestApiTask(this).execute(new GetRulesByTeam(team.getId()));
                break;
        }
    }
}

package skku.teamplay.activity.test;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import skku.teamplay.R;
import skku.teamplay.adapter.GridMemberAdapter;
import skku.teamplay.api.OnRestApiListener;
import skku.teamplay.api.RestApiResult;
import skku.teamplay.api.RestApiTask;
import skku.teamplay.api.impl.SendPenalty;
import skku.teamplay.app.TeamPlayApp;
import skku.teamplay.model.Penalty;
import skku.teamplay.model.User;

public class PenaltyManageActivity extends AppCompatActivity {

    @BindView(R.id.gridview_members)
    GridView gridview_members;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penalty_manage);
        ButterKnife.bind(this);
        final ArrayList<User> userList = new ArrayList<User>();
        for (User u : TeamPlayApp.getAppInstance().getUserList()) {
            if (TeamPlayApp.getAppInstance().getUser().getId() != u.getId())
                userList.add(u);
        }

        GridMemberAdapter adapter = new GridMemberAdapter(userList);
        gridview_members.setAdapter(adapter);
        gridview_members.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final User u = userList.get(i);
                new MaterialDialog.Builder(PenaltyManageActivity.this).negativeText(android.R.string.cancel).title(u.getName()+" ("+u.getEmail()+") 에 페널티 적용하기").positiveText("패널티 적용").customView(R.layout.dialog_penalty_description, true).onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        String description =  ((EditText)dialog.getCustomView().findViewById(R.id.edittext_penalty_description)).getText().toString();
                        new RestApiTask(new OnRestApiListener() {
                            @Override
                            public void onRestApiDone(RestApiResult restApiResult) {
                                Toast.makeText(getApplicationContext(), "패널티가 적용되었습니다.", 0).show();
                                PenaltyManageActivity.this.finish();
                            }
                        }).execute(new SendPenalty(u.getId(), description));
                    }
                }).show();
            }
        });
    }
}

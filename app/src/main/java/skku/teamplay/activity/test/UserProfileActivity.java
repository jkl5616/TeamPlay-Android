package skku.teamplay.activity.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import skku.teamplay.R;
import skku.teamplay.adapter.TeamListAdapter;
import skku.teamplay.api.OnRestApiListener;
import skku.teamplay.api.RestApiResult;
import skku.teamplay.api.RestApiTask;
import skku.teamplay.api.impl.GetTeamByUser;
import skku.teamplay.api.impl.res.TeamListResult;
import skku.teamplay.app.TeamPlayApp;
import skku.teamplay.model.Team;

public class UserProfileActivity extends AppCompatActivity implements OnRestApiListener{
    @BindView(R.id.team_list_recyclerview)
    RecyclerView mRecyclerView;

    @BindView(R.id.main_toolbar)
    Toolbar toolbar;

    private TeamListAdapter teamListAdapter;
    private LinearLayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        ButterKnife.bind(this);

        //유저 이름 타이틀로 설정
        toolbar.setTitle(TeamPlayApp.getAppInstance().getUser().getName());

        new RestApiTask(this).execute(new GetTeamByUser(TeamPlayApp.getAppInstance().getUser().getId()));

        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    }

    @Override
    public void onRestApiDone(RestApiResult restApiResult) {
        switch(restApiResult.getApiName()) {
            case "getteambyuser":
                //해당 유저의 팀 가져옴
                TeamListResult result = (TeamListResult) restApiResult;
                ArrayList<Team> teamList = result.getTeamList();
                teamListAdapter = new TeamListAdapter(teamList);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                mRecyclerView.setAdapter(teamListAdapter);
                break;
        }
    }
}

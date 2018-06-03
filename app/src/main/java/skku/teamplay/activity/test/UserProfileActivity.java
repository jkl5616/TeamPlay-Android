package skku.teamplay.activity.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import skku.teamplay.R;
import skku.teamplay.adapter.TeamListAdapter;
import skku.teamplay.api.OnRestApiListener;
import skku.teamplay.api.RestApiResult;
import skku.teamplay.api.RestApiTask;
import skku.teamplay.api.impl.GetAllUsersByTeam;
import skku.teamplay.api.impl.GetTeamByUser;
import skku.teamplay.api.impl.res.TeamListResult;
import skku.teamplay.api.impl.res.UserListResult;
import skku.teamplay.app.TeamPlayApp;
import skku.teamplay.model.Team;
import skku.teamplay.model.User;
import skku.teamplay.util.RecyclerItemClickListener;

public class UserProfileActivity extends AppCompatActivity implements OnRestApiListener {
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
        switch (restApiResult.getApiName()) {
            case "getteambyuser":
                //해당 유저의 팀 가져옴
                TeamListResult result = (TeamListResult) restApiResult;
                final ArrayList<Team> teamList = result.getTeamList();
                teamListAdapter = new TeamListAdapter(teamList);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                mRecyclerView.setAdapter(teamListAdapter);
                mRecyclerView.addOnItemTouchListener(
                        new RecyclerItemClickListener(UserProfileActivity.this, mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Toast.makeText(UserProfileActivity.this, "clicked", 0).show();
                                //해당 팀
                                Team team = teamList.get(position);
                                TeamPlayApp.getAppInstance().setTeam(team);
                                new RestApiTask(UserProfileActivity.this).execute(new GetAllUsersByTeam(team.getId()));
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                // do whatever
                            }
                        })
                );
                break;
            case "getallusersbyteam":
                UserListResult userListResult = (UserListResult) restApiResult;
                final ArrayList<User> userList = userListResult.getUserList();
                TeamPlayApp.getAppInstance().setUserList(userList);
                //탭 액티비티로 이동
                startActivity(new Intent(UserProfileActivity.this, TabTestActivity.class));
                break;
        }
    }
}

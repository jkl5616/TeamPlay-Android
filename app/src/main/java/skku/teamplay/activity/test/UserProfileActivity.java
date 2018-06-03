package skku.teamplay.activity.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import skku.teamplay.R;
import skku.teamplay.adapter.TeamListAdapter;
import skku.teamplay.models.Team;

public class UserProfileActivity extends AppCompatActivity{
    private RecyclerView mRecyclerView;
    private TeamListAdapter teamListAdapter;
    private ArrayList<Team> teamList;
    private LinearLayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        bind_views();

        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        teamList = new ArrayList<>();
        for (int i = 0; i < 10; i++) //temp create 10 teams
            teamList.add(new Team(Integer.toString(i)));

        teamListAdapter = new TeamListAdapter(teamList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(teamListAdapter);
    }


    private void bind_views(){
        this.mRecyclerView = findViewById(R.id.team_list_recyclerview);
    }
}

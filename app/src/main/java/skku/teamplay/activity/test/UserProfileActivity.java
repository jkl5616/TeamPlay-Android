package skku.teamplay.activity.test;

import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import skku.teamplay.R;
import skku.teamplay.activity.adapter.TeamListAdapter;
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

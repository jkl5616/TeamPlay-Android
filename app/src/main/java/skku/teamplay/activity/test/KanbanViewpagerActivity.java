package skku.teamplay.activity.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import skku.teamplay.R;
import skku.teamplay.adapter.KanbanFragmentAdapter;
import skku.teamplay.activity.dialog.QuestPopupDialog;
import skku.teamplay.api.OnRestApiListener;
import skku.teamplay.api.RestApiResult;
import skku.teamplay.api.RestApiTask;
import skku.teamplay.api.impl.AddKanbanBoard;
import skku.teamplay.api.impl.GetKanbansByTeam;
import skku.teamplay.app.TeamPlayApp;
import skku.teamplay.model.KanbanBoard;
import skku.teamplay.model.KanbanPost;
import skku.teamplay.model.Team;
import skku.teamplay.model.User;

/**
 * Created by ddjdd on 2018-05-07.
 */

public class KanbanViewpagerActivity extends FragmentActivity implements OnRestApiListener {
    @BindView(R.id.pager) ViewPager pager;
    @BindView(R.id.FabAddPost) FloatingActionButton FabAddPost;
    KanbanFragmentAdapter adapter;
    int currentPos;

    private Team team;
    private User user;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kanban_viewpager);
        ButterKnife.bind(this);
        setAdapter();
        team = TeamPlayApp.getAppInstance().getTeam();
        user = TeamPlayApp.getAppInstance().getUser();
        new RestApiTask(this).execute(new GetKanbansByTeam(team.getId());

    }

    @OnClick(R.id.FabAddPost)
    void onFabAddPostClick () {
        Intent intent = new Intent(getApplicationContext(), QuestPopupDialog.class);
        Date startDate = new Date(), endDate = startDate;
        KanbanPost intentKanbanPost = new KanbanPost(-1,-1, "title", "example", false, startDate, endDate, user.getId(), -1, 0, 0);

        intent.putExtra("pos", -1);
        intent.putExtra("page", currentPos);
        intent.putExtra("kanbanPost", intentKanbanPost);

        startActivityForResult(intent, 1);
    }

    @OnClick(R.id.FabAddBoard)
    void onFabAddBoardClick () {
//        Intent intent = new Intent(getApplicationContext(), QuestPopupDialog.class);
//        Date startAt = new Date(), dueAt = startAt;
//        KanbanPost intentKanbanPost = new KanbanPost(-1,-1, "title", "example", false, startAt, dueAt, 1111, -1, 0, 0);
//
//        intent.putExtra("pos", -1);
//        intent.putExtra("page", currentPos);
//        intent.putExtra("kanbanPost", intentKanbanPost);
//
//        startActivityForResult(intent, 1);
        AddKanbanBoard AddKanbanBoard = new AddKanbanBoard(team.getId(), "test", 0);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int page = data.getIntExtra("page", -2);
        adapter.getItem(page).onActivityResult(requestCode, resultCode, data);
    }

    private void setAdapter() {
        adapter = new KanbanFragmentAdapter(getSupportFragmentManager());
        for(int i = 0; i < 7; i++) {
            adapter.addFragment(i);
        }

        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                currentPos = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }


    @Override
    public void onRestApiDone(RestApiResult restApiResult) {
        switch (restApiResult.getApiName()) {
            case "getkanbansbyteam":
                Toast.makeText(getApplicationContext(), "보드 가져오기.", Toast.LENGTH_LONG).show();
                break;
            case "register":
//                new MaterialDialog.Builder(this).content("가입이 완료되었습니다.").show();
                break;
        }
    }
}


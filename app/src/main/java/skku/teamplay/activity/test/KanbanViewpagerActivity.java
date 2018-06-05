package skku.teamplay.activity.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
//import android.support.design.widget.FloatingActionButton;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
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
import skku.teamplay.api.impl.res.KanbanBoardListResult;
import skku.teamplay.app.TeamPlayApp;
import skku.teamplay.model.KanbanBoard;
import skku.teamplay.model.KanbanPost;
import skku.teamplay.model.Team;
import skku.teamplay.model.User;

/**
 * Created by ddjdd on 2018-05-07.
 */

public class
KanbanViewpagerActivity extends FragmentActivity implements OnRestApiListener {
    @BindView(R.id.pager) ViewPager pager;
    @BindView(R.id.FabAddPost) FloatingActionButton FabAddPost;
    KanbanFragmentAdapter adapter;
    int currentPos;

    private Team team;
    private User user;
    ArrayList<KanbanBoard> kanbanBoards;
    int maxLen;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kanban_viewpager);
        ButterKnife.bind(this);
        team = TeamPlayApp.getAppInstance().getTeam();
        new RestApiTask(this).execute(new GetKanbansByTeam(team.getId()));
    }

    @OnClick(R.id.FabAddPost)
    void onFabAddPostClick () {
        Intent intent = new Intent(getApplicationContext(), QuestPopupDialog.class);
        Date startDate = new Date(), endDate = startDate;
        user = TeamPlayApp.getAppInstance().getUser();
        KanbanPost intentKanbanPost = new KanbanPost(-1,-1, "title", "example", 0, startDate, endDate, user.getId(), -1, 0, 0);

        intent.putExtra("pos", -1);
        intent.putExtra("page", currentPos);
        intent.putExtra("kanbanPost", intentKanbanPost);

        startActivityForResult(intent, 1);
    }

    @OnClick(R.id.FabAddBoard)
    void onFabAddBoardClick () {
        MaterialDialog dialog =
                new MaterialDialog.Builder(this)
                        .title("칸반보드 추가하기")
                        .customView(R.layout.dialog_add_kanban_board, true)
                        .positiveText("추가")
                        .negativeText(android.R.string.cancel)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                addBoard();
                            }
                        })
                        .build();
        dialog.show();
    }

    void addBoard() {
        AddKanbanBoard addKanbanBoard = new AddKanbanBoard(team.getId(), "test", kanbanBoards.size()+1);
        new RestApiTask(this).execute(addKanbanBoard);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int page = data.getIntExtra("page", -2);
        adapter.getItem(page).onActivityResult(requestCode, resultCode, data);
    }

    private void setAdapter() {
        adapter = new KanbanFragmentAdapter(getSupportFragmentManager());
        for(int i = 0; i < maxLen; i++) {
            adapter.addFragment(i, kanbanBoards.get(i).getId(), kanbanBoards.get(i).getName());
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
                KanbanBoardListResult kanbanBoardListResult = (KanbanBoardListResult) restApiResult;
                kanbanBoards = kanbanBoardListResult.getKanbanList();
                maxLen = kanbanBoards.size();
                setAdapter();
                break;
            case "addkanbanpost":
                Toast.makeText(getApplicationContext(), "추가됨", Toast.LENGTH_LONG).show();
                break;
        }
    }
}


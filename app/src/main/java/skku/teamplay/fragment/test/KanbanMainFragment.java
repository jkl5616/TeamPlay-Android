package skku.teamplay.fragment.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import skku.teamplay.R;
import skku.teamplay.adapter.KanbanQuestlistAdapter;
import skku.teamplay.activity.test.KanbanViewpagerActivity;
import skku.teamplay.api.OnRestApiListener;
import skku.teamplay.api.RestApiResult;
import skku.teamplay.api.RestApiTask;
import skku.teamplay.api.impl.GetKanbanPostByBoard;
import skku.teamplay.api.impl.GetKanbansByTeam;
import skku.teamplay.api.impl.res.KanbanBoardListResult;
import skku.teamplay.api.impl.res.KanbanPostListResult;
import skku.teamplay.app.TeamPlayApp;
import skku.teamplay.model.KanbanBoard;
import skku.teamplay.model.KanbanPost;
import skku.teamplay.model.Team;
import skku.teamplay.model.User;

/**
 * Created by ddjdd on 2018-05-31.
 */

public class KanbanMainFragment extends  Fragment implements OnRestApiListener{
    private KanbanQuestlistAdapter adapter;
    @BindView(R.id.kanbanPostList) ListView questList;

    ArrayList<KanbanPost> kanbanPosts;
    Team team;
    User user;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_kanban, container, false);
        ButterKnife.bind(this, rootView);

        View header = getLayoutInflater().inflate(R.layout.list_quest_header_template, null, false);
        questList.addHeaderView(header);
        team = TeamPlayApp.getAppInstance().getTeam();
        user = TeamPlayApp.getAppInstance().getUser();
        new RestApiTask(this).execute(new GetKanbansByTeam(team.getId()));

        kanbanPosts = new ArrayList<>();

        questList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            }
            public void onClick(View v) { }
        });

        return rootView;
    }

    @OnClick(R.id.btnKanban)
    void onBtnKanbanClick(){
        Intent intent = new Intent(getActivity(), KanbanViewpagerActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRestApiDone(RestApiResult restApiResult) {
        switch (restApiResult.getApiName()) {
            case "getkanbansbyteam":
                KanbanBoardListResult kanbanBoardListResult = (KanbanBoardListResult) restApiResult;
                ArrayList<KanbanBoard> kanbanBoards = kanbanBoardListResult.getKanbanList();
                for(int i = 0; i < kanbanBoards.size(); i++) {
                    new RestApiTask(this).execute(new GetKanbanPostByBoard(kanbanBoards.get(i).getId()));
                }

                break;
            case "getkanbanpostbyboard":
                KanbanPostListResult result = (KanbanPostListResult) restApiResult;
                ArrayList <KanbanPost> temp = result.getPostList();
                int len = temp.size();
                for(int i = 0; i < len; i++) {
                    if (temp != null) {
                        if (temp.get(i).getOwner_id() == user.getId()) {
                            kanbanPosts.add(temp.get(i));
                        }
                    }
                    if (i == len - 1 && kanbanPosts != null) {
                        adapter = new KanbanQuestlistAdapter(kanbanPosts);
                        if (adapter.getCount() > 0) {
                            questList.setAdapter(adapter);
                        }
                    }
                }
                break;
        }
    }
}

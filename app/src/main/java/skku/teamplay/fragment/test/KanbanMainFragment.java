package skku.teamplay.fragment.test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import skku.teamplay.R;
import skku.teamplay.activity.KanbanViewpagerActivity;
import skku.teamplay.adapter.KanbanQuestlistAdapter;
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

public class KanbanMainFragment extends Fragment implements OnRestApiListener {
    @BindView(R.id.kanbanPostList)
    ListView questList;
    private KanbanQuestlistAdapter adapter;
    private ArrayList<KanbanPost> kanbanPosts;
    private Team team;
    private User user;
    private View rootView;
    View header;

    BroadcastReceiver receiver;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (rootView != null) return rootView;
        rootView = inflater.inflate(R.layout.fragment_kanban, container, false);
        ButterKnife.bind(this, rootView);

        team = TeamPlayApp.getAppInstance().getTeam();
        user = TeamPlayApp.getAppInstance().getUser();

        new RestApiTask(this).execute(new GetKanbansByTeam(team.getId()));

        header = getLayoutInflater().inflate(R.layout.template_postlist_header, null, false);
        questList.addHeaderView(header);
//        questList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
//            }
//            public void onClick(View v) { }
//        });

        adapter = new KanbanQuestlistAdapter();
        initReceiver();
        return rootView;
    }

    private void initReceiver() {
        if (receiver == null) {
            IntentFilter filter = new IntentFilter();
            filter.addAction("skku.teamplay.UPDATE_KANBAN");
            receiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    Log.e("BroadcastReceiver", "update test");
                    adapter = new KanbanQuestlistAdapter();
                    new RestApiTask(KanbanMainFragment.this).execute(new GetKanbansByTeam(team.getId()));
                }
            };
            getActivity().registerReceiver(receiver, filter);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            getActivity().unregisterReceiver(receiver);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();
        //adapter = new KanbanQuestlistAdapter();
        //new RestApiTask(this).execute(new GetKanbansByTeam(team.getId()));
    }

    @OnClick(R.id.btnKanban)
    void onBtnKanbanClick() {
        Intent intent = new Intent(getActivity(), KanbanViewpagerActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRestApiDone(RestApiResult restApiResult) {
        switch (restApiResult.getApiName()) {
            case "getkanbansbyteam":
                KanbanBoardListResult kanbanBoardListResult = (KanbanBoardListResult) restApiResult;
                ArrayList<KanbanBoard> kanbanBoards = kanbanBoardListResult.getKanbanList();
                for (KanbanBoard i : kanbanBoards) {
                    new RestApiTask(this).execute(new GetKanbanPostByBoard(i.getId()));
                }
                break;
            case "getkanbanpostbyboard":
                KanbanPostListResult result = (KanbanPostListResult) restApiResult;
                ArrayList<KanbanPost> temp = result.getPostList();
                int user_id = user.getId();
                if (temp != null) {
                    for (KanbanPost i : temp) {
                        if (i.getOwner_id() == user_id) {
                            adapter.addItem(i);
                        }
                    }
                }
                Collections.sort(adapter.getList());
                questList.setAdapter(adapter);
                TextView tv = (TextView) header.findViewById(R.id.count);
                tv.setText(String.valueOf(adapter.getCount()));
                break;
        }
    }
}

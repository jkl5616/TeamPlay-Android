package skku.teamplay.fragment.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import skku.teamplay.R;
import skku.teamplay.adapter.KanbanQuestlistAdapter;
import skku.teamplay.activity.dialog.QuestPopupDialog;
import skku.teamplay.api.OnRestApiListener;
import skku.teamplay.api.RestApiResult;
import skku.teamplay.api.RestApiTask;
import skku.teamplay.api.impl.AddKanbanPost;
import skku.teamplay.api.impl.GetKanbanPostByBoard;
import skku.teamplay.api.impl.GetKanbansByTeam;
import skku.teamplay.api.impl.GetTeamByUser;
import skku.teamplay.api.impl.res.KanbanBoardListResult;
import skku.teamplay.api.impl.res.KanbanPostListResult;
import skku.teamplay.app.TeamPlayApp;
import skku.teamplay.model.KanbanBoard;
import skku.teamplay.model.KanbanPost;
import skku.teamplay.model.Team;
import skku.teamplay.model.User;
import skku.teamplay.util.KanbanQuestlist;


/**
 * Created by ddjdd on 2018-05-11.
 */

public class KanbanFragment extends Fragment implements OnRestApiListener {
    private int mPage, mKanbanId;
    private  KanbanQuestlistAdapter adapter;
    @BindView(R.id.quest_list) ListView QuestList;
    @BindView(R.id.textKanbanTitle) TextView textKanbanTitle;

    ArrayList<KanbanPost> kanbanPosts;

    public static KanbanFragment create(int page, int kanbanId, String title) {
        KanbanFragment fragment = new KanbanFragment();
        Bundle args = new Bundle();
        args.putInt("page", page);
        args.putInt("kanbanId", kanbanId);
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentLayout = inflater.inflate(R.layout.fragment_kanban_test, container, false);
        ButterKnife.bind(this, fragmentLayout);
        textKanbanTitle.setText(getArguments().getString("title"));
        mPage = getArguments().getInt("page");
        new RestApiTask(this).execute(new GetKanbanPostByBoard(getArguments().getInt("kanbanId")));

        QuestList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent intent = new Intent(getActivity(), QuestPopupDialog.class);
                KanbanPost intentKanbanPost = (KanbanPost)adapter.getItem(position);

                intent.putExtra("pos", position);
                intent.putExtra("page", mPage);
                intent.putExtra("kanbanPost", intentKanbanPost);

                getActivity().startActivityForResult(intent, 1);
            }
            public void onClick(View v) { }
        });
        return fragmentLayout;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int pos;

        KanbanPost retKanbanPost;
        if (requestCode == 1) {
            pos = data.getIntExtra("pos", -1);
            retKanbanPost = (KanbanPost)data.getSerializableExtra("kanbanPost");

            switch (resultCode) {
                case 10:        // 제거
                    if (pos != -1) {
                        adapter.deleteItem(pos);
                    }
                    break;
                case 100:       // 완료
                    if (pos != -1) {
                        adapter.modifyItem(pos, retKanbanPost);
                    }
                    break;

                case 1000:      // 추가
                    retKanbanPost.setKanban_board_id(getArguments().getInt("kanbanId"));
                    adapter.addItem(retKanbanPost);
                    new RestApiTask(this).execute(retKanbanPost.makeAddKanbanPost());
                    break;

                case 2000:      // 변경
                    if (pos != -1) {;
                        adapter.modifyItem(pos, retKanbanPost);
                    }
                    break;
            }
        }
    }

    @Override
    public void onRestApiDone(RestApiResult restApiResult) {
        switch (restApiResult.getApiName()) {
            case "getkanbanpostbyboard":
                KanbanPostListResult result = (KanbanPostListResult) restApiResult;
                kanbanPosts = result.getPostList();
                adapter = new KanbanQuestlistAdapter(kanbanPosts);
                QuestList.setAdapter(adapter);
                break;

            case "addkanbanpost":
                Toast.makeText(getActivity(), "done", Toast.LENGTH_SHORT).show();
        }
    }
}
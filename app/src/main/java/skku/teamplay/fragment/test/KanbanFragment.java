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
import skku.teamplay.api.impl.GetTeamByUser;
import skku.teamplay.api.impl.res.KanbanPostListResult;
import skku.teamplay.app.TeamPlayApp;
import skku.teamplay.model.KanbanPost;


/**
 * Created by ddjdd on 2018-05-11.
 */

public class KanbanFragment extends Fragment implements OnRestApiListener {
    private int mPageNumber;
    private  KanbanQuestlistAdapter adapter;
    @BindView(R.id.quest_list) ListView QuestList;
    @BindView(R.id.textKanbanTitle) TextView textKanbanTitle;

    public static KanbanFragment create(int pageNumber) {
        KanbanFragment fragment = new KanbanFragment();
        Bundle args = new Bundle();
        args.putInt("page", pageNumber);
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
        mPageNumber = getArguments().getInt("page");

        adapter =  new KanbanQuestlistAdapter();
        QuestList.setAdapter(adapter);

//        new RestApiTask(this).execute(new GetKanbanPostByBoard(1));

//        QuestList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
//                Intent intent = new Intent(getActivity(), QuestPopupDialog.class);
//                KanbanPost intentKanbanPost = (KanbanPost)adapter.getItem(position);
//
//                intent.putExtra("pos", position);
//                intent.putExtra("page", mPageNumber);
//                intent.putExtra("kanbanPost", intentKanbanPost);
//
//                getActivity().startActivityForResult(intent, 1);
//            }
//            public void onClick(View v) { }
//        });
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
                    adapter.addItem(retKanbanPost);
                    new RestApiTask(this).execute(retKanbanPost.makeAddKanbanPost());
                    Toast.makeText(getActivity(), retKanbanPost.makeAddKanbanPost().getTitle(), Toast.LENGTH_LONG).show();
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
        Toast.makeText(getActivity(), "추가되었습니다.", Toast.LENGTH_LONG).show();
        switch (restApiResult.getApiName()) {
            case "addkanbanpost":
                Toast.makeText(getActivity(), "추가되었습니다.", Toast.LENGTH_LONG).show();
                break;
//            case "getkanbanpostbyboard":
//                KanbanPostListResult result = (KanbanPostListResult) restApiResult;
//                final ArrayList<KanbanPost> postList = result.getPostList();
//                QuestList.setAdapter((ListAdapter) postList);
//
//                break;
        }
    }
}
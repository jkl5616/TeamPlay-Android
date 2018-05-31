package skku.teamplay.fragment.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import skku.teamplay.R;
import skku.teamplay.activity.adapter.KanbanQuestlistAdapter;
import skku.teamplay.activity.dialog.QuestPopupDialog;
import skku.teamplay.models.MainQuest;
import skku.teamplay.models.Quest;



/**
 * Created by ddjdd on 2018-05-11.
 */

public class KanbanFragment extends Fragment {
    private int mPageNumber;
    private  KanbanQuestlistAdapter adapter;
    ListView QuestList;
    TextView textKanbanTitle;
    MainQuest newMainQuest;

    String GroupID = "G_0001";

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
        textKanbanTitle = (TextView)fragmentLayout.findViewById(R.id.textKanbanTitle);
        QuestList = (ListView) fragmentLayout.findViewById(R.id.quest_list);
        adapter =  new KanbanQuestlistAdapter();
        mPageNumber = getArguments().getInt("page");
        QuestList.setAdapter(adapter);

        QuestList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Quest newQuest = (Quest)adapter.getItem(position);
                Intent intent = new Intent(getActivity(), QuestPopupDialog.class);
                intent.putExtra("isNew", false);
                intent.putExtra("pos", position);
                intent.putExtra("page", mPageNumber);

                newQuest.putExtraIntent(intent);

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

        if (requestCode == 1) {
            switch (resultCode) {
                case 10:        // 제거
                    pos = data.getIntExtra("pos", -1);
                    if (pos != -1) {
                        String id = data.getStringExtra("id");
                        adapter.deleteItem(pos);
                        newMainQuest.setCount(Integer.toString(Integer.valueOf(newMainQuest.getCount()) - 1));
                    }
                    break;
                case 100:       // 완료
                    pos = data.getIntExtra("pos", -1);
                    if (pos != -1) {
                        String id = data.getStringExtra("id");
                        Quest finishedQuest = (Quest) adapter.getItem(pos);
                        adapter.makeFinish(pos, finishedQuest);
                    }
                    break;

                case 1000:      // 추가
                    final Quest newQuest = new Quest();
                    newQuest.getExtraString(data);
                    newQuest.setID(newMainQuest.getNextID());
                    newMainQuest.setCount(Integer.toString(Integer.valueOf(newMainQuest.getCount()) + 1));
                    newMainQuest.setNextID(Integer.toString(Integer.valueOf(newMainQuest.getNextID()) + 1));

                    break;

                case 2000:      // 변경
                    pos = data.getIntExtra("pos", -1);
                    if (pos != -1) {
                        Quest modifiedQuest = new Quest();
                        modifiedQuest.getExtraString(data);
                        adapter.modifyItem(pos, modifiedQuest);
                    }
                    break;
            }
        }
    }
}
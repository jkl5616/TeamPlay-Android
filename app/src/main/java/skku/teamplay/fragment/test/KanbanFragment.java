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
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import skku.teamplay.R;
import skku.teamplay.activity.adapter.KanbanQuestlistAdapter;
import skku.teamplay.activity.dialog.QuestPopupDialog;
import skku.teamplay.models.MainQuest;
import skku.teamplay.model.Quest;


/**
 * Created by ddjdd on 2018-05-11.
 */

public class KanbanFragment extends Fragment {
    private int mPageNumber;
    private  KanbanQuestlistAdapter adapter;
    @BindView(R.id.quest_list) ListView QuestList;
    @BindView(R.id.textKanbanTitle) TextView textKanbanTitle;
    MainQuest newMainQuest;

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

        QuestList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent intent = new Intent(getActivity(), QuestPopupDialog.class);
                Quest intentQuest = (Quest)adapter.getItem(position);

                intent.putExtra("pos", position);
                intent.putExtra("page", mPageNumber);
                intent.putExtra("quest", intentQuest);

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
        Toast.makeText(getActivity(), "here", Toast.LENGTH_LONG).show();

        Quest retQuest;
        if (requestCode == 1) {
            pos = data.getIntExtra("pos", -1);
            retQuest = (Quest)data.getSerializableExtra("quest");

            switch (resultCode) {
                case 10:        // 제거
                    Toast.makeText(getActivity(), "remove", Toast.LENGTH_LONG).show();
                    if (pos != -1) {
                        adapter.deleteItem(pos);
                    }
                    break;
                case 100:       // 완료
                    Toast.makeText(getActivity(), "fin", Toast.LENGTH_LONG).show();
                    if (pos != -1) {
                        adapter.modifyItem(pos, retQuest);
                    }
                    break;

                case 1000:      // 추가
                    adapter.addItem(retQuest);
                    Toast.makeText(getActivity(), "add", Toast.LENGTH_LONG).show();
                    break;

                case 2000:      // 변경
                    Toast.makeText(getActivity(), "modi", Toast.LENGTH_LONG).show();
                    if (pos != -1) {;
                        adapter.modifyItem(pos, retQuest);
                    }
                    break;
            }
        }
    }
}
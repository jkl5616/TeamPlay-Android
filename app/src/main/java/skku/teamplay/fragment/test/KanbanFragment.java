package skku.teamplay.fragment.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.BindView;
import skku.teamplay.R;
import skku.teamplay.activity.adapter.KanbanQuestlistAdapter;
import skku.teamplay.activity.dialog.QuestPopupDialog;
import skku.teamplay.models.Quest;

/**
 * Created by ddjdd on 2018-05-11.
 */

public class KanbanFragment extends Fragment {

    private int mPageNumber;


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

        mPageNumber = getArguments().getInt("page");
    }

    private  KanbanQuestlistAdapter adapter;
    ListView QuestList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentLayout = inflater.inflate(R.layout.kanban_test, container, false);
        QuestList = (ListView) fragmentLayout.findViewById(R.id.quest_list);;
        adapter =  new KanbanQuestlistAdapter();

        showQuestList();

        QuestList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Quest newQuest = (Quest)adapter.getItem(position);
                Intent intent = new Intent(getActivity(), QuestPopupDialog.class);
                intent.putExtra("isNew", false);
                intent.putExtra("pos", position);
                intent.putExtra("title", newQuest.getTitle());
                intent.putExtra("description", newQuest.getDescription());
                intent.putExtra("startAt", newQuest.getStartAt());
                intent.putExtra("dueAt", newQuest.getDueAt());
                intent.putExtra("type", newQuest.getRewardType());
                intent.putExtra("reward", newQuest.getReward());
                getActivity().startActivityForResult(intent, 1);
            }
            public void onClick(View v) { }
        });

        return fragmentLayout;
    }

    public void onResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        int request = requestCode & 0Xffff;
//        KanbanFragment fragment = (KanbanFragment) getSupportFragmentManager().findFragmentById(0);
//        fragment.onResult(requestCode, resultCode, data);

//    getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1).getId()
    }


//    public void onResult (int requestCode, int resultCode, Intent data) {
//
//        String title, description, startAt, dueAt;
//        int type, reward, pos;
//
//        if (requestCode == 1) {
//            switch (resultCode) {
//                case 10:        // 제거
//                    pos = data.getIntExtra("pos", -1);
//                    if (pos != -1) {
////                        adapter.deleteItem(pos);
////                        adapter.notifyDataSetChanged();
//                    }
//                    break;
//
////                case 100:       // 완료
////                    pos = data.getIntExtra("pos", -1);
////                    if (pos != -1) {
////                        Quest finishedQuest = (Quest) adapter.getItem(pos);
////                        adapter.makeFinish(pos, finishedQuest);
////                        adapter.notifyDataSetChanged();
////                    }
////                    break;
////
////                case 1000:      // 추가
////                    title = data.getStringExtra("title");
////                    description = data.getStringExtra("description");
////                    startAt = data.getStringExtra("startAt");
////                    dueAt = data.getStringExtra("dueAt");
////                    type = Integer.parseInt(data.getStringExtra("type"));
////                    reward = Integer.parseInt(data.getStringExtra("reward"));
////                    Quest newQuest = new Quest(title, description, false, startAt, dueAt, "1111", type, reward);
////
////                    adapter.addItem(newQuest);
////                    adapter.notifyDataSetChanged();
////                    break;
////
////                case 2000:      // 변경
////                    title = data.getStringExtra("title");
////                    description = data.getStringExtra("description");
////                    startAt = data.getStringExtra("startAt");
////                    dueAt = data.getStringExtra("dueAt");
////                    type = Integer.parseInt(data.getStringExtra("type"));
////                    reward = Integer.parseInt(data.getStringExtra("reward"));
////
////                    pos = data.getIntExtra("pos", -1);
////                    if (pos != -1) {
////                        Quest modifiedQuest = (Quest) adapter.getItem(pos);
////                        modifiedQuest.setTitle(title);
////                        modifiedQuest.setDescription(description);
////                        modifiedQuest.setStartAt(startAt);
////                        modifiedQuest.setDueAt(dueAt);
////                        modifiedQuest.setRewardType(type);
////                        modifiedQuest.setReward(reward);
////                        adapter.modifyItem(pos, modifiedQuest);
////                        adapter.notifyDataSetChanged();
////                    }
////
////                    break;
//            }
//        }
//    }

    private void showQuestList() {
        QuestList.setAdapter(adapter);

        // sample
        Quest example = new Quest("ppt 작성", "2페이지 만들기", false, "18.02.03", "18.12.03","jinho", 1, 10);
        Quest example2 = new Quest("커피사기", "2잔사기", false, "18.02.03", "18.02.03", "jinho", 1, 10);
        adapter.addItem(example);
        adapter.addItem(example2);

    }
}
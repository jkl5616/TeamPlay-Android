package skku.teamplay.activity.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import skku.teamplay.R;
import skku.teamplay.activity.test.dialog.QuestAddPopupDialog;
import skku.teamplay.models.Quest;
import skku.teamplay.util.KanbanQuestlistAdapter;

/**
 * Created by woorim on 2018-04-15.
 * Test kanbanboard activity.
 */

public class KanbanBoardTestActivity extends AppCompatActivity {


    private ListView QuestList;
    private KanbanQuestlistAdapter adapter;
    private FloatingActionButton addFAB;

    @Override
    public void onCreate(Bundle savedInstnaceState) {
        super.onCreate(savedInstnaceState);
        setContentView(R.layout.kanban_test);

        adapter =  new KanbanQuestlistAdapter();
        QuestList = (ListView) findViewById(R.id.quest_list);

        addFAB = findViewById(R.id.addFAB);

        showQuestList();

        QuestList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Quest newQuest = (Quest)adapter.getItem(position);
                Intent intent = new Intent(getApplicationContext(), QuestAddPopupDialog.class);
                intent.putExtra("isNew", false);
                intent.putExtra("pos", position);
                intent.putExtra("title", newQuest.getTitle());
                intent.putExtra("description", newQuest.getDescription());
                intent.putExtra("startAt", newQuest.getStartAt());
                intent.putExtra("dueAt", newQuest.getDueAt());
                intent.putExtra("type", newQuest.getRewardType());
                intent.putExtra("reward", newQuest.getReward());

                startActivityForResult(intent, 1);

            }
            public void onClick(View v) { }
        });

        addFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), QuestAddPopupDialog.class);
                intent.putExtra("isNew", true);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String title, description, startAt, dueAt;
        int type, reward, pos;

        if(requestCode == 1){
            switch (resultCode){
                case 10:        // 제거
                    pos = data.getIntExtra("pos", -1);
                    if(pos != -1) {
                        adapter.deleteItem(pos);
                        adapter.notifyDataSetChanged();
                    }
                    break;

                case 100:       // 완료
                    pos = data.getIntExtra("pos", -1);
                    if(pos != -1) {
                        Quest finishedQuest = (Quest) adapter.getItem(pos);
                        adapter.makeFinish(pos, finishedQuest);
                        adapter.notifyDataSetChanged();
                    }
                    break;

                case 1000:      // 추가
                    title = data.getStringExtra("title");
                    description = data.getStringExtra("description");
                    startAt = data.getStringExtra("startAt");
                    dueAt = data.getStringExtra("dueAt");
                    type = Integer.parseInt(data.getStringExtra("type"));
                    reward = Integer.parseInt(data.getStringExtra("reward"));
                    Quest newQuest = new Quest(title, description, false, startAt, dueAt, "1111", type, reward);

                    adapter.addItem(newQuest);
                    adapter.notifyDataSetChanged();
                    break;

                case 2000:      // 변경
                    title = data.getStringExtra("title");
                    description = data.getStringExtra("description");
                    startAt = data.getStringExtra("startAt");
                    dueAt = data.getStringExtra("dueAt");
                    type = Integer.parseInt(data.getStringExtra("type"));
                    reward = Integer.parseInt(data.getStringExtra("reward"));

                    pos = data.getIntExtra("pos", -1);
                    if(pos != -1) {
                        Quest modifiedQuest = (Quest) adapter.getItem(pos);
                        modifiedQuest.setTitle(title);
                        modifiedQuest.setDescription(description);
                        modifiedQuest.setStartAt(startAt);
                        modifiedQuest.setDueAt(dueAt);
                        modifiedQuest.setRewardType(type);
                        modifiedQuest.setReward(reward);
                        adapter.modifyItem(pos, modifiedQuest);
                        adapter.notifyDataSetChanged();
                    }

                    break;
            }
        }
    }

    private void showQuestList() {
        QuestList.setAdapter(adapter);

        // sample
        Quest example = new Quest("ppt 작성", "2페이지 만들기", false, "18.02.03", "18.12.03","jinho", 1, 10);
        Quest example2 = new Quest("커피사기", "2잔사기", false, "18.02.03", "18.02.03", "jinho", 1, 10);
        adapter.addItem(example);
        adapter.addItem(example2);

    }
}


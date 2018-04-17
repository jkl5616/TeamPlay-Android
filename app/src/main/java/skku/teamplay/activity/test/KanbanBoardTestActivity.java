package skku.teamplay.activity.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import skku.teamplay.R;
import skku.teamplay.models.Quest;
import skku.teamplay.util.KanbanQuestlistAdapter;

/**
 * Created by woorim on 2018-04-15.
 * Test kanbanboard activity.
 */

public class KanbanBoardTestActivity extends AppCompatActivity {


    private ListView QuestList;
    KanbanQuestlistAdapter adapter;


    @Override
    public void onCreate(Bundle savedInstnaceState) {
        super.onCreate(savedInstnaceState);
        setContentView(R.layout.kanban_test);

        adapter =  new KanbanQuestlistAdapter();
        QuestList = (ListView) findViewById(R.id.quest_list);

        QuestList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Quest newQuest = (Quest)adapter.getItem(position);

//                Intent intent = new Intent(getApplicationContext(), SecondhandPostActivity.class);
//                // putExtra의 첫 값은 식별 태그, 뒤에는 다음 화면에 넘길 값
//                //intent.putExtra("IDSecond", newPost.getIDSecond());
//                intent.putExtra("writerID", newPost.getWriterID());
//                //intent.putExtra("commentID", newPost.getCommentID());
//
//                intent.putExtra("IsFinished", newPost.isFinish());
//                intent.putExtra("title", newPost.getTitle());
//                intent.putExtra("dateTime", newPost.getDateTime());
//                intent.putExtra("price", newPost.getPrice());
//                intent.putExtra("memo", newPost.getMemo());
//                intent.putExtra("category1", newPost.getCategory1());
//                intent.putExtra("category2", newPost.getCategory2());
//                intent.putExtra("condition", newPost.getCondition());
//                intent.putExtra("location", newPost.getLocation());
//
//                startActivity(intent);
            }
            public void onClick(View v) { }
        });

        showPostList();
    }

    private void showPostList() {
        QuestList.setAdapter(adapter);

        // sample
        Quest example = new Quest("1111", "1111", false, "1111", "1111", "1111", "1111", 1, 10);

        adapter.addItem(example);
    }
}

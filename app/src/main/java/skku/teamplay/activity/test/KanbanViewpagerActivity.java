package skku.teamplay.activity.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import skku.teamplay.R;
import skku.teamplay.activity.adapter.KanbanFragmentAdapter;
import skku.teamplay.activity.dialog.QuestPopupDialog;
import skku.teamplay.model.Quest;

/**
 * Created by ddjdd on 2018-05-07.
 */

public class KanbanViewpagerActivity extends FragmentActivity {
    @BindView(R.id.pager) ViewPager pager;
    @BindView(R.id.addFAB) FloatingActionButton addFAB;
    KanbanFragmentAdapter adapter;
    int currentPos;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kanban_viewpager);
        ButterKnife.bind(this);
        setAdapter();
    }

    @OnClick(R.id.addFAB)
    void onAddFabClick () {
        Intent intent = new Intent(getApplicationContext(), QuestPopupDialog.class);
        Date startAt = new Date(), dueAt = startAt;
        Quest intentQuest = new Quest(-1,-1, "title", "example", false, startAt, dueAt, 1111, -1, 0, 0);

        intent.putExtra("pos", -1);
        intent.putExtra("page", currentPos);
        intent.putExtra("quest", intentQuest);

        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int page = data.getIntExtra("page", -2);
        adapter.getItem(page).onActivityResult(requestCode, resultCode, data);
    }

    private void setAdapter() {
        adapter = new KanbanFragmentAdapter(getSupportFragmentManager());
        for(int i = 0; i < 7; i++) {
            adapter.addFragment(i);
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
}


package skku.teamplay.activity.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import skku.teamplay.R;
import skku.teamplay.activity.adapter.KanbanFragmentAdapter;
import skku.teamplay.activity.dialog.QuestPopupDialog;
import skku.teamplay.fragment.test.KanbanFragment;
import skku.teamplay.models.MainQuest;


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

        adapter = new KanbanFragmentAdapter(getSupportFragmentManager());
        for(int i = 0; i < 7; i++) {
            adapter.addFragment(i);
        }

        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
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

    @OnClick(R.id.addFAB)
    void onAddFabClick () {
        Intent intent = new Intent(getApplicationContext(), QuestPopupDialog.class);
        Date startAt = new Date(), dueAt = startAt;
        SimpleDateFormat form = new SimpleDateFormat("yyyy/MM/dd");

        intent.putExtra("isNew", true);
        intent.putExtra("page", currentPos);

        intent.putExtra("questId", 0);
        intent.putExtra("ownerId", 0);

        intent.putExtra("startAt", form.format(startAt));
        intent.putExtra("dueAt", form.format(dueAt));

        startActivityForResult(intent, 1);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        int page = data.getIntExtra("page", -2);
//        adapter.getItem(page).onActivityResult(requestCode, resultCode, data);
//    }
}


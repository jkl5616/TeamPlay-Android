package skku.teamplay.activity.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import skku.teamplay.R;
import skku.teamplay.activity.adapter.KanbanFragmentAdapter;
import skku.teamplay.activity.dialog.QuestPopupDialog;
import skku.teamplay.fragment.test.KanbanFragment;


public class KanbanBoardTestActivity extends FragmentActivity {
    @BindView(R.id.pager) ViewPager pager;
    @BindView(R.id.addFAB) FloatingActionButton addFAB;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kanban_viewpager_test);
        ButterKnife.bind(this);
        PagerAdapter adapter = new KanbanFragmentAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
    }

    @OnClick(R.id.addFAB)
    void onAddFabClick () {
        Intent intent = new Intent(getApplicationContext(), QuestPopupDialog.class);
        intent.putExtra("isNew", true);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 여기서 프래그먼트 리시버를 아예 오버라이드 하는 방법을 생각 중입니다.
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}


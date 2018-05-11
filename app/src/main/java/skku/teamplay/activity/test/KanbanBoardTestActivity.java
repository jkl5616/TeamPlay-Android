package skku.teamplay.activity.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import skku.teamplay.R;
import skku.teamplay.activity.adapter.KanbanFragmentAdapter;
import skku.teamplay.fragment.test.KanbanFragment;


public class KanbanBoardTestActivity extends FragmentActivity {
    @BindView(R.id.pager) ViewPager pager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kanban_viewpager);
        ButterKnife.bind(this);
        PagerAdapter adapter = new KanbanFragmentAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // 여기서 프래그먼트 리시버를 아예 오버라이드 하는 방법을 생각 중입니다.
        super.onActivityResult(requestCode, resultCode, data);
        int request = requestCode & 0Xffff;
//        KanbanFragment fragment = (KanbanFragment) getSupportFragmentManager().findFragmentById(0);
//        KanbanFragment fragment = pager.getCurrentItem();
        Fragment fragment = getSupportFragmentManager().findFragmentById(0);


        Toast.makeText(getApplicationContext(), Integer.toString(i), Toast.LENGTH_LONG).show();
//        fragment.onResult(requestCode, resultCode, data);
//        fragment.onActivityResult(request, resultCode, data);
//    getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1).getId()
    }
}


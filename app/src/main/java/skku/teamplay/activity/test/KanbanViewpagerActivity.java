package skku.teamplay.activity.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import skku.teamplay.R;
import skku.teamplay.activity.adapter.KanbanFragmentAdapter;
import skku.teamplay.activity.dialog.QuestPopupDialog;


public class KanbanViewpagerActivity extends FragmentActivity {
    @BindView(R.id.pager) ViewPager pager;
    @BindView(R.id.addFAB) FloatingActionButton addFAB;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kanban_viewpager);
        ButterKnife.bind(this);
        PagerAdapter adapter = new KanbanFragmentAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
    }

    @OnClick(R.id.addFAB)
    void onAddFabClick () {
        Intent intent = new Intent(getApplicationContext(), QuestPopupDialog.class);
        intent.putExtra("isNew", true);
        intent.putExtra("id", "");
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}


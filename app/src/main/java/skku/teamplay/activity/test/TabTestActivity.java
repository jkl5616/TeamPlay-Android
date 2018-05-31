package skku.teamplay.activity.test;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import skku.teamplay.R;
import skku.teamplay.activity.adapter.ScreenSlidePagerAdapter;
import skku.teamplay.fragment.test.KanbanMainFragment;
import skku.teamplay.fragment.test.MainFragment;
import skku.teamplay.fragment.test.ReportFragment;
import skku.teamplay.fragment.test.ResultFragment;
import skku.teamplay.fragment.test.TestFragment;

/**
 * Created by woorim on 2018. 4. 17..
 */

public class TabTestActivity extends AppCompatActivity {
    @BindView(R.id.tab_container) ViewPager viewPager;

    MainFragment mainFragment;
    KanbanMainFragment kanbanMainFragment;
    ReportFragment reportFragment;
    ResultFragment resultFragment;
    BottomNavigationView bottomNavigationView;
    MenuItem prevMenu;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_test);

        ButterKnife.bind(this);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.tab_bottom_navigation);
        viewPager.addOnPageChangeListener(onPageChangeListener);

        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        setViewPager(viewPager);
    }
    private void setViewPager(ViewPager viewPager){
        ScreenSlidePagerAdapter screenSlidePagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mainFragment = new MainFragment();
        kanbanMainFragment = new KanbanMainFragment();
        reportFragment = new ReportFragment();
        resultFragment = new ResultFragment();

        screenSlidePagerAdapter.addFragment(mainFragment);
        screenSlidePagerAdapter.addFragment(kanbanMainFragment);
        screenSlidePagerAdapter.addFragment(reportFragment);
        screenSlidePagerAdapter.addFragment(resultFragment);

        viewPager.setAdapter(screenSlidePagerAdapter);
    }

    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (prevMenu != null){
                prevMenu.setChecked(false);
            }
            else {
                bottomNavigationView.getMenu().getItem(0).setChecked(false);
            }
            bottomNavigationView.getMenu().getItem(position).setChecked(true);
            prevMenu = bottomNavigationView.getMenu().getItem(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()){
                case R.id.tab1:
                    viewPager.setCurrentItem(0);
                    break;
                case R.id.tab2:
                    viewPager.setCurrentItem(1);
                    break;
                case R.id.tab3:
                    viewPager.setCurrentItem(2);
                    break;
                case R.id.tab4:
                    viewPager.setCurrentItem(3);
                    break;
                case R.id.tab5:
                    viewPager.setCurrentItem(4);
                    break;
            }
            return true;
        }
    };
}

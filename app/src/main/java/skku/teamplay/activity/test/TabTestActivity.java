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
import skku.teamplay.fragment.test.MainFragment;
import skku.teamplay.fragment.test.TestFragment;

/**
 * Created by woorim on 2018. 4. 17..
 */

public class TabTestActivity extends AppCompatActivity {
    @BindView(R.id.tab_container) ViewPager viewPager;

    MainFragment mainFragment;
    TestFragment testFragment1, testFragment2, testFragment3, testFragment4;
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
        testFragment1 = testFragment2 = testFragment3 = testFragment4 = new TestFragment();

        screenSlidePagerAdapter.addFragment(mainFragment);
        screenSlidePagerAdapter.addFragment(testFragment1);

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
            int idx = viewPager.getCurrentItem();
            viewPager.setCurrentItem(idx);
//            viewPager.setCurrentItem(idx);
//            switch (item.getItemId()){
//                case R.id.tab1:
//                    viewPager.setCurrentItem(idx);
//                    break;
//                case R.id.tab2:
//                    viewPager.setCurrentItem(idx);
//                    break;
//                case R.id.tab3:
//                    viewPager.setCurrentItem(idx);
//                    break;
//                case R.id.tab4:
//                    viewPager.setCurrentItem(idx);
//                    break;
//                case R.id.tab5:
//                    viewPager.setCurrentItem(idx);
//                    break;
//            }
            return true;
        }
    };
}

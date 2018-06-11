package skku.teamplay.activity;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import skku.teamplay.R;
import skku.teamplay.adapter.ScreenSlidePagerAdapter;
import skku.teamplay.app.TeamPlayApp;
import skku.teamplay.fragment.test.AppointmentFragment;
import skku.teamplay.fragment.test.KanbanMainFragment;
import skku.teamplay.fragment.test.MainFragment;
import skku.teamplay.fragment.test.ReportFragment;
import skku.teamplay.fragment.test.ResultFragment;
import skku.teamplay.fragment.test.SettingsFragment;
import skku.teamplay.util.CaptureActivity;

/**
 * Created by woorim on 2018. 4. 17..
 */

public class TabTestActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {
    @BindView(R.id.tab_container) ViewPager viewPager;

    MainFragment mainFragment;
    KanbanMainFragment kanbanMainFragment;
    AppointmentFragment appointmentFragment;
    ResultFragment resultFragment;
    SettingsFragment settingsFragment;

    BottomNavigationView bottomNavigationView;
    MenuItem prevMenu;
    ScreenSlidePagerAdapter screenSlidePagerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_test);
        getSupportActionBar().setTitle(TeamPlayApp.getAppInstance().getTeam().getName());
        ButterKnife.bind(this);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.tab_bottom_navigation);
        viewPager.addOnPageChangeListener(onPageChangeListener);

        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        setViewPager(viewPager);
    }

    public void requestPermission(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            Log.d("Permission", "granted");
        }
        else{
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d("Permission", "onRequestPermissionsResult: " + requestCode);
        switch (requestCode){
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Log.d("Permission", "Granted");
                    CaptureActivity.takeScreenshot(this);

                }
        }
    }

    private void setViewPager(ViewPager viewPager){
        screenSlidePagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mainFragment = new MainFragment();
        kanbanMainFragment = new KanbanMainFragment();
        appointmentFragment = new AppointmentFragment();
        resultFragment = new ResultFragment();
        settingsFragment = new SettingsFragment();

        screenSlidePagerAdapter.addFragment(mainFragment);
        screenSlidePagerAdapter.addFragment(kanbanMainFragment);
        screenSlidePagerAdapter.addFragment(appointmentFragment);
        screenSlidePagerAdapter.addFragment(resultFragment);
        screenSlidePagerAdapter.addFragment(settingsFragment);

        viewPager.setOffscreenPageLimit(4);
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

            if (position == 1) {
                screenSlidePagerAdapter.getItem(1).onResume();
            }
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

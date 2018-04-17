package skku.teamplay.activity.test;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import skku.teamplay.R;
import skku.teamplay.fragment.test.TestFragment;

/**
 * Created by woorim on 2018. 4. 17..
 */

public class ViewPagerTestActivity extends AppCompatActivity {
    @BindView(R.id.viewpager_test)
    ViewPager pager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager_test);
        ButterKnife.bind(this);
        PagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                return new TestFragment();
            }

            @Override
            public int getCount() {
                return 5;
            }
        };
        pager.setAdapter(adapter);
    }

}

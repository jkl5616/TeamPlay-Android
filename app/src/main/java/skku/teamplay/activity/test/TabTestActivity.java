package skku.teamplay.activity.test;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import skku.teamplay.R;

/**
 * Created by woorim on 2018-05-04.
 */

public class TabTestActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_test);
        ButterKnife.bind(this);
    }
}

package skku.teamplay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import skku.teamplay.R;
import skku.teamplay.util.Constants;

/**
 * Created by woorim on 2018-04-15.
 * Activity to test another activities, debug only.
 */

public class TestActivity extends AppCompatActivity {

    @BindView(R.id.listview_activity_test)
    ListView lv;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);


        final ArrayList<String> activites = new ArrayList<>();
        for(Class c : Constants.ACTIVITES) {
            activites.add(c.getName());
        }
        lv.setAdapter(new BaseAdapter() {

            @Override
            public int getCount() {
                return activites.size();
            }

            @Override
            public Class getItem(int position) {
                return Constants.ACTIVITES[position];
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if(convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.listitem_card_test, null);
                }
                ((TextView)convertView.findViewById(R.id.text_test)).setText(getItem(position).getName());
                return convertView;
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TestActivity.this, Constants.ACTIVITES[position]);
                startActivity(intent);
            }
        });
    }
}

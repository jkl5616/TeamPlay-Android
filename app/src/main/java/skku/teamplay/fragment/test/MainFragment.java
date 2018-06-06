package skku.teamplay.fragment.test;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.lguipeng.library.animcheckbox.AnimCheckBox;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import skku.teamplay.R;
import skku.teamplay.app.TeamPlayApp;
import skku.teamplay.model.User;
import skku.teamplay.util.UnitConversion;

public class MainFragment extends Fragment {
    //    List<TextView> listPlan = new ArrayList<>();
    ViewGroup rootView;
    RelativeLayout layoutPlan;
    RadarChart mRadarChart;
    TextView textInfo, textPlan;
    ListView listUser;
    ArrayList<IRadarDataSet> setsRadar = new ArrayList<IRadarDataSet>();
    List<Integer> drawnUserId = new ArrayList<>();
    final User selectedUser = TeamPlayApp.getAppInstance().getUser();
    final List<User> userList = TeamPlayApp.getAppInstance().getUserList();

//    @BindView(R.id.main_list_user) ListView listUser;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        int selectedIdx = 0;
        if(rootView != null) return rootView;
        rootView = (ViewGroup) inflater.inflate(R.layout.activity_main_fragment, container, false);


        mRadarChart = (RadarChart) rootView.findViewById(R.id.main_radar_chart);
        textInfo = (TextView) rootView.findViewById(R.id.main_text_my_info);
        textPlan = (TextView) rootView.findViewById(R.id.main_plan_textview);
        layoutPlan = (RelativeLayout) rootView.findViewById(R.id.main_plan_layout);
        listUser = (ListView) rootView.findViewById(R.id.main_list_user);

        for (User user : userList){
            if (user.getId() == selectedUser.getId()) {
                userList.remove(user);
                break;
            }
            selectedIdx++;
        }
        final UserAdapter adapter = new UserAdapter(userList, selectedUser.getId());
        listUser.setAdapter(adapter);

        listUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AnimCheckBox checkBox = view.findViewById(R.id.layout_list_checkbox);
                Boolean isChecked = checkBox.isChecked();
                checkBox.setChecked(!isChecked);

//                updateChartData(userList.get(i), !isChecked);
            }
        });

        User user = TeamPlayApp.getAppInstance().getUser();
        textInfo.setText(user.getName() + "/" + user.getEmail());


        setmRadarChart();
        updateChartData(selectedUser, true);


        return rootView;
    }

    private void updateChartData(User user, boolean isChecked){
        if (isChecked){
            drawnUserId.add(user.getId());
            setChartData(user);
        }
        else{
            for (Integer i : drawnUserId){
                if (i == user.getId()) {
                    drawnUserId.remove(i);
                    for(IRadarDataSet set: setsRadar){
                        if (set.getLabel() == user.getName()) {setsRadar.remove(set); break;}
                    }
                }
            }
            RadarData data = new RadarData(setsRadar);
            mRadarChart.setData(data);

        }

        YAxis yAxis = mRadarChart.getYAxis();
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(80f);
        mRadarChart.setScaleY(1f);

        mRadarChart.invalidate();
    }

    private void setChartData(User user){
        ArrayList<RadarEntry> entry = new ArrayList<>();
        Random rnd = new Random();

        entry.add(new RadarEntry(rnd.nextInt(70) + 1));
        entry.add(new RadarEntry(rnd.nextInt(70) + 1));
        entry.add(new RadarEntry(rnd.nextInt(70) + 1));

        RadarDataSet set = new RadarDataSet(entry, user.getName());
        if (user.getId() == selectedUser.getId()) set.setColor(ColorTemplate.COLORFUL_COLORS[0]);
        else set.setColor(ColorTemplate.COLORFUL_COLORS[rnd.nextInt(3) + 1]);

        set.setDrawFilled(true);
        set.setFillAlpha(180);
        set.setLineWidth(2f);
        set.setDrawHighlightCircleEnabled(true);
        set.setDrawHighlightIndicators(false);

        setsRadar.add(set);
        RadarData data = new RadarData(setsRadar);
        data.setValueTextSize(8f);
        data.setDrawValues(false);
        data.setValueTextColor(Color.WHITE);

        mRadarChart.setData(data);

    }

    private class UserAdapter extends BaseAdapter{
        List<User> userList;
        int selectedId;
        public UserAdapter(List<User> userList, int selectedId) {
            this.userList = userList;
            this.selectedId = selectedId;
        }

        @Override
        public int getCount() {
            return userList.size();
        }

        @Override
        public Object getItem(int i) {
            return userList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return userList.get(i).getId();
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            final int pos = i;

            final Context context = viewGroup.getContext();
            if(view == null){
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.layout_custom_userlist, null, false);
                TextView textUsername = view.findViewById(R.id.layout_list_user_name);
                textUsername.setText(userList.get(i).getName());
            }
            AnimCheckBox checkUser = view.findViewById(R.id.layout_list_checkbox);
            checkUser.setTag(pos);
            checkUser.setOnCheckedChangeListener(new AnimCheckBox.OnCheckedChangeListener() {
                @Override
                public void onChange(AnimCheckBox animCheckBox, boolean b) {
                    int idx = Integer.parseInt(animCheckBox.getTag().toString());
                    updateChartData((User)getItem(idx), b);
                }
            });
//            ImageView imageUser = (ImageView)view.findViewById(R.id.layout_list_profile_pic);



            return view;
        }

        public void addItem(String name){
            User newUser = new User(name, 1);

            userList.add(newUser);
        }
    }


    private void setmRadarChart(){
        mRadarChart.getDescription().setEnabled(false);

        mRadarChart.setTouchEnabled(false);
        mRadarChart.setWebColor(Color.GRAY);
        mRadarChart.setWebLineWidthInner(1f);
        mRadarChart.setWebColorInner(Color.GRAY);
        mRadarChart.setWebAlpha(100);

//        setChartData();

        Typeface mTfLight = Typeface.create("sans-serif-light", Typeface.NORMAL);
        XAxis xAxis = mRadarChart.getXAxis();
        xAxis.setTypeface(mTfLight);
        xAxis.setTextSize(14f);
        xAxis.setYOffset(0f);
        xAxis.setXOffset(0f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {

            private String[] mActivities = new String[]{"지갑", "서포트", "전투력"};

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return mActivities[(int) value % mActivities.length];
            }
        });
        xAxis.setTextColor(Color.BLACK);

        YAxis yAxis = mRadarChart.getYAxis();
        yAxis.setTextColor(Color.BLACK);
        yAxis.setTypeface(mTfLight);
        yAxis.setLabelCount(4, false);
        yAxis.setTextSize(9f);
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(80f);
        yAxis.setDrawLabels(false);

        Legend l = mRadarChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setTypeface(mTfLight);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(5f);
        l.setTextColor(Color.BLACK);
    }
}

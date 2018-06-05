package skku.teamplay.fragment.test;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import skku.teamplay.R;
import skku.teamplay.model.User;
import skku.teamplay.util.UnitConversion;

public class MainFragment extends Fragment {
    RelativeLayout layoutPlan;
    RadarChart mRadarChart;
    TextView textInfo, textPlan;
    List<TextView> listPlan = new ArrayList<>();
    ViewGroup rootView;
    ListView listUser;
    static final String[] user = {"abc", "asd", "asdwq"};
//    @BindView(R.id.main_list_user) ListView listUser;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        List<User> userList = new ArrayList<>();
        if(rootView != null) return rootView;
        rootView = (ViewGroup) inflater.inflate(R.layout.activity_main_fragment, container, false);


        mRadarChart = (RadarChart) rootView.findViewById(R.id.main_radar_chart);
        textInfo = (TextView) rootView.findViewById(R.id.main_text_my_info);
        textPlan = (TextView) rootView.findViewById(R.id.main_plan_textview);
        layoutPlan = (RelativeLayout) rootView.findViewById(R.id.main_plan_layout);
        listUser = (ListView) rootView.findViewById(R.id.main_list_user);

        UserAdapter adapter = new UserAdapter(userList);
        for (int i = 0; i < 10; i++){
            userList.add(new User("A", 1));
        }

        listUser.setAdapter(adapter);



        //        for (int i = 0; i < 10; i++)
//            addPlan("Added Plan #" + Integer.toString(i));
        setmRadarChart();
        return rootView;
    }




    private class UserAdapter extends BaseAdapter{
        List<User> userList;
        public UserAdapter(List<User> userList) {
            this.userList = userList;
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
            }
            TextView textUsername = view.findViewById(R.id.layout_list_user_name);
            AnimCheckBox checkUser = view.findViewById(R.id.layout_list_checkbox);
            ImageView imageUser = (ImageView)view.findViewById(R.id.layout_list_profile_pic);

            User user = userList.get(i);
            textUsername.setText(user.getName());

            return view;
        }

        public void addItem(String name){
            User newUser = new User(name, 1);

            userList.add(newUser);
        }
    }
/*Dynamically add a plan as textview on the layout*/
    public void addPlan(String planTitle){
        final int paddingPx = 20;
        final int marginPx = 30;

        TextView newPlan = new TextView(getContext());


        layoutPlan.addView(newPlan);
        newPlan.setText(planTitle);
        newPlan.setBackgroundColor(Color.parseColor("#CCCCCC"));


        newPlan.setPadding(paddingPx, paddingPx, paddingPx, paddingPx);
        newPlan.setTextSize(16);
        newPlan.setElevation(2f);
        newPlan.setId(ViewGroup.generateViewId());
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) newPlan.getLayoutParams();
        layoutParams.width = RelativeLayout.LayoutParams.MATCH_PARENT;
        if (listPlan.size() == 0){
            layoutParams.setMarginStart(marginPx);
            layoutParams.setMarginEnd(marginPx);
            layoutParams.addRule(RelativeLayout.BELOW, textPlan.getId());
        }
        else{
            layoutParams.addRule(RelativeLayout.BELOW, listPlan.get(listPlan.size() - 1).getId());
        }
        layoutParams.setMargins(marginPx, marginPx/2, marginPx, marginPx/2);

        newPlan.setLayoutParams(layoutParams);
        listPlan.add(newPlan);
    }



    private void setmRadarChart(){
        mRadarChart.getDescription().setEnabled(false);

        mRadarChart.setTouchEnabled(false);
        mRadarChart.setWebColor(Color.GRAY);
        mRadarChart.setWebLineWidthInner(1f);
        mRadarChart.setWebColorInner(Color.GRAY);
        mRadarChart.setWebAlpha(100);

        setChartData();

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

    private void setChartData(){
        float mult = 80;
        float min = 20;
        int cnt = 3;

        ArrayList<RadarEntry> entries1 = new ArrayList<RadarEntry>();
        ArrayList<RadarEntry> entries2 = new ArrayList<RadarEntry>();

        entries1.add(new RadarEntry(81));
        entries1.add(new RadarEntry(20));
        entries1.add(new RadarEntry(52));

        entries2.add(new RadarEntry(30));
        entries2.add(new RadarEntry(61));
        entries2.add(new RadarEntry(92));

        RadarDataSet set1 = new RadarDataSet(entries1, "나");
        set1.setColor(Color.parseColor("#FF8682"));
        set1.setFillColor(Color.parseColor("#FFE8D1"));
        set1.setDrawFilled(true);
        set1.setFillAlpha(180);
        set1.setLineWidth(2f);
        set1.setDrawHighlightCircleEnabled(true);
        set1.setDrawHighlightIndicators(false);

        RadarDataSet set2 = new RadarDataSet(entries2, "너");
        set2.setColor(Color.parseColor("#00BFA5"));
        set2.setFillColor(Color.parseColor("#1DE9B6"));
        set2.setDrawFilled(true);
        set2.setFillAlpha(50);
        set2.setLineWidth(2f);
        set2.setDrawHighlightCircleEnabled(true);
        set2.setDrawHighlightIndicators(false);

        ArrayList<IRadarDataSet> sets = new ArrayList<IRadarDataSet>();
        sets.add(set1);
        sets.add(set2);

        RadarData data = new RadarData(sets);
        data.setValueTextSize(8f);
        data.setDrawValues(false);
        data.setValueTextColor(Color.WHITE);

        mRadarChart.setData(data);
        mRadarChart.invalidate();
    }
}

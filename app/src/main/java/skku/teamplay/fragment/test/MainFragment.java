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
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import skku.teamplay.R;
import skku.teamplay.api.OnRestApiListener;
import skku.teamplay.api.RestApiResult;
import skku.teamplay.api.RestApiTask;
import skku.teamplay.api.impl.GetScoreRecordByTeam;
import skku.teamplay.api.impl.res.ScoreRecordListResult;
import skku.teamplay.app.TeamPlayApp;
import skku.teamplay.model.ScoreRecord;
import skku.teamplay.model.User;
import skku.teamplay.util.ChartAnimatedText;
import skku.teamplay.util.UnitConversion;

public class MainFragment extends Fragment implements OnRestApiListener {
    //    List<TextView> listPlan = new ArrayList<>();
    ViewGroup rootView;
    RelativeLayout layoutBase;
    RadarChart mRadarChart;
    TextView textInfo, textPlan;
    ListView listUser;
    UserAdapter adapter;
    ArrayList<IRadarDataSet> setsRadar = new ArrayList<IRadarDataSet>();
    ArrayList<ScoreRecord> scoreRecords, selectedUserRecords;
    final User selectedUser = TeamPlayApp.getAppInstance().getUser();
    final ArrayList<User> userList = TeamPlayApp.getAppInstance().getUserList();
    List<List<ScoreRecord>> scoreRecordsGroup;

    //    @BindView(R.id.main_list_user) ListView listUser;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        if (rootView != null) return rootView;
        rootView = (ViewGroup) inflater.inflate(R.layout.activity_main_fragment, container, false);

        mRadarChart = (RadarChart) rootView.findViewById(R.id.main_radar_chart);
        textInfo = (TextView) rootView.findViewById(R.id.main_text_my_info);
        textPlan = (TextView) rootView.findViewById(R.id.main_plan_textview);
        layoutBase = (RelativeLayout) rootView.findViewById(R.id.main_frag_base_layout);
        listUser = (ListView) rootView.findViewById(R.id.main_list_user);


        /* Affects userList on TeamPlayApp instance...
        for (User user : userList){ //remove selected user from the list
            if (user.getId() == selectedUser.getId()) {
                userList.remove(user);
                break;
            }
        }*/
        ArrayList<User> userWithRemoved = new ArrayList<>();
        for (User user : userList) {
            if (user.getId() != selectedUser.getId()) userWithRemoved.add(user);
        }
        adapter = new UserAdapter(userWithRemoved, selectedUser.getId()); //set the listview

        listUser.setAdapter(adapter);

        listUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AnimCheckBox checkBox = view.findViewById(R.id.layout_list_checkbox);
                Boolean isChecked = checkBox.isChecked();
                checkBox.setChecked(!isChecked);

            }
        });

        User user = TeamPlayApp.getAppInstance().getUser();
        textInfo.setText(user.getName() + "/" + user.getEmail());

        setmRadarChart();

        new RestApiTask(this).execute(new GetScoreRecordByTeam(TeamPlayApp.getAppInstance().getTeam().getId()));

        return rootView;
    }

    private void init_scoreRecords() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -100);
        scoreRecordsGroup = new ArrayList<List<ScoreRecord>>(userList.size() - 1);
        for (int idx = 0; idx < userList.size(); idx++) {
            if (userList.get(idx).getId() == selectedUser.getId()) continue;
            List<ScoreRecord> tempScores = new ArrayList<>();
            //insert dummy data
            for (int i = 0; i < 3; i++) {
                ScoreRecord dummy = new ScoreRecord();
                Date date;
                date = cal.getTime();
                dummy.setUser_id(userList.get(idx).getId());
                dummy.setScore(0);
                dummy.setType(0);
                dummy.setDate(date);
                tempScores.add(dummy);
            }
            for (ScoreRecord records : scoreRecords) {
                if (records.getUser_id() == userList.get(idx).getId())
                    tempScores.add(records);
            }
            Collections.sort(tempScores, new Comparator<ScoreRecord>() {
                @Override
                public int compare(ScoreRecord scoreRecord, ScoreRecord t1) {
                    return t1.getDate().compareTo(scoreRecord.getDate());
                }
            });
            scoreRecordsGroup.add(tempScores);
        }
        selectedUserRecords = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            ScoreRecord dum = new ScoreRecord();
            dum.setType(0);
            dum.setScore(0);
            dum.setUser_id(selectedUser.getId());
            Date date = cal.getTime();
            dum.setDate(date);
            selectedUserRecords.add(dum);
        }
        for (ScoreRecord record : scoreRecords) {
            if (record.getUser_id() == selectedUser.getId()) selectedUserRecords.add(record);
        }
        Collections.sort(scoreRecords, new Comparator<ScoreRecord>() {
            @Override
            public int compare(ScoreRecord scoreRecord, ScoreRecord t1) {
                return t1.getDate().compareTo(scoreRecord.getDate());
            }
        });


    }

    @Override
    public void onRestApiDone(RestApiResult restApiResult) {
        switch (restApiResult.getApiName()) {
            case "getscorerecordbyteam":
                ScoreRecordListResult scoreList = (ScoreRecordListResult) restApiResult;
                scoreRecords = scoreList.getScoreRecordList();
                init_scoreRecords();
                updateChartData();
                break;
        }
//

    }
    boolean isUserTextAnimated = false;

    private void setAnimatedText(List<ScoreRecord> recordList, boolean isCurUser){
        if (isCurUser){
            for (int i = 0; i < 3; i++){
                if (recordList.get(i).getScore() <= 0) continue;
                ChartAnimatedText animatedText = new ChartAnimatedText(getContext(), layoutBase, mRadarChart, recordList.get(i).getScore(),
                        recordList.get(i).getType());
                animatedText.setDelay(i * animatedText.getDuration());
                animatedText.start();
            }
        }
        else{
            for (int i = 0; i < 3; i++){
                if (recordList.get(i).getScore() <= 0) continue;
                ChartAnimatedText animatedText = new ChartAnimatedText(getContext(), layoutBase, mRadarChart, recordList.get(i).getScore(), recordList.get(i).getType());
                animatedText.setDelay(i * 500);
                animatedText.start();
            }
        }
    }
    private void updateChartData() {
        List<Boolean> checkList = adapter.getChecklist();


        setsRadar.clear();

        setChartData(selectedUserRecords);
        if (isUserTextAnimated == false){
            setAnimatedText(selectedUserRecords, true );
            isUserTextAnimated = true;
        }

        for (int idx = 0; idx < checkList.size(); idx++) {
            if (checkList.get(idx)) {
                setChartData(scoreRecordsGroup.get(idx)); //set checked user's score
                //animate first three scoring events
                if (idx == lastSelected) {
                    setAnimatedText(scoreRecordsGroup.get(idx), false);
                    lastSelected = 0;
                }
            }
        }

        YAxis yAxis = mRadarChart.getYAxis();

        yAxis.resetAxisMaximum();
        mRadarChart.animateY(1000);
        mRadarChart.invalidate();
    }

    private void setChartData(List<ScoreRecord> recordList) {
        String userName = "";
        int[] recordSums = new int[3];
        ArrayList<RadarEntry> entry = new ArrayList<>();


        for (ScoreRecord record : recordList) {
            recordSums[record.getType()] += record.getScore();
        }

        for (int num : recordSums) {
            entry.add(new RadarEntry(num));
        }

        //set name
        if (recordList.get(0).getUser_id() == selectedUser.getId())
            userName = selectedUser.getName();
        else {
            for (User user : userList) {
                if (user.getId() == recordList.get(0).getUser_id()) {
                    userName = user.getName();
                    break;
                }
            }
        }
        RadarDataSet set = new RadarDataSet(entry, userName);
        if (recordList.get(0).getUser_id() == selectedUser.getId()) {
            set.setColor(ColorTemplate.COLORFUL_COLORS[0]);
            set.setFillColor(ColorTemplate.COLORFUL_COLORS[0]);
        } else {
            int length = ColorTemplate.COLORFUL_COLORS.length;
            set.setColor(ColorTemplate.COLORFUL_COLORS[recordList.get(0).getUser_id() % length]);
            set.setFillColor(ColorTemplate.COLORFUL_COLORS[recordList.get(0).getUser_id() % length]);
        }


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
    int lastSelected = 0;
    private class UserAdapter extends BaseAdapter {
        ArrayList<User> userList;
        List<AnimCheckBox> checkBoxes = new ArrayList<>();
        int selectedId;
        Boolean[] tests;

        public UserAdapter(ArrayList<User> userList, int selectedId) {
            this.userList = userList;
            this.selectedId = selectedId;
            tests = new Boolean[getCount()];
            for(int i = 0; i < getCount(); i++) {
                tests[i] = false;
            }
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

        public int getCheckedItems() {
            int cnt = 0;
            for (AnimCheckBox checkBox : checkBoxes) {
                if (checkBox.isChecked()) cnt++;
            }
            return cnt;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            final int pos = i;

            final Context context = viewGroup.getContext();
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.layout_custom_userlist, null, false);

            }
            TextView textUsername = view.findViewById(R.id.layout_list_user_name);
            textUsername.setText(userList.get(i).getName());
            AnimCheckBox checkUser = view.findViewById(R.id.layout_list_checkbox);
            checkUser.setTag(pos);
            //check for duplications; assumption: added sub sequentially
            checkBoxes.add(checkUser);
            checkUser.setOnCheckedChangeListener(new AnimCheckBox.OnCheckedChangeListener() {
                @Override
                public void onChange(AnimCheckBox animCheckBox, boolean b) {
                    int idx = Integer.parseInt(animCheckBox.getTag().toString());
                    if (animCheckBox.isChecked()) lastSelected = pos;
                    tests[idx] = b;

//                        ChartAnimatedText test = new ChartAnimatedText(getContext(), layoutBase, mRadarChart, 5, ChartAnimatedText.RECORD_WALLET);
//                        ChartAnimatedText test1 = new ChartAnimatedText(getContext(), layoutBase, mRadarChart, -21, ChartAnimatedText.RECORD_STRENGTH);
//                        ChartAnimatedText test2 = new ChartAnimatedText(getContext(), layoutBase, mRadarChart, 120, ChartAnimatedText.RECORD_SUPPORT);
//
//                        test.start();
//                        test1.setDelay(900);
//                        test1.start();
//                        test2.setDelay(1800);
//                        test2.start();

                    //user id 로 세팅하고
                    updateChartData();
//                        updateChartData(scoreRecordsGroup.get(idx));
                }
            });
//            ImageView imageUser = (ImageView)view.findViewById(R.id.layout_list_profile_pic);
            return view;
        }

        public List<Boolean> getChecklist() {
            ArrayList<Boolean> list = new ArrayList<>();
            for(int i  = 0 ; i < getCount(); i++) {
                list.add(tests[i]);
            }
            return list;
        }

        public void addItem(String name) {
            User newUser = new User(name, 1);

            userList.add(newUser);
        }
    }


    private void setmRadarChart() {
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

            private String[] mActivities = new String[]{"전투력", "지갑", "서포트"};

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
        yAxis.setAxisMaximum(140f);
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

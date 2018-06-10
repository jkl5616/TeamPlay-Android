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
    final List<User> userList = TeamPlayApp.getAppInstance().getUserList();
    List<List<ScoreRecord>> scoreRecordsGroup;

//    @BindView(R.id.main_list_user) ListView listUser;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        if(rootView != null) return rootView;
        rootView = (ViewGroup) inflater.inflate(R.layout.activity_main_fragment, container, false);

        mRadarChart = (RadarChart) rootView.findViewById(R.id.main_radar_chart);
        textInfo = (TextView) rootView.findViewById(R.id.main_text_my_info);
        textPlan = (TextView) rootView.findViewById(R.id.main_plan_textview);
        layoutBase = (RelativeLayout) rootView.findViewById(R.id.main_frag_base_layout);
        listUser = (ListView) rootView.findViewById(R.id.main_list_user);

        for (User user : userList){ //remove selected user from the list
            if (user.getId() == selectedUser.getId()) {
                userList.remove(user);
                break;
            }
        }

        adapter = new UserAdapter(userList, selectedUser.getId()); //set the listview
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

//        scoreRecords = new ArrayList<>();
//        Random rnd = new Random();
//        for (int i = 0; i < 10; i++){
//            ScoreRecord temp = new ScoreRecord();
//            temp.setUser_id(selectedUser.getId());
//            temp.setScore(rnd.nextInt(30) + 1);
//            temp.setType(i % 3);
//            scoreRecords.add(temp);
//        }
//        //create temp score records
//        scoreRecordsGroup = new ArrayList<List<ScoreRecord>>(userList.size() - 1); //remove selected user
//        for (int idx = 0; idx < userList.size(); idx++){
//            List<ScoreRecord> tempList = new ArrayList<>();
//            for (int j = 0; j < 10; j++){
//                ScoreRecord rec = new ScoreRecord();
//                rec.setType(j % 3);
//                rec.setUser_id(userList.get(idx).getId());
//                rec.setScore(rnd.nextInt(20) + 1);
//                tempList.add(rec);
//            }
//            scoreRecordsGroup.add(tempList);
//        }

//        updateChartData(scoreRecords);
        //get scorerecord

        new RestApiTask(this).execute(new GetScoreRecordByTeam(TeamPlayApp.getAppInstance().getTeam().getId()));

        return rootView;
    }
    private void init_scoreRecords(){
        scoreRecordsGroup = new ArrayList<List<ScoreRecord>>(userList.size() - 1);
        for (int idx = 0; idx < userList.size(); idx++){
            List<ScoreRecord> tempScores = new ArrayList<>();
            for (ScoreRecord records : scoreRecords){
                if (records.getUser_id() == userList.get(idx).getId())
                    tempScores.add(records);
            }
            scoreRecordsGroup.add(tempScores);
        }
        selectedUserRecords = new ArrayList<>();
        for (ScoreRecord record : scoreRecords){
            if(record.getUser_id() == selectedUser.getId()) selectedUserRecords.add(record);
        }
    }
    @Override
    public void onRestApiDone(RestApiResult restApiResult) {
        switch (restApiResult.getApiName()){
            case "getscorerecordbyteam":
                ScoreRecordListResult scoreList = (ScoreRecordListResult) restApiResult;
                scoreRecords = scoreList.getScoreRecordList();
                init_scoreRecords();
                updateChartData();
                break;
        }
//

    }

    private void updateChartData(){
        setsRadar.clear();
        setChartData(selectedUserRecords);


        List<Boolean> checkList = adapter.getChecklist();
        for (int idx = 0; idx < checkList.size(); idx++){
            if (checkList.get(idx)) setChartData(scoreRecordsGroup.get(idx)); //set checked user's score
        }

        YAxis yAxis = mRadarChart.getYAxis();

        yAxis.resetAxisMaximum();
        mRadarChart.animateY(1000);
        mRadarChart.invalidate();
    }

    private void setChartData(List<ScoreRecord> recordList){
        String userName = "";
        if (recordList.size() == 0) return;

        int[] recordSums = new int[3];
        ArrayList<RadarEntry> entry = new ArrayList<>();
        Random rnd = new Random();

        for (ScoreRecord record : recordList){
            recordSums[record.getType()] += record.getScore();
        }

        for (int num : recordSums){
            entry.add(new RadarEntry(num));
        }
//        entry.add(new RadarEntry(rnd.nextInt(70) + 1));
//        entry.add(new RadarEntry(rnd.nextInt(70) + 1));
//        entry.add(new RadarEntry(rnd.nextInt(70) + 1));


        //set name
        if (recordList.get(0).getUser_id() == selectedUser.getId()) userName = selectedUser.getName();
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
        }
        else {
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

    private class UserAdapter extends BaseAdapter{
        List<User> userList;
        List<AnimCheckBox>checkBoxes = new ArrayList<>();
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
                AnimCheckBox checkUser = view.findViewById(R.id.layout_list_checkbox);
                checkUser.setTag(pos);
                checkBoxes.add(checkUser);
                checkUser.setOnCheckedChangeListener(new AnimCheckBox.OnCheckedChangeListener() {
                    @Override
                    public void onChange(AnimCheckBox animCheckBox, boolean b) {
                        int idx = Integer.parseInt(animCheckBox.getTag().toString());

                        ChartAnimatedText test = new ChartAnimatedText(getContext(), layoutBase, mRadarChart, 5, ChartAnimatedText.RECORD_WALLET);
                        ChartAnimatedText test1 = new ChartAnimatedText(getContext(), layoutBase, mRadarChart, -21, ChartAnimatedText.RECORD_STRENGTH);
                        ChartAnimatedText test2 = new ChartAnimatedText(getContext(), layoutBase, mRadarChart, 120, ChartAnimatedText.RECORD_SUPPORT);

                        test.start();
                        test1.setDelay(900);
                        test1.start();
                        test2.setDelay(1800);
                        test2.start();

                        //user id 로 세팅하고
                        updateChartData();
//                        updateChartData(scoreRecordsGroup.get(idx));
                    }
                });
            }
//            ImageView imageUser = (ImageView)view.findViewById(R.id.layout_list_profile_pic);
            return view;
        }

        public List<Boolean> getChecklist(){
            List<Boolean> res = new ArrayList<>();
            for (AnimCheckBox checkBox : checkBoxes)
                res.add(checkBox.isChecked());

            return res;
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

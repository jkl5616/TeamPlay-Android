package skku.teamplay.fragment.test;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
import skku.teamplay.R;
import skku.teamplay.activity.adapter.CountDownAdapter;
import skku.teamplay.activity.widget.CircleCountdownView;
import skku.teamplay.models.Report;
import skku.teamplay.models.User;

public class ReportFragment extends Fragment {
    LinearLayout layoutContributor, layoutChart;
    CircleCountdownView[] circleTimeView = new CircleCountdownView[4];
    ViewGroup rootView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView != null) return rootView;
        List<User> userList = new ArrayList<>();
        List<Report> reportList = new ArrayList<>();
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_report, container, false);
        bindViews();

        //set temp user data
        for (int i = 0; i < 10; i++){
            userList.add(new User("이름" + i, 1));
        }
        Random rand = new Random();
        for (int i = 0; i < 5; i++){
            Report report = new Report("Chart description #" + i, "Title " + i, rand.nextInt(100), 100);
            report.setRandomValues(rand.nextInt(6) + 2);
            reportList.add(report);

        }


        setContributors(userList);
        setCharts(reportList);
        setCountDown();
        return rootView;
    }

    public void setCountDown(){
        long[] time = new long[4];
        time[0] = 10;
        time[1] = 0;
        time[2] = 1;
        time[3] = 14;
        CountDownAdapter countDownAdapter = new CountDownAdapter(circleTimeView, time);
        countDownAdapter.start();
    }
    public void setCharts(List<Report>reportList){
        View view;
        int idx = 0;
        for (Report report : reportList){
            List<PieEntry> entries;
            TextView textTitle, textDes, textVal;
            PieChart pieChart;

            LayoutInflater layoutInflater = (LayoutInflater)rootView.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view  = layoutInflater.inflate(R.layout.template_view_chart, null);
            layoutChart.addView(view);

            textTitle = view.findViewById(R.id.template_chart_title);
            textDes = view.findViewById(R.id.template_chart_description);
            textVal = view.findViewById(R.id.template_chart_values_text);
            pieChart = view.findViewById(R.id.template_pie_chart);


            textTitle.setText(report.getTitle());
            textDes.setText(report.getDescription());
            textVal.setText(Integer.toString(report.getValue()));
            textVal.setTextColor(report.getColor());


            entries = report.getEntries();

//            entries.add(new PieEntry(18.5f, "Green"));
//            entries.add(new PieEntry(26.7f, "Yellow"));
//            entries.add(new PieEntry(24.0f, "Red"));
//            entries.add(new PieEntry(30.8f, "Blue"));

            PieDataSet set = new PieDataSet(entries, "Report");
            //set colors
            Random rand = new Random();
            switch(rand.nextInt(6)){
                case 0:
                    set.setColors(ColorTemplate.COLORFUL_COLORS);
                    break;
                case 1:
                    set.setColors(ColorTemplate.JOYFUL_COLORS);
                    break;
                case 2:
                    set.setColors(ColorTemplate.LIBERTY_COLORS);
                    break;
                case 3:
                    set.setColors(ColorTemplate.MATERIAL_COLORS);
                    break;
                case 4:
                    set.setColors(ColorTemplate.PASTEL_COLORS);
                    break;
                case 5:
                    set.setColors(ColorTemplate.VORDIPLOM_COLORS);
                    break;
            }

            PieData data = new PieData(set);
            pieChart.setData(data);
            if (idx == 0) pieChart.animateY(2000);
            pieChart.invalidate();
        }
    }
    public void setContributors(List<User> userList){
        View view;
        for (User user : userList){
            TextView textView;
            CircleImageView imageView;

            LayoutInflater layoutInflater = (LayoutInflater)rootView.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.template_contributor, null);
            layoutContributor.addView(view);
            textView = view.findViewById(R.id.template_contributor_name);
            imageView = view.findViewById(R.id.template_contributor_image);

            textView.setText(user.getUserName());
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView textName = view.findViewById(R.id.template_contributor_name);
                    Toast.makeText(rootView.getContext(), textName.getText() + " clicked", Toast.LENGTH_LONG).show();
                }
            });
            switch(user.getImageNum()){
                case 1:
                    imageView.setImageResource(R.mipmap.basic_profile_pic);
                    break;
            }


        }
    }
    private void bindViews(){
        layoutContributor = rootView.findViewById(R.id.report_layout_contributor);
        layoutChart = rootView.findViewById(R.id.report_layout_chart);
        circleTimeView[0] = rootView.findViewById(R.id.template_circle_day);
        circleTimeView[1] = rootView.findViewById(R.id.template_circle_hour);
        circleTimeView[2] = rootView.findViewById(R.id.template_circle_min);
        circleTimeView[3] = rootView.findViewById(R.id.template_circle_secs);
    }


}
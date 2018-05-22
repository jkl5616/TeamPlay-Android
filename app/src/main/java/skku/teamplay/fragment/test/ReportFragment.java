package skku.teamplay.fragment.test;

import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
import skku.teamplay.R;
import skku.teamplay.models.Report;
import skku.teamplay.models.User;

public class ReportFragment extends Fragment {
    LinearLayout layoutContributor, layoutChart;
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
            reportList.add(new Report("Chart description #" + i, "Title " + i, rand.nextInt(100), 100));
        }


        setContributors(userList);
        setCharts(reportList);
        return rootView;
    }
    public void setCharts(List<Report>reportList){
        View view;
        int idx = 0;
        for (Report report : reportList){
            List<PieEntry> entries = new ArrayList<>();
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

//            entries.add(new PieEntry(report.getValue(), "Blue"));
//            entries.add(new PieEntry(100 - report.getValue(), "Empty"));
//
//            PieDataSet set = new PieDataSet(entries, "Report");
//            PieData data = new PieData(set);
//            pieChart.setData(data);
//            pieChart.invalidate();
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
    }


}
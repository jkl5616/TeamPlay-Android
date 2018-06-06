package skku.teamplay.fragment.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import skku.teamplay.R;
import skku.teamplay.adapter.TimelineAdapter;
import skku.teamplay.app.TeamPlayApp;
import skku.teamplay.model.KanbanPost;
import skku.teamplay.model.User;

public class ResultFragment extends Fragment {
    final static private float GROUP_SPACE= 0.15f;
    final static private float BAR_SPACE = 0.1f;
    final static private float BAR_WIDTH = 0.45f;

    MaterialSpinner spinnerSelectUser;
    ViewGroup rootView;
    BarChart mBarChart;
    RecyclerView mRecyclerView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView != null) return rootView;
        rootView = (ViewGroup)inflater.inflate(R.layout.fragment_result, container, false);

        bind_views();
        setBarChart();
        setTimeline();

        List<User> userList = TeamPlayApp.getAppInstance().getUserList();
        User curUser = TeamPlayApp.getAppInstance().getUser();
        List<String> userNames = new ArrayList<>();
        userNames.add(curUser.getName());
        for (User user : userList){
            userNames.add(user.getName());
        }
        spinnerSelectUser.setItems(userNames);
        spinnerSelectUser.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                Toast.makeText(rootView.getContext(), "Clicked" + item, Toast.LENGTH_LONG).show();
            }
        });

        return rootView;
    }

    private void setBarChart(){


        List<BarEntry> entriesCont = new ArrayList<>();
        List<BarEntry> entriesAvg = new ArrayList<>();

        Random rand = new Random();
        for (int week = 0; week < 10; week++){
            entriesCont.add(new BarEntry(week, rand.nextInt(100) + 1));
            entriesAvg.add(new BarEntry(week, rand.nextInt(40)));
        }

        BarDataSet setCont = new BarDataSet(entriesCont, "User Score");
        setCont.setColor(ColorTemplate.COLORFUL_COLORS[0]);
        BarDataSet setAvg = new BarDataSet(entriesAvg, "Average Score");
        setAvg.setColors(ColorTemplate.COLORFUL_COLORS[1]);

        BarData data = new BarData(setCont, setAvg);
        data.setBarWidth(BAR_WIDTH);
        mBarChart.setData(data);
        mBarChart.groupBars(0, GROUP_SPACE, BAR_SPACE);

        Description desc = new Description();
        desc.setText("Contribution bar chart");
        mBarChart.setDescription(desc);


        XAxis xAxis = mBarChart.getXAxis();
        xAxis.setAxisMaximum(xAxis.getAxisMaximum() + GROUP_SPACE + BAR_WIDTH);
        xAxis.setCenterAxisLabels(true);
        mBarChart.invalidate();

    }

    private void setTimeline(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(rootView.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        List<KanbanPost> kanbanPosts = new ArrayList<>();
        for (int i = 0; i < 20; i++){
            kanbanPosts.add(new KanbanPost());
            kanbanPosts.get(i).setTitle("KanbanPost Title #" + i);
            kanbanPosts.get(i).setDescription("KanbanPost Description #" + i);
//            kanbanPosts.get(i).setEndDate("0529");
        }
        TimelineAdapter timelineAdapter = new TimelineAdapter(kanbanPosts);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(timelineAdapter);
    }
    private void bind_views(){
        mBarChart = rootView.findViewById(R.id.template_contribution_barchart);
        mRecyclerView = rootView.findViewById(R.id.result_timeline_recycler);
        spinnerSelectUser = rootView.findViewById(R.id.result_user_spinner);
    }
}

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

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import skku.teamplay.R;
import skku.teamplay.activity.adapter.TimelineAdapter;
import skku.teamplay.model.Quest;

public class ResultFragment extends Fragment {
    final static private float GROUP_SPACE= 0.15f;
    final static private float BAR_SPACE = 0.1f;
    final static private float BAR_WIDTH = 0.45f;

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

        List<Quest> quests = new ArrayList<>();
        for (int i = 0; i < 20; i++){
            quests.add(new Quest());
            quests.get(i).setTitle("Quest Title #" + i);
            quests.get(i).setDescription("Quest Description #" + i);
//            quests.get(i).setDueAt("0529");
        }
        TimelineAdapter timelineAdapter = new TimelineAdapter(quests);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(timelineAdapter);
    }
    private void bind_views(){
        mBarChart = rootView.findViewById(R.id.template_contribution_barchart);
        mRecyclerView = rootView.findViewById(R.id.result_timeline_recycler);
    }
}

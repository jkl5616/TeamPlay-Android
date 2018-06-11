package skku.teamplay.activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import skku.teamplay.R;

/**
 * Created by woorim on 2018-04-16.
 * Test activity to test MPAndroidChart
 */

public class ChartTestActivity extends AppCompatActivity{

    @BindView(R.id.rchart)
    RadarChart mChart;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_test);
        ButterKnife.bind(this);
        toolbar.setTitle("MPChart Test");
        setSupportActionBar(toolbar);

        mChart.getDescription().setEnabled(false);

        mChart.setWebLineWidth(1f);
        mChart.setWebColor(Color.GRAY);
        mChart.setWebLineWidthInner(1f);
        mChart.setWebColorInner(Color.GRAY);
        mChart.setWebAlpha(100);

        setData();

        Typeface mTfLight = Typeface.create("sans-serif-light", Typeface.NORMAL);
        XAxis xAxis = mChart.getXAxis();
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

        YAxis yAxis = mChart.getYAxis();
        yAxis.setTextColor(Color.BLACK);
        yAxis.setTypeface(mTfLight);
        yAxis.setLabelCount(4, false);
        yAxis.setTextSize(9f);
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(80f);
        yAxis.setDrawLabels(false);

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setTypeface(mTfLight);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(5f);
        l.setTextColor(Color.BLACK);

    }

    @Override
    public void onResume() {
        super.onResume();
        mChart.animateY(1400);
    }

    public void setData() {

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

        mChart.setData(data);
        mChart.invalidate();
    }


}

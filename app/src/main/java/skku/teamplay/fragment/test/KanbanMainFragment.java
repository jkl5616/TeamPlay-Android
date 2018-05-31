package skku.teamplay.fragment.test;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import butterknife.ButterKnife;
import butterknife.OnClick;
import skku.teamplay.R;
import skku.teamplay.activity.adapter.KanbanQuestlistAdapter;
import skku.teamplay.activity.dialog.QuestPopupDialog;
import skku.teamplay.activity.test.KanbanViewpagerActivity;
import skku.teamplay.models.Quest;

/**
 * Created by ddjdd on 2018-05-31.
 */

public class KanbanMainFragment extends  Fragment{
    private KanbanQuestlistAdapter adapter;

    @BindView(R.id.textProfileInfo) TextView textProfileInfo;
    @BindView(R.id.questList) ListView questList;
    @BindView(R.id.btnKanban) Button btnKanban;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_kanban, container, false);
        ButterKnife.bind(this, rootView);
        adapter =  new KanbanQuestlistAdapter();
        View header = getLayoutInflater().inflate(R.layout.list_quest_header_template, null, false);
        questList.addHeaderView(header);
        questList.setAdapter(adapter);

        questList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
//                Quest newQuest = (Quest)adapter.getItem(position);
//                Intent intent = new Intent(getActivity(), QuestPopupDialog.class);
//                newQuest.putExtraIntent(intent);
//                getActivity().startActivityForResult(intent, 1);
            }
            public void onClick(View v) { }
        });

        return rootView;
    }

    @OnClick(R.id.btnKanban)
    void onBtnKanbanClick(){
        Intent intent = new Intent(getActivity(), KanbanViewpagerActivity.class);
        startActivity(intent);
    }

}

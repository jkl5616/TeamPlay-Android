package skku.teamplay.fragment.test;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import skku.teamplay.R;
import skku.teamplay.adapter.TimelineAdapter;
import skku.teamplay.api.OnRestApiListener;
import skku.teamplay.api.RestApi;
import skku.teamplay.api.RestApiResult;
import skku.teamplay.api.RestApiTask;
import skku.teamplay.api.impl.GetAppointmentByTeam;
import skku.teamplay.api.impl.GetKanbanPostByBoard;
import skku.teamplay.api.impl.GetKanbansByTeam;
import skku.teamplay.api.impl.res.AppointmentListResult;
import skku.teamplay.api.impl.res.KanbanBoardListResult;
import skku.teamplay.api.impl.res.KanbanPostListResult;
import skku.teamplay.app.TeamPlayApp;
import skku.teamplay.model.Appointment;
import skku.teamplay.model.KanbanBoard;
import skku.teamplay.model.KanbanPost;
import skku.teamplay.model.User;
import skku.teamplay.util.AppointKanbanCombined;

public class ResultFragment extends Fragment implements OnRestApiListener{
    final static private float GROUP_SPACE= 0.15f;
    final static private float BAR_SPACE = 0.1f;
    final static private float BAR_WIDTH = 0.45f;
    private boolean KANBAN_FLAG, APPOINT_FLAG;
    private ArrayList<KanbanBoard> kanbanBoards;
    private ArrayList<Appointment> appointments;
    private ArrayList<AppointKanbanCombined> combinedLists;
    private int total_kanbanBoards, idx_kanban;
    MaterialSpinner spinnerSelectUser;
    ViewGroup rootView;
    BarChart mBarChart;
    @BindView(R.id.result_timeline_recycler)RecyclerView mRecyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView != null) return rootView;
        rootView = (ViewGroup)inflater.inflate(R.layout.fragment_result, container, false);
        ButterKnife.bind(this, rootView);
        KANBAN_FLAG = APPOINT_FLAG = false;

        bind_views();
        setBarChart();

        List<User> userList = TeamPlayApp.getAppInstance().getUserList();
        User curUser = TeamPlayApp.getAppInstance().getUser();
        List<String> userNames = new ArrayList<>();
        userNames.add(curUser.getName() + "/" + curUser.getEmail());
        for (User user : userList){
            userNames.add(user.getName() + "/" + user.getEmail());
        }
        spinnerSelectUser.setItems(userNames);
        spinnerSelectUser.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                Toast.makeText(rootView.getContext(), "Clicked" + item, Toast.LENGTH_LONG).show();
            }
        });

        new RestApiTask(this).execute(new GetKanbansByTeam(TeamPlayApp.getAppInstance().getTeam().getId()));
        new RestApiTask(this).execute(new GetAppointmentByTeam(TeamPlayApp.getAppInstance().getTeam().getId()));
        return rootView;
    }

    ArrayList<KanbanPost> kanbanPosts = new ArrayList<>();
    @Override
    public void onRestApiDone(RestApiResult restApiResult) {
        switch (restApiResult.getApiName()){
            case "getkanbansbyteam":
                KanbanBoardListResult kanbanBoardListResult = (KanbanBoardListResult) restApiResult;
                kanbanBoards = kanbanBoardListResult.getKanbanList();
                total_kanbanBoards = kanbanBoards.size();
                for (KanbanBoard kanbanBoard : kanbanBoards){
                    new RestApiTask(this).execute(new GetKanbanPostByBoard(kanbanBoard.getId()));
                }

                break;
            case "getkanbanpostbyboard":
                idx_kanban++;
                KanbanPostListResult kanbanPostListResult = (KanbanPostListResult) restApiResult;
                ArrayList<KanbanPost> temp = kanbanPostListResult.getPostList();
                for (KanbanPost post : temp){
                    kanbanPosts.add(post);
                }
                if (idx_kanban == total_kanbanBoards) KANBAN_FLAG = true;
                break;

            case "getappointmentbyteam":
                AppointmentListResult appointmentListResult = (AppointmentListResult) restApiResult;
                appointments = appointmentListResult.getAppointmentList();
                APPOINT_FLAG = true;
                break;

        }

        if (KANBAN_FLAG && APPOINT_FLAG) {
            combinedLists = AppointKanbanCombined.combine(kanbanPosts, appointments);
            //sort
            Collections.sort(combinedLists, new Comparator<AppointKanbanCombined>() {
                @Override
                public int compare(AppointKanbanCombined appointKanbanCombined, AppointKanbanCombined t1) {
                    return appointKanbanCombined.getEndDate().compareTo(t1.getEndDate());
                }
            });
            setTimeline();
        }
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



        TimelineAdapter timelineAdapter = new TimelineAdapter(combinedLists);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(timelineAdapter);
    }

    private void bind_views(){
        mBarChart = rootView.findViewById(R.id.template_contribution_barchart);
        spinnerSelectUser = rootView.findViewById(R.id.result_user_spinner);
    }



}

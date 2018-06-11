package skku.teamplay.fragment.test;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.security.auth.callback.Callback;

import butterknife.BindView;
import butterknife.ButterKnife;
import skku.teamplay.Manifest;
import skku.teamplay.R;
import skku.teamplay.activity.test.TabTestActivity;
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
import skku.teamplay.util.CaptureActivity;
import skku.teamplay.util.Util;
import skku.teamplay.widget.AnimationUtil;

public class ResultFragment extends Fragment implements OnRestApiListener {
    final static private float GROUP_SPACE = 0.15f;
    final static private float BAR_SPACE = 0.1f;
    final static private float BAR_WIDTH = 0.45f;
    private boolean KANBAN_FLAG, APPOINT_FLAG;
    private ArrayList<KanbanBoard> kanbanBoards;
    private ArrayList<KanbanPost> kanbanPosts = new ArrayList<>();
    private ArrayList<Appointment> appointments;
    private ArrayList<AppointKanbanCombined> combinedLists;
    private ArrayList<User> userList;
    private User curUser;
    private int total_kanbanBoards, idx_kanban;
    TimelineAdapter timelineAdapter;


    ImageButton btnScreenShot;
    MaterialSpinner spinnerSelectUser;
    ViewGroup rootView;
    TextView chartDes;
    //    BarChart mBarChart;
    PieChart mPieChart;
    @BindView(R.id.result_timeline_recycler)
    RecyclerView mRecyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView != null) return rootView;
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_result, container, false);
        ButterKnife.bind(this, rootView);
        KANBAN_FLAG = APPOINT_FLAG = false;

        bind_views();
//        setBarChart();

        userList = TeamPlayApp.getAppInstance().getUserList();
        curUser = TeamPlayApp.getAppInstance().getUser();
        List<String> userNames = new ArrayList<>();
        userNames.add(curUser.getName() + "/" + curUser.getEmail());
        for (User user : userList) {
            if (user.getId() == curUser.getId()) continue;
            userNames.add(user.getName() + "/" + user.getEmail());
        }

        spinnerSelectUser.setItems(userNames);
        spinnerSelectUser.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                String[] tokens = item.toString().split("/");
                if (tokens[1] != null) {
                    int userID = getUserNameFromID(tokens[1]);
                    if (userID != -1) {
                        setUser(userID);
                        int pos = -1; //find position with userID
                        for (int idx = 0; idx < userList.size(); idx++) {
                            if (userList.get(idx).getEmail().equals(tokens[1])) {
                                pos = idx;
                                break;
                            }
                        }
                        if (pos != -1) mPieChart.highlightValue(new Highlight(pos, 0, 0));
                    } else {
                        Toast.makeText(getContext(), "선택된 유저를 찾지 못했습니다.", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        new RestApiTask(this).execute(new GetKanbansByTeam(TeamPlayApp.getAppInstance().getTeam().getId()));
        new RestApiTask(this).execute(new GetAppointmentByTeam(TeamPlayApp.getAppInstance().getTeam().getId()));

        initmPieChart();
        return rootView;
    }

    private void setUser(int userID) {
        setTimeline(filterCombinedLists(userID)); //update timeline
        updateChartDescription(userID);

    }

    //    private void setBarChart(){
//        List<BarEntry> entriesCont = new ArrayList<>();
//        List<BarEntry> entriesAvg = new ArrayList<>();
//
//        Random rand = new Random();
//        for (int week = 0; week < 10; week++){
//            entriesCont.add(new BarEntry(week, rand.nextInt(100) + 1));
//            entriesAvg.add(new BarEntry(week, rand.nextInt(40)));
//        }
//
//        BarDataSet setCont = new BarDataSet(entriesCont, "User Score");
//        setCont.setColor(ColorTemplate.COLORFUL_COLORS[0]);
//        BarDataSet setAvg = new BarDataSet(entriesAvg, "Average Score");
//        setAvg.setColors(ColorTemplate.COLORFUL_COLORS[1]);
//
//        BarData data = new BarData(setCont, setAvg);
//        data.setBarWidth(BAR_WIDTH);
//        mBarChart.setData(data);
//        mBarChart.groupBars(0, GROUP_SPACE, BAR_SPACE);
//
//        Description desc = new Description();
//        desc.setText("Contribution bar chart");
//        mBarChart.setDescription(desc);
//
//
//        XAxis xAxis = mBarChart.getXAxis();
//        xAxis.setAxisMaximum(xAxis.getAxisMaximum() + GROUP_SPACE + BAR_WIDTH);
//        xAxis.setCenterAxisLabels(true);
//        mBarChart.invalidate();
//
//    }
    private void updateChartDescription(int userID) {
        int total_quests, total_fin;
        total_quests = total_fin = 0;
        for (AppointKanbanCombined combined : combinedLists) {
            if (combined.getType() == 0 && combined.getUser_id() == userID) {
                total_quests++;
                if (combined.getIsFinished() == 1) total_fin++;
            }
        }

        chartDes.setText("완료한 임무: " + total_fin + " 총 임무: " + total_quests);

    }

    private void initmPieChart() {
        Description des = new Description();
        des.setText("팀원 총 점수 기여도");
        mPieChart.setDescription(des);
        mPieChart.setTouchEnabled(true);
        mPieChart.setHighlightPerTapEnabled(false);
        mPieChart.setDrawHoleEnabled(true);
        mPieChart.setTransparentCircleRadius(Color.WHITE);

        mPieChart.setHoleRadius(30f);
        mPieChart.setUsePercentValues(true);
        mPieChart.animateY(1000);

    }


    private PieEntry getPieEntry(User user) {
        float scores = 0;
        for (AppointKanbanCombined combined : combinedLists) {
            if (combined.getUser_id() == user.getId()) {
                scores += combined.getReward();
            }
        }
        if (scores < 0) scores = 0;
        return new PieEntry(scores, user.getName());
    }


    private void setmPieChart() {
        List<PieEntry> entries = new ArrayList<>();
        for (User user : userList)
            entries.add(getPieEntry(user));

        PieDataSet set = new PieDataSet(entries, "점수 결과");
        set.setColors(ColorTemplate.MATERIAL_COLORS);
        set.setSliceSpace(2f);

        PieData data = new PieData(set);
        data.setValueTextColor(Color.BLACK);
        data.setValueTextSize(13f);

        mPieChart.setData(data);
        mPieChart.invalidate();

    }

    private void initTimeline(int userID) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(rootView.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        timelineAdapter = new TimelineAdapter(filterCombinedLists(userID));
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(timelineAdapter);
    }

    private ArrayList<AppointKanbanCombined> filterCombinedLists(int userID) {
        ArrayList<AppointKanbanCombined> filtered = new ArrayList<>();
        for (AppointKanbanCombined combined : combinedLists) {
            if (combined.getType() == 1 || combined.getUser_id() == userID) filtered.add(combined);
        }

        return filtered;
    }

    private void setTimeline(ArrayList<AppointKanbanCombined> filtered) {

        timelineAdapter.setCombinedList(filtered);
        timelineAdapter.notifyDataSetChanged();
    }

    private void bind_views() {
//        mBarChart = rootView.findViewById(R.id.template_contribution_barchart);
        mPieChart = rootView.findViewById(R.id.template_contribution_pie_chart);
        spinnerSelectUser = rootView.findViewById(R.id.result_user_spinner);
        chartDes = rootView.findViewById(R.id.template_contribution_text_description);
        btnScreenShot = rootView.findViewById(R.id.result_take_screenshot);
        final NestedScrollView scrollview = rootView.findViewById(R.id.scr_report);
        btnScreenShot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "보고서를 생성중입니다.", 0).show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap bitmap = Util.getBitmapFromView(scrollview, scrollview.getChildAt(0).getHeight(), scrollview.getChildAt(0).getWidth());
                        final String path = Environment.getExternalStorageDirectory() + "/teamplay_report_" + System.currentTimeMillis() + ".png";
                        try {
                            FileOutputStream output = new FileOutputStream(path);
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
                            output.close();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //Android mediascanner에 등록
                                getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
                                new MaterialDialog.Builder(getActivity()).title("결과리포트 저장 완료").content(path + "에 리포트가 저장되었습니다. 공유할까요?").positiveText(android.R.string.ok).negativeText(android.R.string.no).onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        Intent share = new Intent(Intent.ACTION_SEND);
                                        share.setType("image/png");
                                        Uri imageUri = Uri.parse(path);
                                        share.putExtra(Intent.EXTRA_STREAM, imageUri);
                                        startActivity(Intent.createChooser(share, "공유 선택"));
                                    }
                                }).show();
                            }
                        });
                    }
                }).start();

            }

        });
    }


    @Override
    public void onRestApiDone(RestApiResult restApiResult) {
        switch (restApiResult.getApiName()) {
            case "getkanbansbyteam":
                KanbanBoardListResult kanbanBoardListResult = (KanbanBoardListResult) restApiResult;
                kanbanBoards = kanbanBoardListResult.getKanbanList();
                total_kanbanBoards = kanbanBoards.size();
                for (KanbanBoard kanbanBoard : kanbanBoards) {
                    new RestApiTask(this).execute(new GetKanbanPostByBoard(kanbanBoard.getId()));
                }

                break;
            case "getkanbanpostbyboard":
                idx_kanban++;
                KanbanPostListResult kanbanPostListResult = (KanbanPostListResult) restApiResult;
                ArrayList<KanbanPost> temp = kanbanPostListResult.getPostList();
                for (KanbanPost post : temp) {
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
//            Date earliest, latest;
//            earliest = combinedLists.get(0).getEndDate();
//            latest = combinedLists.get(combinedLists.size() - 1).getEndDate();
//            Calendar cal = Calendar.getInstance();
//            cal.setTime(earliest);
            initTimeline(curUser.getId());
            setUser(curUser.getId());
            setmPieChart();
            int pos = 0;
            for (int idx = 0; idx < userList.size(); idx++) {
                if (userList.get(idx).getId() == curUser.getId()) {
                    pos = idx;
                    break;
                }
            }
            mPieChart.highlightValue(new Highlight(pos, 0, 0));
        }
    }

    private int getUserNameFromID(String IDName) {
        int userID = -1; //-1 = fail
        for (User user : userList) {
            if (user.getEmail().contentEquals(IDName)) {
                userID = user.getId();
                return userID;
            }
        }
        return userID;
    }


}

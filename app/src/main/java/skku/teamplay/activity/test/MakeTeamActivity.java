package skku.teamplay.activity.test;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import skku.teamplay.R;
import skku.teamplay.adapter.GridMemberAdapter;
import skku.teamplay.adapter.SimpleMemberAdapter;
import skku.teamplay.api.OnRestApiListener;
import skku.teamplay.api.RestApiResult;
import skku.teamplay.api.RestApiTask;
import skku.teamplay.api.impl.MakeTeam;
import skku.teamplay.api.impl.SearchUser;
import skku.teamplay.api.impl.SearchUserByCourse;
import skku.teamplay.api.impl.res.UserListResult;
import skku.teamplay.app.TeamPlayApp;
import skku.teamplay.model.Course;
import skku.teamplay.model.User;
import skku.teamplay.util.CourseList;
import skku.teamplay.util.Util;

public class MakeTeamActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, OnRestApiListener {

    User user;
    int year, month, day;

    @BindView(R.id.edittext_team_name)
    EditText edittext_team_name;
    @BindView(R.id.edittext_team_deadline)
    EditText edittext_team_deadline;
    @BindView(R.id.edittext_team_coursename)
    EditText edittext_team_course;
    @BindView(R.id.btn_maketeam)
    Button btn_maketeam;
    @BindView(R.id.btn_search_by_id)
    Button btn_search_by_id;
    @BindView(R.id.btn_search_by_timetable)
    Button btn_search_by_timetable;

    @BindView(R.id.gridview_members)
    GridView gridview_members;

    RelativeLayout layout_timetable;
    MaterialDialog dialog;
    Course t_course;
    ArrayList<User> teamMembers;
    Date deadline;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_team);
        ButterKnife.bind(this);
        user = TeamPlayApp.getAppInstance().getUser();
        teamMembers = new ArrayList<>();
        teamMembers.add(user);
        gridview_members.setNumColumns(4);
        gridview_members.setAdapter(new GridMemberAdapter(teamMembers));

        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);



        edittext_team_deadline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(MakeTeamActivity.this, MakeTeamActivity.this, year, month, day).show();
            }
        });
        edittext_team_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCoursePickDialog();
            }
        });
        btn_search_by_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSearchByIdDialog();
            }
        });
        btn_search_by_timetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSearchByCourseDialog();
            }
        });
        btn_maketeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(teamMembers.size() == 1) {
                    Snackbar.make(findViewById(android.R.id.content), "팀원을 더 추가하세요", 0).show();
                } else if(deadline == null) {
                    Snackbar.make(findViewById(android.R.id.content), "종강날짜를 설정하세요", 0).show();
                } else if(edittext_team_name.getText().toString().length() == 0) {
                    Snackbar.make(findViewById(android.R.id.content), "팀 이름을 써주세요.", 0).show();
                } else {
                    new RestApiTask(MakeTeamActivity.this).execute(new MakeTeam(edittext_team_name.getText().toString(), deadline, t_course, user.getId(), teamMembers));
                }
            }
        });
    }

    private void addUser(User user) {
        boolean contains = false;
        for(int i = 0; i < teamMembers.size(); i++) {
            if(teamMembers.get(i).getId() == user.getId()) {
                contains = true;
                break;
            }
        }
        if(contains) {
            Snackbar.make(findViewById(android.R.id.content), "이미 추가되어 있습니다", 0).show();
            return;
        }
        teamMembers.add(user);
        gridview_members.setAdapter(new GridMemberAdapter(teamMembers));
    }

    private void showSearchByIdDialog() {
        final MaterialDialog dialog =
                new MaterialDialog.Builder(this)
                        .title("팀원 검색")
                        .customView(R.layout.dialog_search_by_id, true)
                        .positiveText(android.R.string.ok)
                        .negativeText(android.R.string.cancel)
                        .build();
        dialog.show();
        final Button search = dialog.getCustomView().findViewById(R.id.btn_search);
        final ListView lv = dialog.getCustomView().findViewById(R.id.lv_result);
        final EditText id = dialog.getCustomView().findViewById(R.id.edittext_search_id);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new RestApiTask(new OnRestApiListener() {
                    @Override
                    public void onRestApiDone(RestApiResult restApiResult) {
                        if(restApiResult.getResult()) {
                            UserListResult result = (UserListResult) restApiResult;
                            final ArrayList<User> searched = result.getUserList();
                            lv.setAdapter(new SimpleMemberAdapter(searched));
                            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    hideKeyboard(MakeTeamActivity.this);
                                    dialog.dismiss();
                                    addUser(searched.get(i));
                                }
                            });
                        }
                    }
                }).execute(new SearchUser(id.getText().toString()));
            }
        });
    }

    private void showSearchByCourseDialog() {
        if(t_course == null) {
            Snackbar.make(findViewById(android.R.id.content), "먼저 과목을 지정하세요.", 0).show();
            return;
        }
        final MaterialDialog dialog =
                new MaterialDialog.Builder(this)
                        .title("팀원 검색")
                        .customView(R.layout.dialog_search_by_course, true)
                        .positiveText(android.R.string.ok)
                        .negativeText(android.R.string.cancel)
                        .build();
        dialog.show();
        final ListView lv = dialog.getCustomView().findViewById(R.id.lv_result);
        new RestApiTask(new OnRestApiListener() {
            @Override
            public void onRestApiDone(RestApiResult restApiResult) {
                if(restApiResult.getResult()) {
                    UserListResult result = (UserListResult) restApiResult;
                    final ArrayList<User> searched = result.getUserList();
                    lv.setAdapter(new SimpleMemberAdapter(searched));
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            hideKeyboard(MakeTeamActivity.this);
                            dialog.dismiss();
                            addUser(searched.get(i));
                        }
                    });
                }
            }
        }).execute(new SearchUserByCourse(t_course));
    }

    private void showCoursePickDialog() {
        if(user.getTimetable().length() == 0) {
            Snackbar.make(findViewById(android.R.id.content), "과목을 선택하시려면 프로필 화면에서 시간표를 등록하세요", Snackbar.LENGTH_LONG).show();
        }
        String cols[] = {"#FE816D", "#68C4AF", "#45B4E7", "#D187FE", "#ffb331", "#4573E7", "#6AECF4", "#ADA7FC", "#95CB9C", "#01579B"};
        final View rootlayout = getLayoutInflater().inflate(R.layout.activity_team_timetable, null);
        dialog =
                new MaterialDialog.Builder(this)
                        .title("과목 선택")
                        .customView(rootlayout, true)
                        .negativeText(android.R.string.cancel)
                        .build();
        dialog.show();
        layout_timetable = rootlayout.findViewById(R.id.layout_timetable);
        layout_timetable.post(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        renderTimeTableDialog();
                    }
                });
            }
        });
    }

    public void renderTimeTableDialog() {
        Gson gson = new Gson();
        ArrayList<User> userList = TeamPlayApp.getAppInstance().getUserList();
        String cols[] = {"#FE816D","#68C4AF","#45B4E7","#D187FE","#ffb331","#4573E7","#6AECF4","#ADA7FC","#95CB9C","#01579B"};
        ArrayList<Integer> colArray = new ArrayList<Integer>();
        String alpha = "77";
        for(int i = 0; i < cols.length; i++) {
            colArray.add(Color.parseColor("#"+alpha+cols[i].split("#")[1]));
        }


        Integer colors[] =  colArray.toArray(new Integer[colArray.size()]);

        int i = 0;
        int layoutHeight = layout_timetable.getMeasuredHeight();
        int layoutWidth = layout_timetable.getMeasuredWidth();

        CourseList courseList = gson.fromJson(user.getTimetable(), CourseList.class);
        for(int day = 0; day < 5; day++) {
            ArrayList<Course> courses = courseList.get(day);
            for (Course course : courses) {
                int color = colors[(i++) % colors.length];
                LinearLayout course_layout = (LinearLayout) getLayoutInflater().inflate(R.layout.layout_timetable_course_small, null);
                course_layout.setBackgroundColor(color);
                course_layout.setTag(course);
                course_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        setTeamCourse((Course) view.getTag());
                    }
                });
                TextView tv_coursename = (TextView) course_layout.findViewById(R.id.tv_coursename);
                tv_coursename.setText(course.getName());
                int width = layoutWidth/5;
                int height = layoutHeight*course.getTime()/780;
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
                int left = layoutWidth*day/5;
                int top = layoutHeight*(course.getTop()-540)/780;
                params.setMargins(left, top, 0, 0);
                course_layout.setLayoutParams(params);
                layout_timetable.addView(course_layout);
            }
        }
    }

    private void setTeamCourse(Course course) {
        t_course = course;
        edittext_team_course.setText(course.getName()+"-"+course.getProf());
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        edittext_team_deadline.setText(String.format("%d-%d-%d", i, i1 + 1, i2));
        deadline = Util.dateFromYMD(i, i1, i2);
    }

    @Override
    public void onRestApiDone(RestApiResult restApiResult) {
        if(restApiResult.getResult()) {
            startActivity(new Intent(MakeTeamActivity.this, ProfileActivity.class));
            finish();
        }
    }

    public void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}

package skku.teamplay.activity.test;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import skku.teamplay.R;
import skku.teamplay.app.TeamPlayApp;
import skku.teamplay.model.Course;
import skku.teamplay.model.Team;
import skku.teamplay.model.User;
import skku.teamplay.util.CourseList;

public class TimetableViewActivity extends AppCompatActivity {

    @BindView(R.id.layout_timetable)
    RelativeLayout layout_timetable;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_timetable);
        ButterKnife.bind(this);

        layout_timetable.post(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        drawTimeTable();
                    }
                });
            }
        });
    }

    private void drawTimeTable() {
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
        for (User user : userList) {
            if (user.getTimetable().length() == 0) continue;
            CourseList courseList = gson.fromJson(user.getTimetable(), CourseList.class);
            int color = colors[(i++) % colors.length];
            for(int day = 0; day < 5; day++) {
                ArrayList<Course> courses = courseList.get(day);
                for (Course course : courses) {
                    LinearLayout course_layout = (LinearLayout) getLayoutInflater().inflate(R.layout.layout_timetable_course, null);
                    course_layout.setBackgroundColor(color);
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
    }
}

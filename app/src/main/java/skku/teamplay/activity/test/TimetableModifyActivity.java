package skku.teamplay.activity.test;

import android.app.Activity;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.alamkanak.weekview.DateTimeInterpreter;
import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import skku.teamplay.R;
import skku.teamplay.app.TeamPlayApp;
import skku.teamplay.model.Appointment;
import skku.teamplay.model.Course;
import skku.teamplay.model.User;
import skku.teamplay.util.CourseList;
import skku.teamplay.util.Util;

public class TimetableModifyActivity extends AppCompatActivity {

    @BindView(R.id.weekView)
    WeekView weekView;

    int id = 0;
    User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable_modify);
        user = TeamPlayApp.getAppInstance().getUser();
        ButterKnife.bind(this);
        id = 0;
        weekView.setNumberOfVisibleDays(7);

        weekView.setDateTimeInterpreter(new DateTimeInterpreter() {
            @Override
            public String interpretDate(Calendar date) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("EEEE", Locale.getDefault());
                    return sdf.format(date.getTime()).toUpperCase();
                } catch (Exception e) {
                    e.printStackTrace();
                    return "";
                }
            }

            @Override
            public String interpretTime(int hour) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, 0);

                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
                    return sdf.format(calendar.getTime());
                } catch (Exception e) {
                    e.printStackTrace();
                    return "";
                }
            }
        });

        weekView.setMonthChangeListener(new MonthLoader.MonthChangeListener() {
            @Override
            public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
                Util.setColorIndex(0);
                Gson gson = new Gson();
                Calendar cal = Calendar.getInstance();
                cal.set(newYear, newMonth, 1);
                ArrayList<WeekViewEvent> events = new ArrayList<WeekViewEvent>();

                ArrayList<Course> totalCourse = new ArrayList<>();
                //코스

                CourseList courseList = gson.fromJson(user.getTimetable(), CourseList.class);
                for (int day = 0; day < 5; day++) {
                    ArrayList<Course> courses = courseList.get(day);
                    for (Course course : courses) {
                        course.setDay(day);
                        totalCourse.add(course);
                    }
                }

                int totalday = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

                ArrayList<String> temp = new ArrayList<>();
                for (int i = 0; i < totalCourse.size(); i++) {
                    Course course = totalCourse.get(i);
                    String comp = course.getName() + "-" + course.getTime() + "-" + course.getProf() + "-" + course.getTop();
                    if (temp.contains(comp)) continue;
                    temp.add(comp);
                    int start = getFirstDay(newYear, newMonth, course.getDay());
                    for (int m = start; m <= totalday; m += 7) {
                        Calendar startTime = Calendar.getInstance();

                        int startHour = course.getStart() / 60 + 9;
                        int startMinute = course.getStart() % 60;
                        int endHour = (course.getStart() + course.getTime()) / 60 + 9;
                        int endMinute = (startMinute + course.getTime()) % 60;

                        startTime.set(Calendar.DAY_OF_MONTH, m);
                        startTime.set(Calendar.MONTH, newMonth - 1);
                        startTime.set(Calendar.YEAR, newYear);

                        startTime.set(Calendar.HOUR_OF_DAY, startHour);
                        startTime.set(Calendar.MINUTE, startMinute);

                        Calendar endTime = (Calendar) startTime.clone();
                        endTime.set(Calendar.HOUR_OF_DAY, endHour);
                        endTime.set(Calendar.MINUTE, endMinute);
                        WeekViewEvent event = new WeekViewEvent(id++, course.getName(), startTime, endTime);
                        event.setColor(Util.nextColor());
                        events.add(event);
                        Log.e("TeamPlay", "add event "+course.getName()+"\n"+course.getProf());
                    }
                }
                return events;
            }
        });

        weekView.setEventLongPressListener(new WeekView.EventLongPressListener() {
            @Override
            public void onEventLongPress(WeekViewEvent event, RectF eventRect) {

            }
        });

        weekView.setEmptyViewLongPressListener(new WeekView.EmptyViewLongPressListener() {
            @Override
            public void onEmptyViewLongPress(Calendar time) {

            }
        });

        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, 1);
        weekView.goToDate(c);
    }

    public int getFirstDay(int year, int month, int day) {
        day += 2;
        if (day == 8) day = 1;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, day);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.YEAR, year);
        return calendar.get(Calendar.DATE);
    }

}

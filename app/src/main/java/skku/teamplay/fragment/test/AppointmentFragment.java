package skku.teamplay.fragment.test;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Locale;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.OverScroller;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alamkanak.weekview.DateTimeInterpreter;
import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;

import skku.teamplay.R;
import skku.teamplay.app.TeamPlayApp;
import skku.teamplay.model.Course;
import skku.teamplay.model.User;
import skku.teamplay.util.CourseList;
import skku.teamplay.util.Util;

/**
 * Created by woorim on 2018. 6. 5..
 */

public class AppointmentFragment extends Fragment {

    private View rootView;
    private int id = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (rootView != null) return rootView;
        rootView = inflater.inflate(R.layout.fragment_appointment, null);
        final WeekView weekView = rootView.findViewById(R.id.weekView);
        weekView.setNumberOfVisibleDays(5);
        weekView.setDateTimeInterpreter(new DateTimeInterpreter() {
            @Override
            public String interpretDate(Calendar date) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("M월 d일", Locale.getDefault());
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
                Gson gson = new Gson();
                Calendar cal = Calendar.getInstance();
                cal.set(newYear, newMonth, 1);
                ArrayList<WeekViewEvent> events = new ArrayList<WeekViewEvent>();

                ArrayList<Course> totalCourse = new ArrayList<>();
                //코스
                for (User user : TeamPlayApp.getAppInstance().getUserList()) {
                    CourseList courseList = gson.fromJson(user.getTimetable(), CourseList.class);
                    for (int day = 0; day < 5; day++) {
                        ArrayList<Course> courses = courseList.get(day);
                        for (Course course : courses) {
                            course.setDay(day);
                            totalCourse.add(course);
                        }
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
                    }
                }
                //add courses

                return events;
            }
        });
        weekView.goToHour(9.0f);


        return rootView;
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

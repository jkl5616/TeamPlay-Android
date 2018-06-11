package skku.teamplay.activity.test;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
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
import skku.teamplay.api.OnRestApiListener;
import skku.teamplay.api.RestApiResult;
import skku.teamplay.api.RestApiTask;
import skku.teamplay.api.impl.AddAppointment;
import skku.teamplay.api.impl.Login;
import skku.teamplay.api.impl.UpdateTimeTable;
import skku.teamplay.api.impl.res.LoginResult;
import skku.teamplay.app.TeamPlayApp;
import skku.teamplay.fragment.test.AppointmentFragment;
import skku.teamplay.model.Appointment;
import skku.teamplay.model.Course;
import skku.teamplay.model.User;
import skku.teamplay.util.CourseList;
import skku.teamplay.util.Util;
import skku.teamplay.util.WeekViewEventWithTag;

public class TimetableModifyActivity extends AppCompatActivity implements OnRestApiListener{

    @BindView(R.id.weekView)
    WeekView weekView;

    int id = 0;
    User user;
    Gson gson = new Gson();
    CourseList courseList;

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
                Calendar cal = Calendar.getInstance();
                cal.set(newYear, newMonth, 1);
                ArrayList<WeekViewEvent> events = new ArrayList<WeekViewEvent>();

                ArrayList<Course> totalCourse = new ArrayList<>();
                //코스

                courseList = gson.fromJson(user.getTimetable(), CourseList.class);
                if (courseList == null) courseList = new CourseList();
                for(int i = 0, size = courseList.size(); i < 7 - size; i++) {
                    courseList.add(new ArrayList<Course>());
                }

                for (int day = 0; day < 7; day++) {
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
                        WeekViewEventWithTag event = new WeekViewEventWithTag(id++, course.getName() + "\n" + course.getProf(), startTime, endTime);
                        event.setTag(course);
                        event.setColor(Util.nextColor());
                        events.add(event);
                        Log.e("TeamPlay", "add event " + course.getName() + "\n" + course.getProf());
                    }
                }
                return events;
            }
        });

        weekView.setEventLongPressListener(new WeekView.EventLongPressListener() {
            @Override
            public void onEventLongPress(WeekViewEvent event, RectF eventRect) {
                final Course c = (Course) ((WeekViewEventWithTag) event).getTag();
                //삭제하기
                new MaterialDialog.Builder(TimetableModifyActivity.this).content(c.getName() + " (" + c.getProf() + ") 를 시간표에서 삭제하시겠습니까?").positiveText("삭제하기").negativeText("취소").onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        ArrayList<Course> courses = courseList.get(c.getDay());
                        for(Course co: courses) {
                            if(co.equals(c)) {
                                courses.remove(co);
                                break;
                            }
                        }
                        String json = gson.toJson(courseList);
                        Snackbar.make(findViewById(android.R.id.content), "삭제가 완료되었습니다", 0).show();
                        new RestApiTask(TimetableModifyActivity.this).execute(new UpdateTimeTable(user.getEmail(), user.getPw(), json));
                    }
                }).show();
            }
        });

        weekView.setEmptyViewLongPressListener(new WeekView.EmptyViewLongPressListener() {
            @Override
            public void onEmptyViewLongPress(Calendar time) {
                showAddCourseDialog(time);
            }
        });

        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, 2);
        weekView.goToDate(c);
        weekView.goToHour(9.0f);
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

    EditText edittext_day;
    EditText edittext_start_time;
    EditText edittext_end_time;
    EditText edittext_course_name;
    EditText edittext_course_prof;

    Calendar startDate;
    Calendar endDate;

    private void showAddCourseDialog(final Calendar time) {
        final int day_of_week = time.get(Calendar.DAY_OF_WEEK);
        startDate = (Calendar) time.clone();
        endDate = (Calendar) time.clone();
        endDate.set(Calendar.HOUR_OF_DAY, startDate.get(Calendar.HOUR_OF_DAY) + 1);
        MaterialDialog dialog =
                new MaterialDialog.Builder(this)
                        .title("시간표 추가")
                        .customView(R.layout.dialog_add_course, true)
                        .positiveText("추가")
                        .negativeText(android.R.string.cancel)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                Course c = new Course();
                                long diff = endDate.getTime().getTime() - startDate.getTime().getTime();
                                endDate.set(Calendar.HOUR_OF_DAY, 9);
                                endDate.set(Calendar.MINUTE, 0);
                                long diff2 = startDate.getTime().getTime() - endDate.getTime().getTime();
                                long diffMinutes = diff / 60000;
                                long diffMinutes2 = diff2 / 60000;
                                // Variables for parsing from everything web
                                c.setTop(0);
                                c.setHeight(0);

                                c.setStart((int)diffMinutes2);
                                c.setTime((int)diffMinutes);
                                c.setName(edittext_course_name.getText().toString());
                                c.setProf(edittext_course_prof.getText().toString());
                                Log.e("ConvertDay", convertDay(day_of_week)+"");
                                courseList.get(convertDay(day_of_week)).add(c);
                                String json = gson.toJson(courseList);
                                Snackbar.make(findViewById(android.R.id.content), "추가가 완료되었습니다.", 0).show();
                                new RestApiTask(TimetableModifyActivity.this).execute(new UpdateTimeTable(user.getEmail(), user.getPw(), json));
                            }
                        })
                        .build();
        dialog.show();

        View layout = dialog.getCustomView();
        edittext_start_time = layout.findViewById(R.id.edittext_start_time);
        edittext_end_time = layout.findViewById(R.id.edittext_end_time);
        edittext_course_prof = layout.findViewById(R.id.edittext_course_prof);
        edittext_course_name = layout.findViewById(R.id.edittext_course_name);
        edittext_start_time.setText(Util.DATEFORMAT_HHmm.format(time.getTime()));
        time.set(Calendar.HOUR_OF_DAY, time.get(Calendar.HOUR_OF_DAY) + 1);
        edittext_end_time.setText(Util.DATEFORMAT_HHmm.format(time.getTime()));
        edittext_day = layout.findViewById(R.id.edittext_day);
        edittext_day.setText(Util.DATEFORMAT_DayOfWeek.format(startDate.getTime()));


        edittext_start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerDialog(TimetableModifyActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        startDate.set(Calendar.HOUR_OF_DAY, i);
                        startDate.set(Calendar.MINUTE, i1);
                        edittext_start_time.setText(Util.DATEFORMAT_HHmm.format(startDate.getTime()));
                    }
                }, startDate.get(Calendar.HOUR_OF_DAY), startDate.get(Calendar.MINUTE), true).show();
            }
        });

        edittext_end_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerDialog(TimetableModifyActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        endDate.set(Calendar.HOUR_OF_DAY, i);
                        endDate.set(Calendar.MINUTE, i1);
                        edittext_end_time.setText(Util.DATEFORMAT_HHmm.format(endDate.getTime()));
                    }
                }, endDate.get(Calendar.HOUR_OF_DAY), endDate.get(Calendar.MINUTE), true).show();
            }
        });
    }

    private int convertDay(int day) {
        day -= 2;
        if (day < 0) day += 7;
        return day;
    }

    @Override
    public void onRestApiDone(RestApiResult restApiResult) {
        if(restApiResult.getApiName().equals("updatetimetable")) {
            Login login = new Login(TeamPlayApp.getAppInstance().getUser().getEmail(), TeamPlayApp.getAppInstance().getUser().getPw());
            new RestApiTask(this).execute(login);
        } else if(restApiResult instanceof LoginResult) {
            LoginResult result = (LoginResult) restApiResult;
            TeamPlayApp.getAppInstance().getUser().setTimetable(result.user.getTimetable());
            Calendar c = Calendar.getInstance();
            c.set(Calendar.DAY_OF_WEEK, 2);
            weekView.goToDate(c);
        }
    }
}

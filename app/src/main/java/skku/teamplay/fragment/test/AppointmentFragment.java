package skku.teamplay.fragment.test;

import java.lang.reflect.Field;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.OverScroller;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
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
import skku.teamplay.activity.test.MakeTeamActivity;
import skku.teamplay.adapter.GridMemberAdapter;
import skku.teamplay.api.OnRestApiListener;
import skku.teamplay.api.RestApi;
import skku.teamplay.api.RestApiResult;
import skku.teamplay.api.RestApiTask;
import skku.teamplay.api.impl.AddAppointment;
import skku.teamplay.api.impl.AttendAppointment;
import skku.teamplay.api.impl.DeleteAppointment;
import skku.teamplay.api.impl.GetAppointmentByTeam;
import skku.teamplay.api.impl.GetAttendList;
import skku.teamplay.api.impl.res.AppointmentListResult;
import skku.teamplay.api.impl.res.UserListResult;
import skku.teamplay.app.TeamPlayApp;
import skku.teamplay.model.Appointment;
import skku.teamplay.model.Course;
import skku.teamplay.model.User;
import skku.teamplay.util.CourseList;
import skku.teamplay.util.Util;
import skku.teamplay.util.WeekViewEventWithTag;

/**
 * Created by woorim on 2018. 6. 5..
 */

public class AppointmentFragment extends Fragment implements OnRestApiListener {

    private View rootView;
    private long id = 0;
    private ArrayList<Long> courseIds = new ArrayList<>();
    private ArrayList<Appointment> appointments = new ArrayList<>();
    private WeekView weekView;
    private Calendar firstVisibleDay = Calendar.getInstance();
    private HashMap<String, ArrayList<Appointment>> appointmentMap;
    private int count = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (rootView != null) return rootView;
        rootView = inflater.inflate(R.layout.fragment_appointment, null);
        weekView = rootView.findViewById(R.id.weekView);

        new RestApiTask(this).execute(new GetAppointmentByTeam(TeamPlayApp.getAppInstance().getTeam().getId()));


        weekView.setNumberOfVisibleDays(5);
        weekView.setDateTimeInterpreter(new DateTimeInterpreter() {
            @Override
            public String interpretDate(Calendar date) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("M/d EE", Locale.getDefault());
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
                    String comp = course.getName() + "-" + course.getTime() + "-" + course.getTop();
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
                        courseIds.add(id);
                        WeekViewEvent event = new WeekViewEvent(id++, course.getName(), startTime, endTime);
                        event.setColor(Util.nextGrayColor());
                        events.add(event);
                    }
                }


                for (Appointment appointment : appointments) {
                    if (Util.calendarFromDate(appointment.getStartDate()).get(Calendar.MONTH) != newMonth) {
                        continue;
                    }
                    WeekViewEventWithTag event = new WeekViewEventWithTag(id++, appointment.getDescription() + "\n" + appointment.getAttend_count() + "명 참여", Util.calendarFromDate(appointment.getStartDate()), Util.calendarFromDate(appointment.getEndDate()));
                    event.setColor(Util.nextColor());
                    event.setTag(appointment);
                    events.add(event);
                    Log.e("WeekView", "add appointment " + newYear + " " + newMonth);
                }
                //add courses

                return events;
            }
        });

        weekView.setEventLongPressListener(new WeekView.EventLongPressListener() {
            @Override
            public void onEventLongPress(WeekViewEvent event, RectF eventRect) {
                long id = event.getId();
                if (courseIds.contains(event.getId())) {
                    Toast.makeText(getActivity(), "이 이벤트는 수업입니다.", 0).show();
                } else {
                    Appointment appointment = (Appointment) ((WeekViewEventWithTag) event).getTag();
                    final int appointmentId = appointment.getId();
                    new MaterialDialog.Builder(getActivity()).positiveText("삭제").negativeText("취소").content(appointment.getDescription() + " 일정을 삭제하시겠습니까?").onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            new RestApiTask(AppointmentFragment.this).execute(new DeleteAppointment(appointmentId));
                        }
                    }).show();
                }
            }
        });

        weekView.setEmptyViewLongPressListener(new WeekView.EmptyViewLongPressListener() {
            @Override
            public void onEmptyViewLongPress(Calendar time) {
                //일정 추가 다이얼로그
                showAddAppointmentDialog(time);
            }
        });

        weekView.setOnEventClickListener(new WeekView.EventClickListener() {
            @Override
            public void onEventClick(WeekViewEvent event, RectF eventRect) {
                if (courseIds.contains(event.getId())) {
                    Toast.makeText(getActivity(), "이 이벤트는 수업입니다.", 0).show();
                } else {
                    Appointment appointment = (Appointment) ((WeekViewEventWithTag) event).getTag();
                    showAppointmentStatusDialog(appointment);
                }
            }
        });

        weekView.goToHour(9.0f);

        final TextView date = rootView.findViewById(R.id.textview_date);
        final ListView lv_appointment = rootView.findViewById(R.id.lv_appointment);
        weekView.setScrollListener(new WeekView.ScrollListener() {
            @Override
            public void onFirstVisibleDayChanged(Calendar newFirstVisibleDay, Calendar oldFirstVisibleDay) {
                firstVisibleDay = newFirstVisibleDay;
                Calendar middle = (Calendar) firstVisibleDay.clone();
                middle.add(Calendar.DATE, 2);
                date.setText(Util.DATEFORMAT_MdEE.format(middle.getTime()));
                if (appointmentMap == null) return;
                ArrayList<Appointment> appointmentList = appointmentMap.get(Util.DATEFORMAT_yyyyMMdd.format(middle.getTime()));
                if (appointmentList == null) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, new ArrayList<String>());
                    lv_appointment.setAdapter(adapter);
                } else {
                    ArrayList<String> strings = new ArrayList<>();
                    for (Appointment appointment : appointmentList) {
                        strings.add(Util.DATEFORMAT_HHmms.format(appointment.getStartDate().getTime()) + " - " + Util.DATEFORMAT_HHmms.format(appointment.getEndDate().getTime()) + " " + appointment.getDescription());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, strings);
                    lv_appointment.setAdapter(adapter);
                }
            }
        });


        return rootView;
    }

    EditText edittext_date;
    EditText edittext_start_time;
    EditText edittext_end_time;
    EditText edittext_description;

    Calendar startDate;
    Calendar endDate;

    private void showAppointmentStatusDialog(final Appointment appointment) {
        new RestApiTask(new OnRestApiListener() {
            @Override
            public void onRestApiDone(RestApiResult restApiResult) {
                ArrayList<User> users = new ArrayList<>();
                UserListResult result = (UserListResult) restApiResult;
                for (User u : result.getUserList()) {
                    users.add(TeamPlayApp.getAppInstance().getUserById(u.getId()));
                }
                final boolean meattends = users.contains(TeamPlayApp.getAppInstance().getUser());
                final MaterialDialog dialog = new MaterialDialog.Builder(getActivity()).customView(R.layout.dialog_appointment, true).title(appointment.getDescription()).positiveText("닫기").negativeText(meattends ? "참여 취소하기" : "참여하기").onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        new RestApiTask(AppointmentFragment.this).execute(new AttendAppointment(TeamPlayApp.getAppInstance().getUser().getId(), appointment.getId(), meattends ? 0 : 1));
                    }
                }).build();
                View layout = dialog.getCustomView();
                TextView tv_info = layout.findViewById(R.id.tv_info);
                tv_info.append("시작: " + Util.DATEFORMAT_HHmm.format(appointment.getStartDate()) + "\n");
                tv_info.append("끝: " + Util.DATEFORMAT_HHmm.format(appointment.getEndDate()));
                GridView gridView = layout.findViewById(R.id.gridview_attend);
                gridView.setAdapter(new GridMemberAdapter(users));
                TextView tv_attend = layout.findViewById(R.id.tv_teamitem_text_contributors);
                tv_attend.setText("참여자 - " + users.size() + "명");
                dialog.show();
            }
        }).execute(new GetAttendList(appointment.getId()));
    }

    private void showAddAppointmentDialog(final Calendar time) {

        startDate = (Calendar) time.clone();
        endDate = (Calendar) time.clone();
        endDate.set(Calendar.HOUR_OF_DAY, startDate.get(Calendar.HOUR_OF_DAY) + 1);
        MaterialDialog dialog =
                new MaterialDialog.Builder(getActivity())
                        .title("일정 추가")
                        .customView(R.layout.dialog_add_appointment, true)
                        .positiveText("추가")
                        .negativeText(android.R.string.cancel)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                firstVisibleDay.add(Calendar.DATE, 2);
                                new RestApiTask(AppointmentFragment.this).execute(new AddAppointment(TeamPlayApp.getAppInstance().getTeam().getId(), startDate.getTime(), endDate.getTime(), edittext_description.getText().toString()));
                            }
                        })
                        .build();
        dialog.show();

        View layout = dialog.getCustomView();
        edittext_date = layout.findViewById(R.id.edittext_date);
        edittext_start_time = layout.findViewById(R.id.edittext_start_time);
        edittext_end_time = layout.findViewById(R.id.edittext_end_time);
        edittext_description = layout.findViewById(R.id.edittext_description);
        edittext_date.setText(Util.DATEFORMAT_yyyyMMdd.format(time.getTime()));
        edittext_start_time.setText(Util.DATEFORMAT_HHmm.format(time.getTime()));
        time.set(Calendar.HOUR_OF_DAY, time.get(Calendar.HOUR_OF_DAY) + 1);
        edittext_end_time.setText(Util.DATEFORMAT_HHmm.format(time.getTime()));

        edittext_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        startDate.set(i, i1, i2);
                        endDate.set(i, i1, i2);
                        edittext_start_time.setText(Util.DATEFORMAT_HHmm.format(startDate.getTime()));
                    }
                }, time.get(Calendar.YEAR), time.get(Calendar.MONTH), time.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        edittext_start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
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
                new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
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

    public int getFirstDay(int year, int month, int day) {
        day += 2;
        if (day == 8) day = 1;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, day);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.YEAR, year);
        return calendar.get(Calendar.DATE);
    }

    @Override
    public void onRestApiDone(RestApiResult restApiResult) {
        if (restApiResult instanceof AppointmentListResult) {
            AppointmentListResult result = (AppointmentListResult) restApiResult;
            appointments = result.getAppointmentList();
            Calendar adjust = (Calendar) firstVisibleDay.clone();
            if (count++ == 0) {
                adjust.add(Calendar.DATE, -2);
            }
            appointmentMap = new HashMap<>();
            for (Appointment appointment : appointments) {
                String dateString = Util.DATEFORMAT_yyyyMMdd.format(appointment.getStartDate());
                ArrayList<Appointment> appointmentListOfDate = appointmentMap.get(dateString);
                if (appointmentListOfDate == null) {
                    appointmentListOfDate = new ArrayList<Appointment>();
                    appointmentMap.put(dateString, appointmentListOfDate);
                }
                appointmentListOfDate.add(appointment);
            }
            for (ArrayList<Appointment> list : appointmentMap.values()) {
                Collections.sort(list);
            }
            weekView.goToDate(adjust);
        } else {
            new RestApiTask(this).execute(new GetAppointmentByTeam(TeamPlayApp.getAppInstance().getTeam().getId()));
        }
    }
}

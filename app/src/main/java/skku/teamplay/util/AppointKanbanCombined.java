package skku.teamplay.util;

import android.support.annotation.NonNull;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

import skku.teamplay.model.Appointment;
import skku.teamplay.model.KanbanBoard;
import skku.teamplay.model.KanbanPost;

public class AppointKanbanCombined implements Comparable<AppointKanbanCombined>{
    private Date startDate;
    private Date endDate;
    private String description;
    private String title;
    private int team_id;
    private int attend_count;

    public static ArrayList<AppointKanbanCombined> combine(ArrayList<KanbanPost> kanbanPosts, ArrayList<Appointment> appointments) {
        ArrayList<AppointKanbanCombined> combinedList = new ArrayList<>();
        for (KanbanPost kanban : kanbanPosts){
            AppointKanbanCombined temp = new AppointKanbanCombined();
            temp.setStartDate(kanban.getStartDate());
            temp.setEndDate(kanban.getEndDate());
            temp.setDescription(kanban.getDescription());
            temp.setTitle(kanban.getTitle());
            temp.setTeam_id(-1); //unknown
            temp.setAttend_count(-1); //unknown
            combinedList.add(temp);
        }
        for (Appointment appointment : appointments){
            AppointKanbanCombined temp = new AppointKanbanCombined();
            temp.setStartDate(appointment.getStartDate());
            temp.setEndDate(appointment.getEndDate());
            temp.setDescription(appointment.getDescription());
            temp.setTitle("[일정]");
            temp.setTeam_id(appointment.getTeam_id());
            temp.setAttend_count(appointment.getAttend_count());
            combinedList.add(temp);
        }

        return combinedList;
    }


    @Override
    public int compareTo(@NonNull AppointKanbanCombined appointKanbanCombined) {
        return getEndDate().compareTo(appointKanbanCombined.getEndDate());
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

    public void setAttend_count(int attend_count) {
        this.attend_count = attend_count;
    }


    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public int getTeam_id() {
        return team_id;
    }

    public int getAttend_count() {
        return attend_count;
    }
}
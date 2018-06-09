package skku.teamplay.model;

import android.support.annotation.NonNull;

import java.util.Date;

public class Appointment implements Comparable<Appointment> {

    private Date startDate;
    private Date endDate;
    private String description;
    private int team_id;

    public Appointment(Date startDate, Date endDate, String description, int team_id) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.team_id = team_id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

    @Override
    public int compareTo(@NonNull Appointment appointment) {
        return startDate.compareTo(appointment.startDate);
    }
}

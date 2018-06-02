package skku.teamplay.api.impl;

import java.util.Date;

import skku.teamplay.api.GenericResult;
import skku.teamplay.api.RestApi;
import skku.teamplay.api.RestApiResult;

public class AddPenalty extends RestApi {

    private int team_id;
    private int user_id;
    private int minute;
    private Date date;
    private String description;

    public AddPenalty(int team_id, int user_id, int minute, Date date, String description) {
        this.team_id = team_id;
        this.user_id = user_id;
        this.minute = minute;
        this.date = date;
        this.description = description;
    }

    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Class<? extends RestApiResult> getResultClass() {
        return GenericResult.class;
    }
}

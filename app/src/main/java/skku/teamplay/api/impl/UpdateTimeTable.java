package skku.teamplay.api.impl;

import skku.teamplay.api.GenericResult;
import skku.teamplay.api.RestApi;
import skku.teamplay.api.RestApiResult;

/**
 * Created by woorim on 2018. 6. 4..
 */

public class UpdateTimeTable extends RestApi{
    private String email;
    private String pw;
    private String timetable;

    public UpdateTimeTable(String email, String pw, String timetable) {
        this.email = email;
        this.pw = pw;
        this.timetable = timetable;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getTimetable() {
        return timetable;
    }

    public void setTimetable(String timetable) {
        this.timetable = timetable;
    }

    @Override
    public Class<? extends RestApiResult> getResultClass() {
        return GenericResult.class;
    }
}

package skku.teamplay.model;

import java.util.Date;

/**
 * Created by woorim on 2018. 5. 31..
 */

public class User {
    private int id;
    private String email;
    private String pw;
    private String name;
    private String firebase_token;
    private String timetable;
    private Date lastlogin_date;

    //for test only
    public User(String name, int id) {
        this.name = name;
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirebase_token() {
        return firebase_token;
    }

    public void setFirebase_token(String firebase_token) {
        this.firebase_token = firebase_token;
    }

    public String getTimetable() {
        return timetable;
    }

    public void setTimetable(String timetable) {
        this.timetable = timetable;
    }

    public Date getLastlogin_date() {
        return lastlogin_date;
    }

    public void setLastlogin_date(Date lastlogin_date) {
        this.lastlogin_date = lastlogin_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof User) {
            return ((User) obj).getId() == getId();
        }
        return false;
    }
}

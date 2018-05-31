package skku.teamplay.model;

import java.sql.Date;

/**
 * Created by woorim on 2018. 5. 31..
 */

public class Team {
    private int id;
    private String name;
    private Date date;
    private String coursename;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }
}

package skku.teamplay.model;

import java.util.Date;

/**
 * Created by woorim on 2018. 5. 31..
 */

public class Team {
    private int id;
    private String name;
    private Date deadline;
    private String coursename;
    private int count;
    private int leader_id;

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

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getLeader_id() {
        return leader_id;
    }

    public void setLeader_id(int leader_id) {
        this.leader_id = leader_id;
    }
}

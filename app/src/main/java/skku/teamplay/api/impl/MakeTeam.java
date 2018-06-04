package skku.teamplay.api.impl;

import java.util.ArrayList;
import java.util.Date;

import skku.teamplay.api.GenericResult;
import skku.teamplay.api.RestApi;
import skku.teamplay.api.RestApiResult;
import skku.teamplay.model.Course;
import skku.teamplay.model.User;

public class MakeTeam extends RestApi {
    private String name;
    private Date deadline;
    private Course course;
    private int leader_id;
    private ArrayList<User> members;

    public MakeTeam(String name, Date deadline, Course course, int leader_id, ArrayList<User> members) {
        this.name = name;
        this.deadline = deadline;
        this.course = course;
        this.leader_id = leader_id;
        this.members = members;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public int getLeader_id() {
        return leader_id;
    }

    public void setLeader_id(int leader_id) {
        this.leader_id = leader_id;
    }

    @Override
    public Class<? extends RestApiResult> getResultClass() {
        return GenericResult.class;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public ArrayList<User> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<User> members) {
        this.members = members;
    }
}


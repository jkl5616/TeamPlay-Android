package skku.teamplay.api.impl;

import java.util.ArrayList;
import java.util.Date;

import skku.teamplay.api.GenericResult;
import skku.teamplay.api.RestApi;
import skku.teamplay.api.RestApiResult;
import skku.teamplay.model.Course;
import skku.teamplay.model.User;

public class ModifyTeam extends RestApi {
    private int id;
    private String name;
    private Date deadline;
    private String coursename;
    private int leader_id;
    private ArrayList<User> addmembers;

    public ModifyTeam(int id, String name, Date deadline, String coursename, int leader_id, ArrayList<User> addmembers) {
        this.id = id;
        this.name = name;
        this.deadline = deadline;
        this.coursename = coursename;
        this.leader_id = leader_id;
        this.addmembers = addmembers;
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



    public ArrayList<User> getAddmembers() {
        return addmembers;
    }

    public void setAddmembers(ArrayList<User> addmembers) {
        this.addmembers = addmembers;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}


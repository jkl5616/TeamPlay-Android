package skku.teamplay.model;

import java.io.Serializable;

/**
 * Created by ddjdd on 2018-06-01.
 */

public class MainQuest implements Serializable {
    private int id;
    private int teamId;
    private String title;
    private int page;

    public MainQuest() {    }

    public MainQuest(int id,
                     int teamId,
                     String title,
                     int page) {
        this.id = id;
        this.teamId = teamId;
        this.title = title;
        this.page = page;
    }

    public int getId() { return this.id; }
    public int getTeamId() { return this.teamId; }
    public String getTitle() { return this.title; }
    public int getPage() { return this.page; }

    public void setId(int id) { this.id = id; }
    public void setTeamId(int teamId) { this.teamId = teamId; }
    public void setTitle(String title) { this.title = title; }
    public void setPage(int page) { this.page = page; }
}

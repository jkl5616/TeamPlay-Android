package skku.teamplay.api.impl;

import java.util.Date;

import skku.teamplay.api.GenericResult;
import skku.teamplay.api.RestApi;
import skku.teamplay.api.RestApiResult;

public class AddKanbanBoard extends RestApi {

    private int team_id;
    private String name;
    private int page;

    public AddKanbanBoard(int team_id, String name, int page) {
        this.team_id = team_id;
        this.name = name;
        this.page = page;
    }

    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    @Override
    public Class<? extends RestApiResult> getResultClass() {
        return GenericResult.class;
    }
}

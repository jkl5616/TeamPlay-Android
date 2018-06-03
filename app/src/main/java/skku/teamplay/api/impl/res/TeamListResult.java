package skku.teamplay.api.impl.res;

import java.util.ArrayList;

import skku.teamplay.api.RestApiResult;
import skku.teamplay.model.Team;

public class TeamListResult extends RestApiResult {
    private ArrayList<Team> teamList;

    public ArrayList<Team> getTeamList() {
        return teamList;
    }

    public void setTeamList(ArrayList<Team> teamList) {
        this.teamList = teamList;
    }
}

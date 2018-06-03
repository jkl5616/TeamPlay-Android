package skku.teamplay.api.impl;

import java.util.ArrayList;

import skku.teamplay.api.RestApi;
import skku.teamplay.api.RestApiResult;
import skku.teamplay.api.impl.res.UserListResult;
import skku.teamplay.model.User;

public class GetAllUsersByTeam extends RestApi {

    private int team_id;

    public GetAllUsersByTeam(int team_id) {
        this.team_id = team_id;
    }

    @Override
    public Class<? extends RestApiResult> getResultClass() {
        return UserListResult.class;
    }

    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }
}

package skku.teamplay.api.impl;

import skku.teamplay.api.RestApi;
import skku.teamplay.api.RestApiResult;
import skku.teamplay.api.impl.res.TeamListResult;

public class GetTeamByUser extends RestApi {

    private int user_id;

    public GetTeamByUser(int user_id) {
        this.user_id = user_id;
    }

    @Override
    public Class<? extends RestApiResult> getResultClass() {
        return TeamListResult.class;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}

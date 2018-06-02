package skku.teamplay.api.impl;

import java.util.ArrayList;

import skku.teamplay.api.RestApi;
import skku.teamplay.api.RestApiResult;
import skku.teamplay.api.impl.res.UserListResult;
import skku.teamplay.model.User;

public class GetAllUsersByTeam extends RestApi {
    @Override
    public Class<? extends RestApiResult> getResultClass() {
        return UserListResult.class;
    }
}

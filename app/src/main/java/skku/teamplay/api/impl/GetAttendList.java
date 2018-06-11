package skku.teamplay.api.impl;

import skku.teamplay.api.RestApi;
import skku.teamplay.api.RestApiResult;
import skku.teamplay.api.impl.res.UserListResult;

/**
 * Created by woorim on 2018. 6. 11..
 */

public class GetAttendList extends RestApi {

    private int appointment_id;

    public GetAttendList(int appointment_id) {
        this.appointment_id = appointment_id;
    }

    @Override
    public Class<? extends RestApiResult> getResultClass() {
        return UserListResult.class;
    }
}

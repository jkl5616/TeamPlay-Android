package skku.teamplay.api.impl;

import skku.teamplay.api.RestApi;
import skku.teamplay.api.RestApiResult;
import skku.teamplay.api.impl.res.AppointmentListResult;

public class GetAppointmentByTeam extends RestApi {
    private int team_id;

    public GetAppointmentByTeam(int team_id) {
        this.team_id = team_id;
    }


    @Override
    public Class<? extends RestApiResult> getResultClass() {
        return AppointmentListResult.class;
    }

    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }
}

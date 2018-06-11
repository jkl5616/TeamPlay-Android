package skku.teamplay.api.impl;

import skku.teamplay.api.GenericResult;
import skku.teamplay.api.RestApi;
import skku.teamplay.api.RestApiResult;

/**
 * Created by woorim on 2018. 6. 11..
 */

public class DeleteAppointment extends RestApi {

    private int appointment_id;

    public DeleteAppointment(int appointment_id) {
        this.appointment_id = appointment_id;
    }

    @Override
    public Class<? extends RestApiResult> getResultClass() {
        return GenericResult.class;
    }

    public int getAppointment_id() {
        return appointment_id;
    }

    public void setAppointment_id(int appointment_id) {
        this.appointment_id = appointment_id;
    }
}

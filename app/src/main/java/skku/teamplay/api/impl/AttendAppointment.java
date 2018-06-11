package skku.teamplay.api.impl;

import skku.teamplay.api.RestApi;
import skku.teamplay.api.RestApiResult;

/**
 * Created by woorim on 2018. 6. 11..
 */

public class AttendAppointment extends RestApi {
    private int user_id;
    private int appointment_id;
    private int attend;

    public AttendAppointment(int user_id, int appointment_id, int attend) {
        this.user_id = user_id;
        this.appointment_id = appointment_id;
        this.attend = attend;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getAppointment_id() {
        return appointment_id;
    }

    public void setAppointment_id(int appointment_id) {
        this.appointment_id = appointment_id;
    }

    public int getAttend() {
        return attend;
    }

    public void setAttend(int attend) {
        this.attend = attend;
    }

    @Override
    public Class<? extends RestApiResult> getResultClass() {
        return null;
    }
}

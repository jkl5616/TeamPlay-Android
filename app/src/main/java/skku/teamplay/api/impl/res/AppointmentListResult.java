package skku.teamplay.api.impl.res;

import java.util.ArrayList;

import skku.teamplay.api.RestApiResult;
import skku.teamplay.model.Appointment;

public class AppointmentListResult extends RestApiResult {
    private ArrayList<Appointment> appointmentList;

    public ArrayList<Appointment> getAppointmentList() {
        return appointmentList;
    }

    public void setAppointmentList(ArrayList<Appointment> appointmentList) {
        this.appointmentList = appointmentList;
    }
}

package skku.teamplay.util;

import java.util.Calendar;
import java.util.Date;

import skku.teamplay.api.OnRestApiListener;
import skku.teamplay.api.RestApiResult;
import skku.teamplay.api.RestApiTask;
import skku.teamplay.api.impl.UpdateToken;

/**
 * Created by woorim on 2018. 5. 15..
 */

public class Util {
    public static void updateToken() {
        try {
            UpdateToken updateToken = new UpdateToken();
            String email = SharedPreferencesUtil.getString("user_email");
            String pw = SharedPreferencesUtil.getString("user_pw");

            if (email != null && pw != null) {
                updateToken.setEmail(SharedPreferencesUtil.getString("user_email"));
                updateToken.setPw(SharedPreferencesUtil.getString("user_pw"));
                updateToken.setToken(SharedPreferencesUtil.getString("firebase_token"));
                new RestApiTask(new OnRestApiListener() {
                    @Override
                    public void onRestApiDone(RestApiResult restApiResult) {

                    }
                }).execute(updateToken);
            }
        } catch (Exception e) {

        }
    }

    public Date dateFromYMD(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        return cal.getTime();
    }

    public Calendar calendarFromDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }
}

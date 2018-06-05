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
            String token = SharedPreferencesUtil.getString("firebase_token");

            if (email != null && pw != null && token != null && token.length() > 0) {
                updateToken.setEmail(email);
                updateToken.setPw(pw);
                updateToken.setToken(token);
                new RestApiTask(new OnRestApiListener() {
                    @Override
                    public void onRestApiDone(RestApiResult restApiResult) {

                    }
                }).execute(updateToken);
            }
        } catch (Exception e) {

        }
    }

    public static Date dateFromYMD(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        return cal.getTime();
    }

    public static Calendar calendarFromDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }
}

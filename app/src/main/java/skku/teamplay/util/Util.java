package skku.teamplay.util;

import android.graphics.Color;

import com.google.firebase.iid.FirebaseInstanceId;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import skku.teamplay.api.OnRestApiListener;
import skku.teamplay.api.RestApiResult;
import skku.teamplay.api.RestApiTask;
import skku.teamplay.api.impl.UpdateToken;

/**
 * Created by woorim on 2018. 5. 15..
 */

public class Util {
    public static SimpleDateFormat DATEFORMAT_yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat DATEFORMAT_HHmm = new SimpleDateFormat("HH시 mm분");
    public static SimpleDateFormat DATEFORMAT_HHmms = new SimpleDateFormat("HH:mm");
    public static SimpleDateFormat DATEFORMAT_MdEE = new SimpleDateFormat("M/d EE", Locale.getDefault());
    public static SimpleDateFormat DATEFORMAT_DayOfWeek = new SimpleDateFormat("EEEE", Locale.getDefault());

    public static void updateToken() {
        try {
            UpdateToken updateToken = new UpdateToken();
            String email = SharedPreferencesUtil.getString("user_email");
            String pw = SharedPreferencesUtil.getString("user_pw");
            String token = SharedPreferencesUtil.getString("firebase_token");
            if(token == null) token = FirebaseInstanceId.getInstance().getToken();
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

    private static boolean inited = false;
    private static Integer colors[];
    private static Integer graycolors[];
    private static int index = 0;
    private static int index2 = 0;

    public static void setColorIndex(int iz) {
        index = iz;
    }

    private static void initColors() {
        String cols[] = {"#FE816D","#68C4AF","#45B4E7","#D187FE","#ffb331","#4573E7","#f4726a","#ADA7FC","#95CB9C","#01579B"};
        ArrayList<Integer> colArray = new ArrayList<Integer>();
        String alpha = "ff";
        for(int i = 0; i < cols.length; i++) {
            colArray.add(Color.parseColor("#"+alpha+cols[i].split("#")[1]));
        }
        colors = colArray.toArray(new Integer[colArray.size()]);
    }

    public static int nextColor() {
        if(!inited) {
            initColors();
        }
        return colors[(index++) % colors.length];

    }

    private static void initGrayColors() {
        String cols[] = {"#FE816D","#68C4AF","#45B4E7","#D187FE","#ffb331","#4573E7","#f4726a","#ADA7FC","#95CB9C","#01579B"};
        ArrayList<Integer> colArray = new ArrayList<Integer>();
        String alpha = "80";
        for(int i = 0; i < cols.length; i++) {
            colArray.add(manipulateColor(Color.parseColor("#"+alpha+cols[i].split("#")[1]), 0.2f));
        }
        graycolors = colArray.toArray(new Integer[colArray.size()]);
    }

    public static int manipulateColor(int color, float factor) {
        int a = Color.alpha(color);
        int r = Math.round(Color.red(color) * factor);
        int g = Math.round(Color.green(color) * factor);
        int b = Math.round(Color.blue(color) * factor);
        return Color.argb(a,
                Math.min(r,255),
                Math.min(g,255),
                Math.min(b,255));
    }

    public static int nextGrayColor() {
        if(!inited) {
            initGrayColors();
        }
        return graycolors[(index2++) % graycolors.length];

    }

}

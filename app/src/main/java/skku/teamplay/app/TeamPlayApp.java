package skku.teamplay.app;

import android.app.Application;

import com.google.firebase.messaging.FirebaseMessaging;

/**
 * Created by woorim on 2018-04-15.
 * Application class to manage data easily.
 */

public class TeamPlayApp extends Application {
    private static TeamPlayApp instance;
    public static TeamPlayApp getAppInstance() { return instance; }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
    }

}

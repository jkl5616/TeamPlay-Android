package skku.teamplay.app;

import android.app.Application;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.HashMap;

import skku.teamplay.model.Team;
import skku.teamplay.model.User;

/**
 * Created by woorim on 2018-04-15.
 * Application class to manage data easily.
 */

public class TeamPlayApp extends Application {
    private static TeamPlayApp instance;
    public static TeamPlayApp getAppInstance() { return instance; }

    //접속한 유저.
    private User user;
    //같은 팀에 있는 유저 저장
    private ArrayList<User> userList;
    //현재 팀
    private Team team;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    private HashMap<Integer, User> userMap;

    public ArrayList<User> getUserList() {
        return userList;
    }

    public void setUserList(ArrayList<User> userList) {
        this.userList = userList;
        userMap = new HashMap<>();
        for(User u: userList) {
            userMap.put(u.getId(), u);
        }
    }

    public User getUserById(int id) {
        return userMap.get(id);
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}

package skku.teamplay.api.impl.res;

import java.util.ArrayList;

import skku.teamplay.api.RestApiResult;
import skku.teamplay.model.User;

public class UserListResult extends RestApiResult {
    private ArrayList<User> userList;

    public ArrayList<User> getUserList() {
        return userList;
    }

    public void setUserList(ArrayList<User> userList) {
        this.userList = userList;
    }
}

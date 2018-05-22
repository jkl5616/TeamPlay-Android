package skku.teamplay.models;

/**
 * Created by woorim on 2018. 4. 17..
 */

public class User {
    private String userName;
    private int imageNum;

    public User(String userName, int imageNum) {
        this.userName = userName;
        imageNum = imageNum;
    }

    public String getUserName(){
        return this.userName;
    }

    public int getImageNum(){
        return this.imageNum;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    public void setImageNum(int num){
        this.imageNum = num;
    }
}

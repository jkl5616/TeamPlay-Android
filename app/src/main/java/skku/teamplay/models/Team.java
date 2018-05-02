package skku.teamplay.models;

/**
 * Created by woorim on 2018. 4. 17..
 */

public class Team {
    private String team_name;
    private int team_id;
    private int category_type;

    public Team(String team_name){
        this.team_name = team_name;
    }
    public void setTeam_name(String team_name){
        this.team_name = team_name;
    }

    public String getTeam_name(){
        return this.team_name;
    }

    public void setTeam_id(int team_id){
        this.team_id = team_id;
    }

    public int getTeam_id(){
        return this.team_id;
    }

    public void setCategory_type(int category_type){
        this.category_type = category_type;
    }

    public int getCategory_type(){
        return this.category_type;
    }
}

package skku.teamplay.models;

import android.content.Intent;

public class Quest {
    private String title;
    private String description;
    private boolean finish;
    private String startAt, dueAt;
    private String owner;
    private String id;
    private int rewardType, reward;

    public Quest() {    }

    public Quest(String title,
                 String description,
                 boolean finish,
                 String startAt, String dueAt,
                 String owner,
                 String id,
                 int rewardType, int reward) {
        this.title = title;
        this.description = description;
        this.finish = finish;               // initial -> false
        this.startAt = startAt;           // initial -> current time
        this.dueAt = dueAt;
        this.owner = owner;
        this.id = id;
        this.rewardType = rewardType;       // 1 전투력, 2 지갑, 3 서포트
        this.reward = reward;
    }

    public void delete (){
        //delete this quest
    }
    public void change () {
        // change this quest
    }
    public void makeFinish(){
        this.finish = !this.finish;
    }

    public String getID() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public boolean getFinish() { return finish; }
    public String getStartAt() { return startAt; }
    public String getDueAt() { return dueAt; }
    public String getOwner() { return owner; }
    public int getRewardType() { return rewardType; }
    public int getReward() { return reward; }

    public void setID(String id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setFinish(boolean finish) { this.finish = finish; }
    public void setStartAt(String startAt) { this.startAt = startAt; }
    public void setDueAt(String dueAt) { this.dueAt = dueAt; }
    public void setOwner(String owner) { this.owner = owner; }
    public void setRewardType(int rewardType) { this.rewardType = rewardType; }
    public void setReward(int reward) { this.reward = reward; }

    public void putExtraIntent(Intent newIntent) {
        newIntent.putExtra("id", this.id);
        newIntent.putExtra("title", this.title);
        newIntent.putExtra("description", this.description);
        newIntent.putExtra("startAt", this.startAt);
        newIntent.putExtra("dueAt", this.dueAt);
        newIntent.putExtra("type", this.rewardType);
        newIntent.putExtra("reward", this.reward);
    }

    public void getExtraString(Intent data) {
        this.id = data.getStringExtra("id");
        this.title = data.getStringExtra("title");
        this.description = data.getStringExtra("description");
        this.startAt = data.getStringExtra("startAt");
        this.dueAt = data.getStringExtra("dueAt");
        this.reward = Integer.parseInt(data.getStringExtra("reward"));
        this.rewardType = Integer.parseInt(data.getStringExtra("type"));
    }
}

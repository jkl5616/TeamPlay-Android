package skku.teamplay.models;

public class Quest {
    private String title;
    private String description;
    private boolean finish;
    private String createAt, dueAt, endAt;
    private String owner;
    private int rewardType, reward;

    public Quest() {    }   // empty creator

    public Quest(String title, String description,
                 boolean finish,
                 String createAt, String dueAt, String endAt,
                 String owner,
                 int rewardType, int reward) {
        this.title = title;
        this.description = description;
        this.finish = finish;               // initial -> false
        this.createAt = createAt;           // initial -> current time
        this.dueAt = dueAt;
        this.endAt = endAt;
        this.owner = owner;
        this.rewardType = rewardType;       // 1, 2, 3
        this.reward = reward;
    }

    public void delete (){
        //delete this quest
    }
    
    public void makeFinish(){
        this.finish = !this.finish;
    }

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public boolean getFinish() { return finish; }
    public String getCreateAt() { return createAt; }
    public String getDueAt() { return dueAt; }
    public String getEndAtAt() { return endAt; }
    public String getOwner() { return owner; }
    public int getRewardType() { return rewardType; }
    public int getReward() { return reward; }

    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setFinish(boolean finish) { this.finish = finish; }
    public void setCreateAt(String createAt) { this.createAt = createAt; }
    public void setDueAt(String dueAt) { this.dueAt = dueAt; }
    public void setEndAt(String endAt) { this.endAt = endAt; }
    public void setOwner(String owner) { this.owner = owner; }
    public void setRewardType(int rewardType) { this.rewardType = rewardType; }
    public void setReward(int reward) { this.reward = reward; }
}

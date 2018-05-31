package skku.teamplay.model;

import android.content.Intent;

import java.io.Serializable;
import java.util.Date;

public class Quest implements Serializable {
    private int questId;
    private int mainQuestId;
    private String title;
    private String description;
    private boolean finish;
    private Date startAt, dueAt;
    private int writterId;
    private int ownerId;
    private int rewardType;
    private int reward;

    public Quest() {    }

    public Quest(int questId,
                 int mainQuestId,
                 String title,
                 String description,
                 boolean finish,
                 Date startAt,
                 Date dueAt,
                 int writterId,
                 int ownerId,
                 int rewardType,
                 int reward) {
        this.questId = questId;
        this.mainQuestId = mainQuestId;
        this.title = title;
        this.description = description;
        this.finish = finish;               // initial -> false
        this.startAt = startAt;           // initial -> current time
        this.dueAt = dueAt;
        this.writterId = writterId;
        this.ownerId = ownerId;
        this.rewardType = rewardType;       // 0 전투력, 1 지갑, 2 서포트
        this.reward = reward;
    }

    public int getQuestId() { return questId; }
    public int getMainQuestId() { return mainQuestId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public boolean getFinish() { return finish; }
    public Date getStartAt() { return startAt; }
    public Date getDueAt() { return dueAt; }
    public int getWritterId() { return writterId; }
    public int getOwnerId() { return ownerId; }
    public int getRewardType() { return rewardType; }
    public int getReward() { return reward; }

    public String getRewardType_String() {
        if(this.rewardType == 0) return "전투력";
        else if(this.rewardType == 1) return "지갑";
        else if(this.rewardType == 2) return "서포트";
        else return "";
    }

    public void setID(int questId) { this.questId = questId; }
    public void setMainQuestId(int mainQuestId) { this.mainQuestId = mainQuestId; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setFinish(boolean finish) { this.finish = finish; }
    public void setStartAt(Date startAt) { this.startAt = startAt; }
    public void setDueAt(Date dueAt) { this.dueAt = dueAt; }
    public void setWritterId(int writterId) { this.writterId = writterId; }
    public void setOwner(int ownerId) { this.ownerId = ownerId; }
    public void setRewardType(int rewardType) { this.rewardType = rewardType; }
    public void setReward(int reward) { this.reward = reward; }

    public void makeFinish(){
        this.finish = !this.finish;
    }
}

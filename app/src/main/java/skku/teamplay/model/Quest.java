package skku.teamplay.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import skku.teamplay.api.GenericResult;
import skku.teamplay.api.RestApi;
import skku.teamplay.api.RestApiResult;


public class Quest extends RestApi implements Serializable {
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

    @Override
    public Class<? extends RestApiResult> getResultClass() {
        return GenericResult.class;
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
    public int getStartAtYear() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        String year = format.format(startAt);
        return Integer.valueOf(year);
    }
    public int getStartAtMonth() {
        SimpleDateFormat format = new SimpleDateFormat("MM");
        String month = format.format(startAt);
        return Integer.valueOf(month);
    }
    public int getStartAtDay() {
        SimpleDateFormat format = new SimpleDateFormat("dd");
        String day = format.format(startAt);
        return Integer.valueOf(day);
    }
    public int getDueAtYear() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        String year = format.format(dueAt);
        return Integer.valueOf(year);
    }
    public int getDueAtMonth() {
        SimpleDateFormat format = new SimpleDateFormat("MM");
        String month = format.format(dueAt);
        return Integer.valueOf(month);
    }
    public int getDueAtDay() {
        SimpleDateFormat format = new SimpleDateFormat("dd");
        String day = format.format(dueAt);
        return Integer.valueOf(day);
    }
    public String getStartAtSimple(){
        SimpleDateFormat format = new SimpleDateFormat("yy.MM.dd");
        return format.format(startAt);
    }
    public String getDueAtSimple(){
        SimpleDateFormat format = new SimpleDateFormat("yy.MM.dd");
        return format.format(dueAt);
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

    public void setStartAtInt(int year, int month, int day) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        String tmp = (Integer.toString(year*10000 + month*100 + day));
        startAt = format.parse(tmp);
    }
    public void setDueAtIntint(int year, int month, int day) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        String tmp = (Integer.toString(year * 10000 + month * 100 + day));
        dueAt = format.parse(tmp);
    }
    public void setStartAtSimple(String startAtSimple) {
        SimpleDateFormat format = new SimpleDateFormat("yy.MM.dd");
        try {
            startAt = format.parse(startAtSimple);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public void setDueAtSimple(String dueAtSimple) {
        SimpleDateFormat format = new SimpleDateFormat("yy.MM.dd");
        try {
            dueAt = format.parse(dueAtSimple);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    public void makeFinish(){
        this.finish = !this.finish;
    }
}

package skku.teamplay.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import skku.teamplay.api.impl.AddKanbanPost;
import skku.teamplay.api.impl.DeleteKanbanPost;
import skku.teamplay.api.impl.ModifyKanbanPost;


public class KanbanPost implements Serializable {
    private int id;
    private int kanban_board_id;
    private String title;
    private String description;
    private int finished;
    private Date startDate, endDate;
    private int writer_user_id;
    private int owner_id;
    private int rewardType;
    private int reward;

    public KanbanPost() {    }

    public KanbanPost(int id,
                      int kanban_board_id,
                      String title,
                      String description,
                      int finished,
                      Date startDate,
                      Date endDate,
                      int writer_user_id,
                      int owner_id,
                      int rewardType,
                      int reward) {
        this.id = id;
        this.kanban_board_id = kanban_board_id;
        this.title = title;
        this.description = description;
        this.finished = finished;               // initial -> false
        this.startDate = startDate;           // initial -> current time
        this.endDate = endDate;
        this.writer_user_id = writer_user_id;
        this.owner_id = owner_id;
        this.rewardType = rewardType;       // 0 전투력, 1 지갑, 2 서포트
        this.reward = reward;
    }


    public int getId() { return id; }
    public int getKanban_board_id() { return kanban_board_id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public int getFinished() { return finished; }
    public Date getStartDate() { return startDate; }
    public Date getEndDate() { return endDate; }
    public int getWriter_user_id() { return writer_user_id; }
    public int getOwner_id() { return owner_id; }
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
        String year = format.format(startDate);
        return Integer.valueOf(year);
    }
    public int getStartAtMonth() {
        SimpleDateFormat format = new SimpleDateFormat("MM");
        String month = format.format(startDate);
        return Integer.valueOf(month);
    }
    public int getStartAtDay() {
        SimpleDateFormat format = new SimpleDateFormat("dd");
        String day = format.format(startDate);
        return Integer.valueOf(day);
    }
    public int getDueAtYear() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        String year = format.format(endDate);
        return Integer.valueOf(year);
    }
    public int getDueAtMonth() {
        SimpleDateFormat format = new SimpleDateFormat("MM");
        String month = format.format(endDate);
        return Integer.valueOf(month);
    }
    public int getDueAtDay() {
        SimpleDateFormat format = new SimpleDateFormat("dd");
        String day = format.format(endDate);
        return Integer.valueOf(day);
    }
    public String getStartAtSimple(){
        SimpleDateFormat format = new SimpleDateFormat("yy.MM.dd");
        return format.format(startDate);
    }
    public String getDueAtSimple(){
        SimpleDateFormat format = new SimpleDateFormat("yy.MM.dd");
        return format.format(endDate);
    }

    public void setID(int questId) { this.id = questId; }
    public void setKanban_board_id(int kanban_board_id) { this.kanban_board_id = kanban_board_id; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setFinished(int finished) { this.finished = finished; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }
    public void setWritter_user_id(int writter_user_id) { this.writter_user_id = writter_user_id; }
    public void setOwner(int ownerId) { this.owner_id = ownerId; }
    public void setRewardType(int rewardType) { this.rewardType = rewardType; }
    public void setReward(int reward) { this.reward = reward; }

    public void setStartAtInt(int year, int month, int day) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        String tmp = (Integer.toString(year*10000 + month*100 + day));
        startDate = format.parse(tmp);
    }
    public void setDueAtIntint(int year, int month, int day) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        String tmp = (Integer.toString(year * 10000 + month * 100 + day));
        endDate = format.parse(tmp);
    }
    public void setStartAtSimple(String startAtSimple) {
        SimpleDateFormat format = new SimpleDateFormat("yy.MM.dd");
        try {
            startDate = format.parse(startAtSimple);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public void setDueAtSimple(String dueAtSimple) {
        SimpleDateFormat format = new SimpleDateFormat("yy.MM.dd");
        try {
            endDate = format.parse(dueAtSimple);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void makeFinish(){
        this.finished = 1;
    }

    public AddKanbanPost makeAddKanbanPost() {
        AddKanbanPost addKanbanPost = new AddKanbanPost(this.kanban_board_id,
                this.title,
                this.description,
                this.finished,
                this.startDate,
                this.endDate,
                this.writer_user_id,
                this.owner_id,
                this.rewardType,
                this.reward
                );
        return addKanbanPost;
        return new AddKanbanPost(this.kanban_board_id, this.title, this.description, this.finished, this.startDate, this.endDate, this.writter_user_id, this.owner_id, this.rewardType, this.reward);
    }
    public ModifyKanbanPost makeModifiyKanbanPost() {
        return new ModifyKanbanPost(this.id, this.kanban_board_id, this.title, this.description, this.finished, this.startDate, this.endDate, this.writter_user_id, this.owner_id, this.rewardType, this.reward);
    }
    public DeleteKanbanPost makeDeleteKanbanPost() {
        return new DeleteKanbanPost(this.id);
    }
}
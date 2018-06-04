package skku.teamplay.model;

import java.util.Date;

public class temp {
    private int id;
    private int kanban_board_id;
    private Date startDate;
    private Date endDate;
    private int type;
    private int score;
    private String description;
    private String title;
    private int writer_user_id;
    private int owner_id;
    private int reward_type;
    private int reward;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getKanban_board_id() {
        return kanban_board_id;
    }

    public void setKanban_board_id(int kanban_board_id) {
        this.kanban_board_id = kanban_board_id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getWriter_user_id() {
        return writer_user_id;
    }

    public void setWriter_user_id(int writer_user_id) {
        this.writer_user_id = writer_user_id;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(int owner_id) {
        this.owner_id = owner_id;
    }

    public int getReward_type() {
        return reward_type;
    }

    public void setReward_type(int reward_type) {
        this.reward_type = reward_type;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }
}

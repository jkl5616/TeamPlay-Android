package skku.teamplay.api.impl;

import java.util.Date;

import skku.teamplay.api.GenericResult;
import skku.teamplay.api.RestApi;
import skku.teamplay.api.RestApiResult;

public class ModifyKanbanPost extends RestApi {
    int id;
    int kanban_board_id;
    String title;
    String description;
    int finished;
    Date startDate;
    Date endDate;
    int writer_user_id;
    int owner_id;
    int reward_type;
    int reward;

    public ModifyKanbanPost(int id, int kanban_board_id,
                            String title,
                            String description,
                            int finished,
                            Date startDate,
                            Date endDate,
                            int writer_user_id,
                            int owner_id,
                            int reward_type,
                            int reward) {
        this.id = id;
        this.kanban_board_id = kanban_board_id;
        this.title = title;
        this.description = description;
        this.finished = finished;
        this.startDate = startDate;
        this.endDate = endDate;
        this.writer_user_id = writer_user_id;
        this.owner_id = owner_id;
        this.reward_type = reward_type;
        this.reward = reward;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getKanban_board_id() {
        return kanban_board_id;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() { return description; }
    public int getFinished() {return finished; }
    public Date getStartDate() { return startDate; }
    public Date getEndDate() {
        return endDate;
    }
    public int getWriter_user_id() {
        return writer_user_id;
    }
    public int getOwner_id() {
        return owner_id;
    }
    public int getReward_type() {
        return reward_type;
    }
    public int getReward() {
        return reward;
    }

    public void setKanban_board_id(int kanban_board_id) {
        this.kanban_board_id = kanban_board_id;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setFinished(int finished) { this.finished = finished; }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    public void setWriter_user_id(int writer_user_id) {
        this.writer_user_id = writer_user_id;
    }
    public void setOwner_id(int owner_id) {
        this.owner_id = owner_id;
    }
    public void setReward_type(int reward_type) {
        this.reward_type = reward_type;
    }
    public void setReward(int reward) {
        this.reward = reward;
    }

    @Override
    public Class<? extends RestApiResult> getResultClass() {
        return GenericResult.class;
    }
}

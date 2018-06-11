package skku.teamplay.api.impl;

import skku.teamplay.api.GenericResult;
import skku.teamplay.api.RestApi;
import skku.teamplay.api.RestApiResult;

public class SendPenalty extends RestApi {

    private int user_id;
    private String description;

    public SendPenalty(int user_id, String description) {
        this.user_id = user_id;
        this.description = description;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Class<? extends RestApiResult> getResultClass() {
        return GenericResult.class;
    }
}

package skku.teamplay.api.impl;

import skku.teamplay.api.GenericResult;
import skku.teamplay.api.RestApi;
import skku.teamplay.api.RestApiResult;

public class DeleteKanbanBoard extends RestApi {
    private int id;

    public DeleteKanbanBoard(int id) {
        this.id = id;
    }

    @Override
    public Class<? extends RestApiResult> getResultClass() {
        return GenericResult.class;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

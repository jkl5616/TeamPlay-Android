package skku.teamplay.api.impl;

import skku.teamplay.api.RestApi;
import skku.teamplay.api.RestApiResult;
import skku.teamplay.api.impl.res.KanbanPostListResult;

public class GetKanbanPostByBoard extends RestApi {
    private int kanban_id;

    public GetKanbanPostByBoard(int kanban_id) {
        this.kanban_id = kanban_id;
    }

    public int getKanban_id() {
        return kanban_id;
    }

    public void setKanban_id(int kanban_id) {
        this.kanban_id = kanban_id;
    }

    @Override
    public Class<? extends RestApiResult> getResultClass() {
        return KanbanPostListResult.class;
    }
}

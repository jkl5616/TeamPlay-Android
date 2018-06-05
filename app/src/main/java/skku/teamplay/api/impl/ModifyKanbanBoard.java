package skku.teamplay.api.impl;

import skku.teamplay.api.GenericResult;
import skku.teamplay.api.RestApi;
import skku.teamplay.api.RestApiResult;

public class ModifyKanbanBoard extends RestApi {

    private int id;
    private String name;
    private int page;

    public ModifyKanbanBoard(int id, String name, int page) {
        this.id = id;
        this.name = name;
        this.page = page;
    }

    public String getName() { return name; }
    public int getPage() {
        return page;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setPage(int page) {
        this.page = page;
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

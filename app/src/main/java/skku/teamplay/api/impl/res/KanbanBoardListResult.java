package skku.teamplay.api.impl.res;

import java.util.ArrayList;

import skku.teamplay.api.RestApi;
import skku.teamplay.api.RestApiResult;
import skku.teamplay.model.KanbanBoard;

public class KanbanBoardListResult extends RestApiResult{
    private ArrayList<KanbanBoard> kanbanList;

    public ArrayList<KanbanBoard> getKanbanList() {
        return kanbanList;
    }

    public void setKanbanList(ArrayList<KanbanBoard> kanbanList) {
        this.kanbanList = kanbanList;
    }
}

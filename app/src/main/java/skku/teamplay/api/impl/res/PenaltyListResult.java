package skku.teamplay.api.impl.res;

import java.util.ArrayList;

import skku.teamplay.api.RestApiResult;
import skku.teamplay.model.Penalty;

public class PenaltyListResult extends RestApiResult {
    private ArrayList<Penalty> penaltyList;

    public ArrayList<Penalty> getPenaltyList() {
        return penaltyList;
    }

    public void setPenaltyList(ArrayList<Penalty> penaltyList) {
        this.penaltyList = penaltyList;
    }
}

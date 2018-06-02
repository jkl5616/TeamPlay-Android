package skku.teamplay.api.impl.res;

import java.util.ArrayList;

import skku.teamplay.api.RestApiResult;
import skku.teamplay.model.ScoreRecord;

public class ScoreRecordListResult extends RestApiResult {
    private ArrayList<ScoreRecord> scoreRecordList;

    public ArrayList<ScoreRecord> getScoreRecordList() {
        return scoreRecordList;
    }

    public void setScoreRecordList(ArrayList<ScoreRecord> scoreRecordList) {
        this.scoreRecordList = scoreRecordList;
    }
}

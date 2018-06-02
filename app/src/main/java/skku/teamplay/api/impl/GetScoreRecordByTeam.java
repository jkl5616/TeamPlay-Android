package skku.teamplay.api.impl;

import skku.teamplay.api.RestApi;
import skku.teamplay.api.RestApiResult;
import skku.teamplay.api.impl.res.ScoreRecordListResult;

public class GetScoreRecordByTeam extends RestApi {
    @Override
    public Class<? extends RestApiResult> getResultClass() {
        return ScoreRecordListResult.class;
    }
}

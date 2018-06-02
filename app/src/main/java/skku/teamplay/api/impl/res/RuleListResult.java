package skku.teamplay.api.impl.res;

import java.util.ArrayList;

import skku.teamplay.api.RestApi;
import skku.teamplay.api.RestApiResult;
import skku.teamplay.model.Rule;

public class RuleListResult extends RestApiResult {
    private ArrayList<Rule> ruleList;

    public ArrayList<Rule> getRuleList() {
        return ruleList;
    }

    public void setRuleList(ArrayList<Rule> ruleList) {
        this.ruleList = ruleList;
    }
}

package skku.teamplay.api;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

/**
 * Created by woorim on 2018. 5. 29..
 */

public abstract class RestApiResult {

    private static final String TAG = RestApiResult.class.getSimpleName();

    private String apiName;
    private boolean result;

    public String getApiName() {
        return apiName;
    }

    public RestApiResult setApiName(String api) {
        this.apiName = api;
        return this;
    }

    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}

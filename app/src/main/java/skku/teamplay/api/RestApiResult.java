package skku.teamplay.api;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

/**
 * Created by woorim on 2018. 5. 29..
 */

public abstract class RestApiResult {

    private static final String TAG = RestApiResult.class.getSimpleName();

    // operation success
    public static final int CODE_SUCCESS = 0;
    public static final int CODE_INVALID_ACCESS_CODE = 10004;
    public static final int CODE_NO_SUCH_CLASS = 10005;

    protected int resultCode;
    protected String resultMessage;

    private String apiName;


    public String getResultMessage() {
        return resultMessage;
    }

    public int getResultCode() {
        return resultCode;
    }

    public boolean isResultSucceed() {
        return resultCode == CODE_SUCCESS;
    }

    @Override
    public String toString() {
        return "resultCode : " + resultCode + ", resultMessage : "
                + resultMessage;
    }

    private static String getErrorCodeMessage(RestApiResult result) {
        return result.resultMessage;
    }

    public String getApiName() {
        return apiName;
    }

    public RestApiResult setApiName(String api) {
        this.apiName = api;
        return this;
    }
}

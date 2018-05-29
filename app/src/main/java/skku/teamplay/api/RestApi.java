package skku.teamplay.api;

import android.content.Context;

import com.google.gson.Gson;

/**
 * Created by woorim on 2018. 5. 29..
 */

public abstract class RestApi {


    public static String getBaseUrl() {
        return "http://202.182.104.206/";
    }

    public String getApiUrl() {
        String apiName = getClass().getSimpleName();
        return getBaseUrl() + apiName;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    public abstract Class<? extends RestApiResult> getResultClass();

}

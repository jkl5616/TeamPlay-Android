package skku.teamplay.api;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import skku.teamplay.util.Constants;

/**
 * Created by woorim on 2018. 5. 29..
 */

public class RestApiTask extends AsyncTask<RestApi, Void, RestApiResult> {

    private static final String TAG = RestApiTask.class.getSimpleName();

    private static final int CONNECTION_TIMEOUT = 10000;

    private final boolean LOG_CONTENT = Constants.DEBUG;

    private OnRestApiListener mListener;

    public RestApiTask(OnRestApiListener listener) {
        mListener = listener;
    }

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected RestApiResult doInBackground(RestApi... params) {
        OkHttpClient client = new OkHttpClient();

        if (params.length == 0) {
            return RestApiResultNull.get(0);
        }

        RestApi restApi = params[0];
        String apiUrl = restApi.getApiUrl();

        String requestBody = new Gson().toJson(restApi);

        RequestBody body = RequestBody.create(JSON, requestBody);
        Request request = new Request.Builder()
                .url(restApi.getApiUrl())
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String s = response.body().string();
            return new Gson().fromJson(s, restApi.getResultClass());
        } catch (Exception e) {
            e.printStackTrace();
        }


        return RestApiResultNull.get(RestApiResultNull.PARSE_FAILED).setApiName(restApi.getClass().getSimpleName());
    }


    @Override
    protected void onPostExecute(RestApiResult result) {
        super.onPostExecute(result);

        OnRestApiListener listener = mListener;
        if (listener != null) {
            listener.onRestApiDone(result);
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }


}

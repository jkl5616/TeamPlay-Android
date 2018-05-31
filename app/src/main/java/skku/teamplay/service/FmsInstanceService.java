package skku.teamplay.service;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import skku.teamplay.api.OnRestApiListener;
import skku.teamplay.api.RestApiResult;
import skku.teamplay.api.RestApiTask;
import skku.teamplay.api.impl.UpdateToken;
import skku.teamplay.util.SharedPreferencesUtil;


public class FmsInstanceService extends FirebaseInstanceIdService implements OnRestApiListener {
    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.e("TeamPlay", "Firebase inited, token : "+refreshedToken);
        SharedPreferencesUtil.putString("firebase_token", refreshedToken);
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String token) {
        UpdateToken updateToken = new UpdateToken();
        if(SharedPreferencesUtil.getString("user_email").length() > 5) {
            updateToken.setEmail(SharedPreferencesUtil.getString("user_email"));
            updateToken.setPw(SharedPreferencesUtil.getString("user_pw"));
            updateToken.setToken(token);
            new RestApiTask(this).execute(updateToken);
        }
    }

    @Override
    public void onRestApiDone(RestApiResult restApiResult) {
        //do nothing
    }
}

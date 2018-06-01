package skku.teamplay.service;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import skku.teamplay.api.OnRestApiListener;
import skku.teamplay.api.RestApiResult;
import skku.teamplay.api.RestApiTask;
import skku.teamplay.api.impl.UpdateToken;
import skku.teamplay.util.SharedPreferencesUtil;
import skku.teamplay.util.Util;


public class FmsInstanceService extends FirebaseInstanceIdService implements OnRestApiListener {
    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.e("TeamPlay", "Firebase inited, token : "+refreshedToken);
        SharedPreferencesUtil.putString("firebase_token", refreshedToken);
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String token) {
        Util.updateToken();
    }

    @Override
    public void onRestApiDone(RestApiResult restApiResult) {
        //do nothing
    }
}

package skku.teamplay.service;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FmsPushService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage rm) {
        Log.e("TeamPlay", "new firebasepush message");
    }
}

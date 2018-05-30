package skku.teamplay.service;

import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FmsPushService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage rm) {
        // do the magic
    }
}

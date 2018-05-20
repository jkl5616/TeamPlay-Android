package skku.teamplay.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import skku.teamplay.service.PenaltyService;
import skku.teamplay.util.SharedPreferencesUtil;

public class BootCompletedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("TeamPlay", "Received action: " + intent.getAction());
        int leftTime = SharedPreferencesUtil.getInt("leftTime");
        Log.e("TeamPlay", "BootCompletedReceiver");
        //Penalty is not over.
        if(leftTime > 0) {
            context.startService(new Intent(context, PenaltyService.class));
        }
    }
}

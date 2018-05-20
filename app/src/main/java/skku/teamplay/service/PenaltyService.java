package skku.teamplay.service;

import android.app.ActivityManager;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import skku.teamplay.R;
import skku.teamplay.activity.test.DummyActivity;
import skku.teamplay.activity.test.PenaltyTestActivity;
import skku.teamplay.activity.test.TabTestActivity;
import skku.teamplay.util.SharedPreferencesUtil;

public class PenaltyService extends Service {

    Handler handler = new Handler();
    Boolean stopped = false;
    WindowManager mWindowManager;
    View penaltyLayout;
    TextView textView_leftTime;
    int leftTime;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("TeamPlay", "Service created!!");
        penaltyLayout = LayoutInflater.from(this).inflate(R.layout.penalty_lock, null);
        textView_leftTime = penaltyLayout.findViewById(R.id.textView_leftTime);
        int LAYOUT_FLAG = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_PHONE;
        }
        leftTime = SharedPreferencesUtil.getInt("leftTime");
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                LAYOUT_FLAG,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD,
                PixelFormat.TRANSLUCENT);

        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mWindowManager.addView(penaltyLayout, params);
        Button btn = penaltyLayout.findViewById(R.id.button3);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopPenalty();
            }
        });
        checkStatus();
    }

    private void stopPenalty() {
        stopped = true;
        SharedPreferencesUtil.putInt("leftTime", 0);
        mWindowManager.removeView(penaltyLayout);
        stopSelf();
    }

    int count = 0;
    private void checkStatus() {
        if(count % 5 == 0) {
            SharedPreferencesUtil.putInt("leftTime", --leftTime);
            textView_leftTime.setText(String.format("%d초 남았습니다.", leftTime));
        }
        if(leftTime  <= 0) {
            stopPenalty();
        }
        if(stopped) return;
        //prohibit user from execute any applications by executing dummy activity
        Intent intent = new Intent(getApplicationContext(), DummyActivity.class);
        startActivity(intent);
        count++;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkStatus();
            }
        }, 200);
    }

}

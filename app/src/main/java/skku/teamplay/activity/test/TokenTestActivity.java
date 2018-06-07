package skku.teamplay.activity.test;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;

public class TokenTestActivity extends Activity {
    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
        TextView tv = new TextView(this);
        tv.setText(FirebaseInstanceId.getInstance().getToken());
        setContentView(tv);
    }
}

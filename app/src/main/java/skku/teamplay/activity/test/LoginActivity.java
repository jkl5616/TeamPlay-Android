package skku.teamplay.activity.test;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import skku.teamplay.R;
import skku.teamplay.api.OnRestApiListener;
import skku.teamplay.api.RestApiResult;
import skku.teamplay.api.RestApiTask;
import skku.teamplay.api.impl.Login;
import skku.teamplay.api.impl.Register;
import skku.teamplay.api.impl.UpdateToken;
import skku.teamplay.api.impl.res.LoginResult;
import skku.teamplay.app.TeamPlayApp;
import skku.teamplay.util.SharedPreferencesUtil;
import skku.teamplay.util.Util;

/**
 * Created by woorim on 2018-05-04.
 */

public class LoginActivity extends AppCompatActivity implements OnRestApiListener {
    @BindView(R.id.editEmail)
    EditText editEmail;
    @BindView(R.id.editPassword)
    EditText editPassword;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.btnSignup)
    Button btnSignup;

    int REQUEST_WRITE_STORAGE_REQUEST_CODE = 100;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        requestAppPermissions();
    }

    private void requestAppPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(LoginActivity.this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, 1234);
            }
        }

        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return;
        }

        if (hasReadPermissions() && hasWritePermissions()) {
            return;
        }

        ActivityCompat.requestPermissions(this,
                new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, REQUEST_WRITE_STORAGE_REQUEST_CODE);
    }

    private boolean hasReadPermissions() {
        return (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    private boolean hasWritePermissions() {
        return (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    @OnClick(R.id.btnLogin)
    public void onClickLogin() {
        String email, password;
        email = editEmail.getText().toString();
        password = editPassword.getText().toString();
        if (email.length() < 5 || password.length() < 6) {
            Toast.makeText(this, "Fill all", Toast.LENGTH_LONG).show();
            // 메소드화 필요
        } else {
            Login login = new Login(editEmail.getText().toString(), editPassword.getText().toString());
            new RestApiTask(this).execute(login);
        }
    }

    @OnClick(R.id.btnSignup)
    public void onBtnSignup() {
        MaterialDialog dialog =
                new MaterialDialog.Builder(this)
                        .title("가입")
                        .customView(R.layout.dialog_register, true)
                        .positiveText("가입하기")
                        .negativeText(android.R.string.cancel)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                doRegister(dialog.getCustomView());
                            }
                        })
                        .build();
        dialog.show();
    }

    private void doRegister(View registerView) {
        String id = ((EditText) registerView.findViewById(R.id.edittext_register_id)).getText().toString();
        String pw = ((EditText) registerView.findViewById(R.id.edittext_register_password)).getText().toString();
        String name = ((EditText) registerView.findViewById(R.id.edittext_register_name)).getText().toString();
        Register register = new Register();
        register.setEmail(id);
        register.setPw(pw);
        register.setName(name);
        new RestApiTask(this).execute(register);
    }

    @Override
    public void onRestApiDone(RestApiResult restApiResult) {
        switch (restApiResult.getApiName()) {
            case "login":
                LoginResult loginResult = (LoginResult) restApiResult;
                if (loginResult.getResult()) {
                    //로그인 성공
                    SharedPreferencesUtil.putString("user_email", loginResult.user.getEmail());
                    SharedPreferencesUtil.putString("user_pw", loginResult.user.getPw());
                    Util.updateToken();
                    TeamPlayApp.getAppInstance().setUser(loginResult.user);
                    //팀 선택 액티비티로 넘어감
                    startActivity(new Intent(LoginActivity.this, ProfileActivity.class));
                    finish();
                } else {
                    new MaterialDialog.Builder(this).content("로그인에 실패했습니다.").show();
                }
                break;
            case "register":
                new MaterialDialog.Builder(this).content("가입이 완료되었습니다.").show();
                break;
        }
    }
}

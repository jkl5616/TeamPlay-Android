package skku.teamplay.activity.test;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import skku.teamplay.R;

/**
 * Created by woorim on 2018-05-04.
 */

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.editEmail) EditText editEmail;
    @BindView(R.id.editPassword) EditText editPassword;
    @BindView(R.id.btnLogin) Button btnLogin;
    @BindView(R.id.btnSignup) Button btnSignup;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener =  new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    Toast.makeText(getApplicationContext(), "user is not null", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "user is null", Toast.LENGTH_LONG).show();
                }
            }
        };

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @OnClick(R.id.btnLogin)
    void onClickLogin() {
        String email, password;
        email = editEmail.getText().toString();
        password = editPassword.getText().toString();
        if(email.length() < 5 || password.length() < 6) {
            Toast.makeText(this, "Fill all", Toast.LENGTH_LONG).show();
            // 메소드화 필요
        }
        else {
            mAuth.signInWithEmailAndPassword(editEmail.getText().toString(), editPassword.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "LogIn Fail", Toast.LENGTH_LONG).show();
                                // 로그인 실패 이유 알림
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "LogIn Success", Toast.LENGTH_LONG).show();
                                // 다음 액티비티로 넘어가기
                            }
                        }
                    });
        }

    }

    @OnClick(R.id.btnSignup)
    void onBtnSignup() {
        String email, password;
        email = editEmail.getText().toString();
        password = editPassword.getText().toString();
        if(email.length() < 5 || password.length() < 6) {
            Toast.makeText(this, "Fill all", Toast.LENGTH_LONG).show();
            // 메소드화 필요
        }
        else {
            mAuth.createUserWithEmailAndPassword(editEmail.getText().toString(), editPassword.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "회원가입 시작", Toast.LENGTH_LONG).show();
                                // 회원가입 다이얼로그 시작
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "회원가입 실패", Toast.LENGTH_LONG).show();
                                // 회원가입 실패 이유 알림
                            }
                        }
                    });
        }
    }
}

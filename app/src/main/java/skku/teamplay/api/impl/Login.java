package skku.teamplay.api.impl;

import skku.teamplay.api.RestApi;
import skku.teamplay.api.RestApiResult;
import skku.teamplay.api.impl.res.LoginResult;

/**
 * Created by woorim on 2018. 5. 29..
 */

public class Login extends RestApi {

    private String email;
    private String pw;

    @Override
    public Class<? extends RestApiResult> getResultClass() {
        return LoginResult.class;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

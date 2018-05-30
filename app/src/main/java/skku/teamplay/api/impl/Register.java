package skku.teamplay.api.impl;

import skku.teamplay.api.GenericResult;
import skku.teamplay.api.RestApi;
import skku.teamplay.api.RestApiResult;

/**
 * Created by woorim on 2018. 5. 31..
 */

public class Register extends RestApi {
    private String email;
    private String pw;
    private String name;
    @Override
    public Class<? extends RestApiResult> getResultClass() {
        return GenericResult.class;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

package skku.teamplay.api.impl;

import skku.teamplay.api.RestApi;
import skku.teamplay.api.RestApiResult;
import skku.teamplay.api.impl.res.UserListResult;

public class SearchUser extends RestApi {
    //아이디, 이름등 검색 키워드
    private String keyword;

    public SearchUser(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public Class<? extends RestApiResult> getResultClass() {
        return UserListResult.class;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}

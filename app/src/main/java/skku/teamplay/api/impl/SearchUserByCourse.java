package skku.teamplay.api.impl;

import skku.teamplay.api.RestApi;
import skku.teamplay.api.RestApiResult;
import skku.teamplay.api.impl.res.UserListResult;
import skku.teamplay.model.Course;

public class SearchUserByCourse extends RestApi {

    private Course course;

    public SearchUserByCourse(Course course) {
        this.course = course;
    }

    @Override
    public Class<? extends RestApiResult> getResultClass() {
        return UserListResult.class;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}

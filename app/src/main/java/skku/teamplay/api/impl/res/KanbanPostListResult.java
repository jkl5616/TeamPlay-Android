package skku.teamplay.api.impl.res;

import java.util.ArrayList;

import skku.teamplay.api.RestApiResult;
import skku.teamplay.model.KanbanPost;
import skku.teamplay.model.temp;

public class KanbanPostListResult extends RestApiResult {
    private ArrayList<KanbanPost> postList;

    public ArrayList<KanbanPost> getPostList() {
        return postList;
    }

    public void setPostList(ArrayList<KanbanPost> postList) {
        this.postList = postList;
    }
}

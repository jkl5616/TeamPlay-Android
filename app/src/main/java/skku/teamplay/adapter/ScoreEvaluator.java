package skku.teamplay.adapter;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import skku.teamplay.model.User;
import skku.teamplay.util.AppointKanbanCombined;

public class ScoreEvaluator {
    private ArrayList<AppointKanbanCombined> allUser;
    private User selectedUser;
    private ImageView imageView;
    private TextView textView;
    private int average, selectedAvg;

    public ScoreEvaluator(User selectedUser, ArrayList<AppointKanbanCombined> allUser, ImageView imageView, TextView textView) {
        this.selectedUser = selectedUser;
        this.allUser = allUser;
        this.imageView = imageView;
        this.textView = textView;

        notifyChange();
    }

    public void setSelectedUser(User selectedUser){
        this.selectedUser = selectedUser;
    }
    public void notifyChange(){
        setAverageScore();

        this.textView.setText("Avg: " + average);
        Log.d("Avg", "All: " + average + " User: " + selectedAvg);
    }
    private void setAverageScore(){
        int cnt, selectCnt;
        cnt = selectCnt = 0;
        for (AppointKanbanCombined combined : allUser){
            if (combined.getType() == AppointKanbanCombined.KANBAN_POST && combined.getIsFinished() == 1){
                average += combined.getReward();
                cnt++;
                if (combined.getUser_id() == selectedUser.getId()){
                    selectedAvg += combined.getReward();
                    selectCnt++;
                }
            }
        }
        if (cnt != 0) average = average / cnt;

        if (selectCnt != 0)  selectedAvg = selectedAvg / selectCnt;

    }

}

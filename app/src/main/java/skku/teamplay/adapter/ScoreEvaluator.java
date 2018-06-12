package skku.teamplay.adapter;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import skku.teamplay.app.TeamPlayApp;
import skku.teamplay.model.User;
import skku.teamplay.util.AppointKanbanCombined;

public class ScoreEvaluator {
    private ArrayList<AppointKanbanCombined> allUser;
    private User selectedUser;
    private ImageView imageView;
    private TextView textView;
    final private static int NUM_TYPES = 3;
    final private static int NUM_BAD_IMAGES = 5;
    final private static int NUM_AVERAGE_IMAGES = 3;
    final private static int NUM_BEST_IMAGES = 4;
    final private static int NUM_GOOD_IMAGES = 3;
    final private static int NUM_WORST_IMAGES = 4;

    private int average, selectedAvg;
    private int[] avgForType = new int[NUM_TYPES];
    private int[] userAvgForType = new int [NUM_TYPES];
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
        Log.d("Score: ", evaluateScore() +".");
        this.textView.setText("Score: " + evaluateScore());
        setImageAndText(evaluateScore());
//        for (AppointKanbanCombined combined : allUser){
//            if (combined.getType() == AppointKanbanCombined.KANBAN_POST && combined.getReward_Type() == 2 && combined.getIsFinished() == 1)
//                Log.d ("Reward 2: ", combined.getTitle() + ": " + combined.getReward() + " USER ID: " + combined.getUser_id());
//        }
//
//        Log.d("Avg", "All: " + average + " User: " + selectedAvg);
//        for (int i = 0; i < 3; i++){
//            Log.d("Avg type:", i + ": " + avgForType[i] + ", " + userAvgForType[i]);
//        }
    }
    private void initImageAndText(){

    }
    private void setImageAndText(int score){
        //0~2 worst  3~6 bad, 7~9, avg, 10~13 good, 14~15 good
        Resources res = TeamPlayApp.getAppInstance().getResources();
        String image_name;
        Random random = new Random();
        int image_idx;
        if (score <= 2){
            image_idx = random.nextInt(NUM_WORST_IMAGES) + 1;
            image_name = "worst_" + image_idx;
        }
        else if (score > 2 && score <= 6){
            image_idx = random.nextInt(NUM_BAD_IMAGES) + 1;
            image_name = "bad_" + image_idx;
        }
        else if (score > 6 && score <= 9){
            image_idx = random.nextInt(NUM_AVERAGE_IMAGES) + 1;
            image_name = "average_" + image_idx;
        }
        else if (score > 9 && score <= 13){
            image_idx = random.nextInt(NUM_GOOD_IMAGES) + 1;
            image_name = "good_" + image_idx;
        }
        else{
            image_idx = random.nextInt(NUM_BEST_IMAGES) + 1;
            image_name = "best_" + image_idx;
        }
        int resID = res.getIdentifier(image_name, "mipmap", TeamPlayApp.getAppInstance().getPackageName());
        Log.d("image name: ", resID + ": " + image_name);
        Drawable drawable = res.getDrawable(resID, null);
        imageView.setImageDrawable(drawable);

    }
    private int evaluateScore(){
        //avg < 0.1 = 0; avg 0.1 ~ 0.5 = 2; avg 0.5 ~ 0.8 = 4; avg 0.8 ~ 1 = 6;
        //avg_type < 0.1 = 0; avg_type 0.1 ~ 0.5 = 1; avg_type 0.5 ~ 0.8 = 2; avg_type 0.8 ~ 1 = 3;
        //perfect score 15
        int total_score = 0;
        double percentile;
        percentile = (double)selectedAvg / average;
        if (percentile <= 0.1) total_score += 0;
        else if (percentile > 0.1 && percentile <= 0.5) total_score +=2;
        else if (percentile > 0.5 && percentile <= 0.8) total_score +=4;
        else if (percentile > 0.8) total_score +=6;

        for (int i = 0 ; i < NUM_TYPES; i++){
            percentile = (double)userAvgForType[i] / avgForType[i];
            if (percentile <= 0.1) total_score += 0;
            else if (percentile > 0.1 && percentile <= 0.5) total_score +=1;
            else if (percentile > 0.5 && percentile <= 0.8) total_score +=2;
            else if (percentile > 0.8) total_score +=3;
        }

        return total_score;
    }
    private void setAverageScore(){
        int cnt, selectCnt;
        int []cnt_type = new int[NUM_TYPES];
        int []selected_cnt_type = new int[NUM_TYPES];
        cnt = selectCnt = 0;
        for (int i = 0; i < NUM_TYPES; i++)
            avgForType[i] = userAvgForType[i] = 0;
        average = selectedAvg = 0;

        for (AppointKanbanCombined combined : allUser){
            if (combined.getType() == AppointKanbanCombined.KANBAN_POST && combined.getIsFinished() == 1){
                average += combined.getReward();
                cnt++;
                if (combined.getUser_id() == selectedUser.getId()){
                    selectedAvg += combined.getReward();
                    selectCnt++;
                    selected_cnt_type[combined.getReward_Type()]++;
                    userAvgForType[combined.getReward_Type()] += combined.getReward();
                }
                cnt_type[combined.getReward_Type()]++;
                avgForType[combined.getReward_Type()] += combined.getReward();
            }
        }
        if (cnt != 0) average = average / cnt;
        if (selectCnt != 0)  selectedAvg = selectedAvg / selectCnt;
        for (int i = 0; i < NUM_TYPES; i++){
            if (cnt_type[i] != 0) avgForType[i] = avgForType[i] / cnt_type[i];
            if (selected_cnt_type[i] != 0) userAvgForType[i] = userAvgForType[i] / selected_cnt_type[i];
        }
    }

}

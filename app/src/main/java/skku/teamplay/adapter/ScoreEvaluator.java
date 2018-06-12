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
    private TextView textResult;
    final private static String[] AVERAGE_TEXTS={"늦었다고 생각했을 때가 가장 늦었을 때다.\n더 늦기 전에 더 일하자", "이거 마감전까지 다 해야한다", "오늘도 내일도 했던만큼만 하자", "중간만 하자"};
    final private static String[] BAD_TEXTS={"후우... 일좀 합시다", "멘탈 챙기자아아", "으어어어어 팀플이라는 이름으로 버티자", "안돼 가지마..", "see see I can't see"};
    final private static String[] BEST_TEXTS={"넌 너무 멋있어 어플인 내가 봐도 반하겠어", "너 나랑 다음에도 팀플할래?", "팀플 좀 할줄 아는 놈인가", "우리팀 운전기사"};
    final private static String[] GOOD_TEXTS={"에이쁠 가즈아", "호수같은 그대의 눈동자에 건배", "너는 팀플할때 가장 멋져", "팀플 떡상 가즈아아"};
    final private static String[] WORST_TEXTS={"놉 안돼 돌아가 일해", "가지마..", "본 조교는 굉장히 실망했습니다."};
    final private static int NUM_TYPES = 3;
    final private static int NUM_BAD_IMAGES = BAD_TEXTS.length;
    final private static int NUM_AVERAGE_IMAGES = AVERAGE_TEXTS.length;
    final private static int NUM_BEST_IMAGES = BEST_TEXTS.length;
    final private static int NUM_GOOD_IMAGES = GOOD_TEXTS.length;
    final private static int NUM_WORST_IMAGES = WORST_TEXTS.length;
    private int average, selectedAvg;
    private int[] avgForType = new int[NUM_TYPES];
    private int[] userAvgForType = new int [NUM_TYPES];
    public ScoreEvaluator(User selectedUser, ArrayList<AppointKanbanCombined> allUser, ImageView imageView, TextView textView) {
        this.selectedUser = selectedUser;
        this.allUser = allUser;
        this.imageView = imageView;
        this.textResult = textView;

        notifyChange();
    }

    public void setSelectedUser(User selectedUser){
        this.selectedUser = selectedUser;
    }
    public void notifyChange(){
        setAverageScore();
        Log.d("Score: ", evaluateScore() +".");
//        this.textView.setText("Score: " + evaluateScore());
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

    private void setImageAndText(int score){
        //0~2 worst  3~6 bad, 7~9, avg, 10~13 good, 14~15 good
        Resources res = TeamPlayApp.getAppInstance().getResources();
        String image_name;
        Random random = new Random();
        int image_idx;
        if (score <= 2){
            image_idx = random.nextInt(NUM_WORST_IMAGES) + 1;
            image_name = "worst_" + image_idx;
            this.textResult.setText(WORST_TEXTS[image_idx - 1]);
        }
        else if (score > 2 && score <= 6){
            image_idx = random.nextInt(NUM_BAD_IMAGES) + 1;
            image_name = "bad_" + image_idx;
            this.textResult.setText(BAD_TEXTS[image_idx - 1]);
        }
        else if (score > 6 && score <= 9){
            image_idx = random.nextInt(NUM_AVERAGE_IMAGES) + 1;
            image_name = "average_" + image_idx;
            this.textResult.setText(AVERAGE_TEXTS[image_idx - 1]);
        }
        else if (score > 9 && score <= 13){
            image_idx = random.nextInt(NUM_GOOD_IMAGES) + 1;
            image_name = "good_" + image_idx;
            this.textResult.setText(GOOD_TEXTS[image_idx - 1]);
        }
        else{
            image_idx = random.nextInt(NUM_BEST_IMAGES) + 1;
            image_name = "best_" + image_idx;
            this.textResult.setText(BEST_TEXTS[image_idx - 1]);
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

package skku.teamplay.util;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

public class ChartAnimatedText {
    public static final int RECORD_STRENGTH = 0;
    public static final int RECORD_WALLET = 1;
    public static final int RECORD_SUPPORT = 2;
    private View targetView;
    private RelativeLayout layoutBase;
    private String topic;
    private int delay;
    private int option, duration, deltaY, score;
    private static final float offsetsX[] = {0.2f, 0.50f, 0.70f};
    private static final float offsetsY[] = {0.60f, 0f, 0.60f};
    private static final String type_name[] = new String[]{"전투력", "지갑", "서포트"};

    Context context;
    public ChartAnimatedText(Context context, RelativeLayout layoutBase, View view, int score, int option) {
        this.layoutBase = layoutBase;
        this.score = score;
        this.topic = topic;
        this.option = option; //0 지갑 1 전투력 2 서포트
        this.context = context;
        this.targetView = view; //radar chart

        duration = 800;
        deltaY = 50;

    }

    public void setDelay(int delay){
        this.delay = delay;
    }
    public void setDuration(int duration){
        this.duration = duration;
    }
    public int getDuration(){
        return this.duration;
    }

    public void moveYBy(int Y){
        this.deltaY = Y;
    }


    public void start(){
        TextView textView = createText();
        textView.setVisibility(View.INVISIBLE);
        layoutBase.addView(textView);

        TextAnimatedListener listener = new TextAnimatedListener(textView, layoutBase);
        textView.animate().alpha(0f).yBy(-deltaY).setDuration(this.duration).setListener(listener).setStartDelay(delay);
    }

    private TextView createText(){
        TextView textView = new TextView(context);
        ///textView.setText(text);
        textView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));


        String text, htmlTxt;
        int htmlLen = "<'font color='#000000'></font>".length();
        text = "<'font color='#000000'>" + type_name[option] + "</font>" + " ";
        if (score >= 0){
            htmlTxt = "<font color='#03d100'>+" + score + "</font>";
            text = text + htmlTxt + " <'font color='#000000'>" + "증가했습니다.</font>" ;
        }
        else{
            htmlTxt = "<font color='#cf001c'>" + score + "</font>" + " ";
            text = text + htmlTxt + " <'font color='#000000'>" + "감소했습니다.</font>" ;
        }
        textView.setText(Html.fromHtml(text));
        textView.setX(targetView.getLeft() + targetView.getWidth() * offsetsX[option] - ((text.length() - htmlLen * 3) * 10));
        textView.setY(targetView.getTop() + targetView.getHeight() * offsetsY[option]);
        textView.setAlpha(1f);

        return textView;
    }
    class TextAnimatedListener implements Animator.AnimatorListener{
        private View view;
        private RelativeLayout layoutBase;

        public TextAnimatedListener(View view, RelativeLayout layoutBase) {
            this.view = view;
            this.layoutBase = layoutBase;
        }

        @Override
        public void onAnimationStart(Animator animator) {
            this.view.setVisibility(View.VISIBLE);
        }

        @Override
        public void onAnimationEnd(Animator animator) {
            layoutBase.removeView(view);
        }

        @Override
        public void onAnimationCancel(Animator animator) {

        }

        @Override
        public void onAnimationRepeat(Animator animator) {

        }
    }
}

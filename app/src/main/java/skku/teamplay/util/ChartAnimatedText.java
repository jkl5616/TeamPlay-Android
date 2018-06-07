package skku.teamplay.util;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

public class ChartAnimatedText {
    private View targetView;
    private RelativeLayout layoutBase;
    private String text, topic;
    private int option, duration, deltaY;
    private static final float offsetsX[] = {0.45f, 0.2f, 0.75f};
    private static final float offsetsY[] = {0f, 0.55f, 0.55f};
    Context context;
    public ChartAnimatedText(Context context, RelativeLayout layoutBase, View view, String text, int option) {
        this.layoutBase = layoutBase;
        this.text = text;
        this.topic = topic;
        this.option = option; //0 지갑 1 전투력 2 서포트
        this.context = context;
        this.targetView = view; //radar chart

        duration = 500;
        deltaY = 50;

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
        layoutBase.addView(textView);

        TextAnimatedListener listener = new TextAnimatedListener(textView, layoutBase);
        textView.animate().alpha(0f).yBy(-deltaY).setDuration(this.duration).setListener(listener);
    }

    private TextView createText(){
        TextView textView = new TextView(context);
        textView.setText(text);
        textView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));


        textView.setX(targetView.getLeft() + targetView.getWidth() * offsetsX[option] - (text.length() * 12));
        textView.setY(targetView.getTop() + targetView.getHeight() * offsetsY[option]);
        textView.setAlpha(1f);
        textView.setTextColor(Color.BLACK);
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

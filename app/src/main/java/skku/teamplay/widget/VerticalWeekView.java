package skku.teamplay.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.alamkanak.weekview.WeekView;

public class VerticalWeekView extends WeekView {
    public VerticalWeekView(Context context) {
        super(context);
    }

    public VerticalWeekView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VerticalWeekView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    float x = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            x = event.getX();
        }
        event.setLocation(x, event.getY());
        return super.onTouchEvent(event);
    }
}

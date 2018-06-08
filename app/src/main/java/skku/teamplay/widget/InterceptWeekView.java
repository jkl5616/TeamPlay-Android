package skku.teamplay.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.alamkanak.weekview.WeekView;

public class InterceptWeekView extends WeekView {
    public InterceptWeekView(Context context) {
        super(context);
    }

    public InterceptWeekView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InterceptWeekView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE && getParent() != null) {
            getParent().requestDisallowInterceptTouchEvent(true);
        }
        return super.onTouchEvent(event);
    }
}

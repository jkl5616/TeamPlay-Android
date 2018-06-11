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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        event.setLocation(0, event.getY());
        return super.onTouchEvent(event);
    }
}

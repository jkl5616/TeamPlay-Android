package skku.teamplay.util;

import java.util.Calendar;

import com.alamkanak.weekview.WeekViewEvent;

/**
 * Created by woorim on 2018. 6. 11..
 */

public class WeekViewEventWithTag extends WeekViewEvent {

    Object o;

    public void setTag(Object o) {
        this.o = o;
    }

    public Object getTag() {
        return o;
    }

    public WeekViewEventWithTag(int i1, String s1, Calendar c1, Calendar c2) {
        super(i1, s1, c1, c2);
    }

    public WeekViewEventWithTag(long l1, String s1, Calendar c1, Calendar c2) {
        super(l1, s1, c1, c2);
    }

}

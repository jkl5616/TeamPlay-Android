package skku.teamplay.fragment.test;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;

import java.util.List;
import java.util.ArrayList;

import skku.teamplay.R;

/**
 * Created by woorim on 2018. 6. 5..
 */

public class AppointmentFragment extends Fragment {

    private View rootView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (rootView != null) return rootView;
        rootView = inflater.inflate(R.layout.fragment_appointment, null);
        WeekView weekView = rootView.findViewById(R.id.weekView);
        weekView.setMonthChangeListener(new MonthLoader.MonthChangeListener() {
            @Override
            public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
                ArrayList<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
                return events;
            }
        });
        return rootView;
    }
}

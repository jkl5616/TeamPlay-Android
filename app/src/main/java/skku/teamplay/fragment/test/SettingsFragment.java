package skku.teamplay.fragment.test;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import skku.teamplay.R;
import skku.teamplay.activity.test.ModifyTeamActivity;
import skku.teamplay.util.SharedPreferencesUtil;

/**
 * Created by woorim on 2018. 6. 4..
 */


//팀원 추가, 데드라인 설정, 규칙 추가, 페널티 등 팀 설정
public class SettingsFragment extends Fragment {

    View rootView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView != null) return rootView;
        rootView = (ViewGroup)inflater.inflate(R.layout.fragment_settings, container, false);
        rootView.findViewById(R.id.tv_modify_team_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ModifyTeamActivity.class));
            }
        });
        rootView.findViewById(R.id.tv_manage_rule).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        CheckBox push_kanban = rootView.findViewById(R.id.push_kanban_checkbox);
        push_kanban.setChecked(SharedPreferencesUtil.getBoolean("use_push_kanban", true));
        CheckBox push_appointment = rootView.findViewById(R.id.push_kanban_appointment);
        push_appointment.setChecked(SharedPreferencesUtil.getBoolean("use_push_kanban", true));
        CheckBox push_penalty = rootView.findViewById(R.id.push_kanban_penalty);
        push_penalty.setChecked(SharedPreferencesUtil.getBoolean("use_push_kanban", true));

        push_kanban.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPreferencesUtil.putBoolean("use_push_kanban", b);
            }
        });
        push_appointment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPreferencesUtil.putBoolean("use_push_appointment", b);
            }
        });
        push_penalty.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
               SharedPreferencesUtil.putBoolean("use_push_penalty", b);
            }
        });
        return rootView;
    }

}

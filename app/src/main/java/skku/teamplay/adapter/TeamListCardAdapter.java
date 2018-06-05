package skku.teamplay.adapter;

import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import skku.teamplay.R;
import skku.teamplay.app.TeamPlayApp;
import skku.teamplay.model.Team;
import skku.teamplay.widget.CircleCountdownView;

public class TeamListCardAdapter extends BaseAdapter{
    ArrayList<Team> teamList;
    public TeamListCardAdapter(ArrayList<Team> teamList) {
        this.teamList = teamList;
    }

    public ArrayList<Team> getTeamList() {
        return teamList;
    }

    public void setTeamList(ArrayList<Team> teamList) {
        this.teamList = teamList;
    }

    @Override
    public int getCount() {
        return teamList.size();
    }

    @Override
    public Team getItem(int i) {
        return teamList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        long[] time = new long[4];
        if(view == null) {
            view = LayoutInflater.from(TeamPlayApp.getAppInstance()).inflate(R.layout.list_team_template_card, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.name.setText(getItem(i).getName());

        time[0] = 1;
        time[1] = 2;
        time[2] = 3;
        time[3] = 4;
        CountDownAdapter countDownAdapter = new CountDownAdapter(holder.circleTimeView, time);
        countDownAdapter.start();
        return view;
    }

    public class ViewHolder {
        CircleCountdownView[] circleTimeView = new CircleCountdownView[4];
        @BindView(R.id.tv_teamitem_teamname) TextView name;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
            circleTimeView[0] = view.findViewById(R.id.template_circle_day);
            circleTimeView[1] = view.findViewById(R.id.template_circle_hour);
            circleTimeView[2] = view.findViewById(R.id.template_circle_min);
            circleTimeView[3] = view.findViewById(R.id.template_circle_secs);

        }
    }

}

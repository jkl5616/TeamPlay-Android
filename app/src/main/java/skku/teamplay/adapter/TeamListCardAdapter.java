package skku.teamplay.adapter;

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
        if(view == null) {
            view = LayoutInflater.from(TeamPlayApp.getAppInstance()).inflate(R.layout.list_team_template_card, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.name.setText(getItem(i).getName());
        return view;
    }

    public class ViewHolder {
        @BindView(R.id.tv_teamitem_teamname) TextView name;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}

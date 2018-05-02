package skku.teamplay.activity.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import skku.teamplay.models.Team;

public class TeamListAdapter extends RecyclerView.Adapter<TeamListAdapter.TeamListHolder>{
    List<Team> teamList;
    public TeamListAdapter(List<Team> teamList) {
        this.teamList = teamList;
    }

    @Override
    public TeamListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(TeamListHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return teamList.size();
    }

    public static class TeamListHolder extends RecyclerView.ViewHolder{
        public TeamListHolder(final View itemView){
            super (itemView);

        }
    }
}

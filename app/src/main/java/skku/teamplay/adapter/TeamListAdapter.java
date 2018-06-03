package skku.teamplay.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import skku.teamplay.R;

public class TeamListAdapter extends RecyclerView.Adapter<TeamListAdapter.TeamListHolder>{
    ArrayList teamList;
    public TeamListAdapter(ArrayList teamList) {
        this.teamList = teamList;
    }

    @Override
    public TeamListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_team_template, parent, false);
        return new TeamListHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TeamListHolder holder, int position) {
        holder.txtTitle.setText("Team Title #" + position);
        holder.txtDescription.setText("Team Description: " + position);
    }

    @Override
    public int getItemCount() {
        return teamList.size();
    }

    public static class TeamListHolder extends RecyclerView.ViewHolder{
        public TextView txtTitle, txtDescription;

        public TeamListHolder(final View itemView){
            super (itemView);
            txtTitle = itemView.findViewById(R.id.team_template_name);
            txtDescription = itemView.findViewById(R.id.team_template_description);

        }
    }
}

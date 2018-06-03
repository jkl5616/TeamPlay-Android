package skku.teamplay.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import skku.teamplay.R;
import skku.teamplay.model.Quest;

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.TimelineHolder>{
    List<Quest> questList;

    public TimelineAdapter(List<Quest> questList) {
        this.questList = questList;
    }

    @Override
    public TimelineHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.template_timeline_item, parent, false);
        return new TimelineHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TimelineHolder holder, int position) {
        holder.description.setText(questList.get(position).getDescription());
        holder.title.setText(questList.get(position).getTitle());
//        holder.date.setText(questList.get(position).getDueAt());
    }

    @Override
    public int getItemCount() {
        return questList.size();
    }

    public static class TimelineHolder extends RecyclerView.ViewHolder{
        public TextView date, title, description;
        public TimelineHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.template_timeline_description_title);
            description = itemView.findViewById(R.id.template_timeline_description);
            date = itemView.findViewById(R.id.template_timeline_time);
        }
    }
}

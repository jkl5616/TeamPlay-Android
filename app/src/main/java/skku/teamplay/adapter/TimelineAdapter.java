package skku.teamplay.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import skku.teamplay.R;
import skku.teamplay.model.KanbanPost;

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.TimelineHolder>{
    List<KanbanPost> kanbanPostList;

    public TimelineAdapter(List<KanbanPost> kanbanPostList) {
        this.kanbanPostList = kanbanPostList;
    }

    @Override
    public TimelineHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.template_timeline_item, parent, false);
        return new TimelineHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TimelineHolder holder, int position) {
        holder.description.setText(kanbanPostList.get(position).getDescription());
        holder.title.setText(kanbanPostList.get(position).getTitle());
//        holder.date.setText(kanbanPostList.get(position).getEndDate());
    }

    @Override
    public int getItemCount() {
        return kanbanPostList.size();
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

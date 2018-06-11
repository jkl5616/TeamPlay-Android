package skku.teamplay.adapter;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import skku.teamplay.R;
import skku.teamplay.model.KanbanPost;
import skku.teamplay.util.AppointKanbanCombined;
import skku.teamplay.util.Util;

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.TimelineHolder>{
    final static private int END_TIMELINE_COLOR_R = 0x30;
    final static private int START_TIMELINE_COLOR_R = 0x79;
    final static private int END_TIMELINE_COLOR_G = 0x2c;
    final static private int START_TIMELINE_COLOR_G = 0xaf;
    final static private int END_TIMELINE_COLOR_B = 0x80;
    final static private int START_TIMELINE_COLOR_B = 0xff;

    private int GAB_COLOR_R, GAB_COLOR_G, GAB_COLOR_B;
    ArrayList<AppointKanbanCombined> combinedList;

    public TimelineAdapter(ArrayList<AppointKanbanCombined> combinedLists) {
        this.combinedList = combinedLists;
        setGab_COLORS();
    }

    public void setCombinedList(ArrayList<AppointKanbanCombined> combinedList) {
        this.combinedList = combinedList;
    }

    private GradientDrawable setGradientTimeLine(int position){

        int start_color = Color.argb(255, (GAB_COLOR_R * position) + START_TIMELINE_COLOR_R, (GAB_COLOR_G * position) + START_TIMELINE_COLOR_G,
                (GAB_COLOR_B * position) + START_TIMELINE_COLOR_B);
        int end_color = Color.argb(255, (GAB_COLOR_R * (position + 1)) + START_TIMELINE_COLOR_R, (GAB_COLOR_G * (position + 1)) + START_TIMELINE_COLOR_G,
                (GAB_COLOR_B * (position + 1)) + START_TIMELINE_COLOR_B);
        GradientDrawable gradientTimeline = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] {start_color, end_color});
        gradientTimeline.setCornerRadius(0f);
        return gradientTimeline;
    }

    private void setGab_COLORS(){
        if (getItemCount() > 0){
            GAB_COLOR_R = (END_TIMELINE_COLOR_R - START_TIMELINE_COLOR_R) / getItemCount();
            GAB_COLOR_G = (END_TIMELINE_COLOR_G - START_TIMELINE_COLOR_G) / getItemCount();
            GAB_COLOR_B = (END_TIMELINE_COLOR_B - START_TIMELINE_COLOR_B) / getItemCount();
        }
    }
    @Override
    public TimelineHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.template_timeline_item, parent, false);
        return new TimelineHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TimelineHolder holder, int position) {

        if (combinedList.get(position).getType() == AppointKanbanCombined.KANBAN_POST){
            String htmlTxt;
            if (combinedList.get(position).getIsFinished() == 1)
                htmlTxt =  "<font color='#03d100'>[완료]</font> " + combinedList.get(position).getDescription();
            else
                htmlTxt = "<font color='#cf001c'>[실패]</font> " + combinedList.get(position).getDescription();
            holder.description.setText(Html.fromHtml(htmlTxt));
        }

        if (combinedList.get(position).getType() == 0){
            String title = "", htmlTxt;
            if (combinedList.get(position).getReward() > 0) {
                htmlTxt = "<font color='#03d100'> +" + combinedList.get(position).getReward() + "</font>";
                title = combinedList.get(position).getTitle()  + htmlTxt;
            }
            else if (combinedList.get(position).getReward() < 0){
                htmlTxt = "<font color='#cf001c'> " + combinedList.get(position).getReward() + "</font>" + " ";
                title = combinedList.get(position).getTitle() + htmlTxt;
            }
            else{
                title = combinedList.get(position).getTitle();
            }
            holder.title.setText(Html.fromHtml(title));
        }
        else  holder.title.setText(combinedList.get(position).getTitle());
        holder.timeLine.setBackground(setGradientTimeLine(position));
        holder.date.setText(Util.DATEFORMAT_yyyyMMdd.format(combinedList.get(position).getEndDate()));
    }

    @Override
    public int getItemCount() {
        return combinedList.size();
    }

    public static class TimelineHolder extends RecyclerView.ViewHolder{
        public TextView date, title, description;
        ImageView timeLine;
        public TimelineHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.template_timeline_description_title);
            description = itemView.findViewById(R.id.template_timeline_description);
            date = itemView.findViewById(R.id.template_timeline_time);
            timeLine = itemView.findViewById(R.id.template_timeline_line);
        }
    }
}

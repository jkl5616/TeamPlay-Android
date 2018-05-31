package skku.teamplay.activity.adapter;

/**
 * Created by ddjdd on 2018-04-18.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import skku.teamplay.R;
import skku.teamplay.model.Quest;

public class KanbanQuestlistAdapter extends BaseAdapter {
    private ArrayList<Quest> QuestList = new ArrayList<>();

    @BindView(R.id.user_profile) ImageView user_profile;
    @BindView(R.id.rewardTypeIc) ImageView rewardTypeIc;
    @BindView(R.id.rewardIc) ImageView rewardIc;
    @BindView(R.id.dueAtIc) ImageView dueAtIc;

    @BindView(R.id.textTitle) TextView textTitle;
    @BindView(R.id.textRewardType) TextView textRewardType;
    @BindView(R.id.textReward) TextView textReward;
    @BindView(R.id.textDueAt) TextView textDueAt;

    public KanbanQuestlistAdapter() { }

    @Override
    public int getCount() { return QuestList.size(); }

    @Override
    public View getView(int position, View rootView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (rootView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rootView = inflater.inflate(R.layout.list_quest_template, parent, false);
        }
        ButterKnife.bind(this, rootView);

        Quest quest = QuestList.get(pos);
        textTitle.setText(quest.getTitle());
        textRewardType.setText(quest.getRewardType_String());
        textReward.setText(Integer.toString(quest.getReward()));
//        textDueAt.setText(quest.getDueAt());

        if(quest.getFinish()) {
            rootView.setBackgroundColor(context.getColor(R.color.questFinished));
        }
        else {
            int mine = quest.getOwnerId();
            if(mine == 0) {
                rootView.setBackgroundColor(context.getColor(R.color.questNew));
            }
            else if(mine == 1) { // my id
                rootView.setBackgroundColor(context.getColor(R.color.questMine));
            }
            else {
                rootView.setBackgroundColor(context.getColor(R.color.questOther));
            }
        }
        notifyDataSetChanged();
        return rootView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return QuestList.get(position);
    }

    public void addItem(Quest item) {
        QuestList.add(item);
        this.notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        QuestList.remove(position);
        this.notifyDataSetChanged();
    }

    public void modifyItem(int position, Quest modifiedQuest) {
        QuestList.set(position, modifiedQuest);
        this.notifyDataSetChanged();
    }
}


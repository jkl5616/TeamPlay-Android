package skku.teamplay.util;

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

import org.w3c.dom.Text;

import java.util.ArrayList;

import skku.teamplay.R;
import skku.teamplay.models.Quest;

public class KanbanQuestlistAdapter extends BaseAdapter {
    private ArrayList<Quest> QuestList = new ArrayList<>();

    // 빈 생성자
    public KanbanQuestlistAdapter() { }

    // 만든 리스트의 개체수
    @Override
    public int getCount() { return QuestList.size(); }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listitem_quest_test, parent, false);
        }

        // xml 링크
        ImageView user_profie = (ImageView)convertView.findViewById(R.id.user_profile);
        ImageView rewardTypeIc = (ImageView)convertView.findViewById(R.id.rewardTypeIc);
        ImageView rewardIc = (ImageView)convertView.findViewById(R.id.rewardIc);
        ImageView dueAt = (ImageView)convertView.findViewById(R.id.dueAtIc);

        TextView textTitle = (TextView) convertView.findViewById(R.id.textTitle);
        TextView textRewardType = (TextView) convertView.findViewById(R.id.textRewardType);
        TextView textReward = (TextView) convertView.findViewById(R.id.textReward);
        TextView textDueAt = (TextView) convertView.findViewById(R.id.textDueAt);

        //
        Quest quest = QuestList.get(pos);
        textTitle.setText(quest.getTitle());
        textRewardType.setText(Integer.toString(quest.getRewardType()));
        textReward.setText(Integer.toString(quest.getReward()));
        textDueAt.setText(quest.getDueAt());

        return convertView;
    }

    //
    @Override
    public long getItemId(int position) {
        return position ;
    }

    //
    @Override
    public Object getItem(int position) {
        return QuestList.get(position) ;
    }


    // 리스트에 새로운 글 추가
    public void addItem(Quest item) {
        QuestList.add(item);
    }

    public void deleteItem(int position) { QuestList.remove(position); }

    public void modifyItem(int position, Quest modifiedQuest) { QuestList.set(position, modifiedQuest); }

    public void makeFinish(int position, Quest finishedQuest) {
        finishedQuest.makeFinish();
        QuestList.set(position, finishedQuest);
    }
}

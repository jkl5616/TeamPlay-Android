package skku.teamplay.util;

/**
 * Created by ddjdd on 2018-04-18.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

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
        TextView titleTextView = (TextView) convertView.findViewById(R.id.postTitle);
        TextView memoTextView = (TextView) convertView.findViewById(R.id.postMemo);
        TextView dateTextView = (TextView) convertView.findViewById(R.id.postDate);
        //   TextView writerTextView= (TextView) convertView.findViewById(R.id.postWriter);

        //
        Quest quest = QuestList.get(pos);
        titleTextView.setText(quest.getTitle());
        memoTextView.setText(quest.getDescription());
        dateTextView.setText(quest.getDueAt());
        //     writerTextView.setText(SecondhandPostItem.getWriterID());

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
}


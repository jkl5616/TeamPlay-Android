package skku.teamplay.adapter;

import android.support.v4.content.ContextCompat;
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
import skku.teamplay.model.Rule;
import skku.teamplay.model.User;

public class RuleCardAdapter extends BaseAdapter {

    private ArrayList<Rule> ruleList;
    public static final String typeString[] = {"전투력", "지갑", "포인트"};

    public RuleCardAdapter(ArrayList<Rule> ruleList) {
        this.ruleList = ruleList;
    }

    @Override
    public int getCount() {
        return ruleList.size();
    }

    @Override
    public Rule getItem(int i) {
        return ruleList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(TeamPlayApp.getAppInstance()).inflate(R.layout.list_rule_card, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.tv_name.setText(getItem(i).getName());
        holder.tv_description.setText(getItem(i).getDescription());
        holder.tv_type.setText(typeString[getItem(i).getType()]);
        if (getItem(i).getScore() > 0) {
            holder.tv_score.setTextColor(ContextCompat.getColor(TeamPlayApp.getAppInstance(), R.color.colorPrimary));
            holder.tv_score.setText("+"+getItem(i).getScore());
        } else {
            holder.tv_score.setTextColor(ContextCompat.getColor(TeamPlayApp.getAppInstance(), R.color.colorAccent));
            holder.tv_score.setText(""+getItem(i).getScore());
        }
        return view;
    }

    public class ViewHolder {
        @BindView(R.id.tv_rule_name)
        TextView tv_name;
        @BindView(R.id.tv_rule_description)
        TextView tv_description;
        @BindView(R.id.tv_rule_type)
        TextView tv_type;
        @BindView(R.id.tv_rule_score)
        TextView tv_score;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

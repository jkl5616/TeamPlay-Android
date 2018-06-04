package skku.teamplay.adapter;

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
import skku.teamplay.model.User;

public class GridMemberAdapter extends BaseAdapter {

    private ArrayList<User> userList;

    public GridMemberAdapter(ArrayList<User> userList) {
        this.userList = userList;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public User getItem(int i) {
        return userList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(view == null) {
            view = LayoutInflater.from(TeamPlayApp.getAppInstance()).inflate(R.layout.griditem_member, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.name.setText(getItem(i).getName() + " ("+getItem(i).getEmail()+")");
        return view;
    }

    public class ViewHolder {
        @BindView(R.id.griditem_user_name)
        TextView name;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

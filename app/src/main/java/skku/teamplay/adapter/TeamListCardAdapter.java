package skku.teamplay.adapter;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import skku.teamplay.R;
import skku.teamplay.app.TeamPlayApp;
import skku.teamplay.model.Team;
import skku.teamplay.model.User;
import skku.teamplay.widget.CircleCountdownView;

public class TeamListCardAdapter extends BaseAdapter{
    ArrayList<Team> teamList;
    ArrayList<User> userList;
    ArrayList<GridView> gridList = new ArrayList<>();
    ArrayList<TextView> titleList = new ArrayList<>();
    GridAdapter adapter;
    public TeamListCardAdapter(ArrayList<Team> teamList) {
        this.teamList = teamList;
    }

    public ArrayList<Team> getTeamList() {
        return teamList;
    }

    public void setTeamList(ArrayList<Team> teamList) {
        this.teamList = teamList;
    }

    public void setGridAdapter(ArrayList<User> userList, int teamIdx){
        this.userList = userList;
        adapter = new GridAdapter(userList);
        gridList.get(teamIdx).setAdapter(adapter);
    }
    @Override
    public int getCount() {
        return teamList.size();
    }

    @Override
    public Team getItem(int i) {
        return teamList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void setContributorTitle(String title, int idx){
        titleList.get(idx).setText(title);
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        long[] time = new long[4];
        if(view == null) {
            view = LayoutInflater.from(TeamPlayApp.getAppInstance()).inflate(R.layout.list_team_template_card, null);
            holder = new ViewHolder(view);
            view.setTag(holder);

            holder.name.setText(getItem(i).getName());
            gridList.add(holder.gridView);

            Calendar today = Calendar.getInstance();
            today.set(Calendar.HOUR_OF_DAY, 0);
            holder.contrTitle.setText("총 참여자: "+getItem(i).getCount()+"명");
            long diff = teamList.get(i).getDeadline().getTime() - today.getTime().getTime();
            time[3] = diff / 1000 % 60; //seconds
            time[2] = diff / (60 * 1000) % 60; //minutes
            time[1] = diff / (60 * 60 * 1000) % 24; //hours
            time[0] = diff / (24 * 60 * 60 * 1000) % 30; //day
            CountDownAdapter countDownAdapter = new CountDownAdapter(holder.circleTimeView, time);
            countDownAdapter.start();

        } else {
            holder = (ViewHolder) view.getTag();
        }


        return view;
    }



    public class ViewHolder {
        CircleCountdownView[] circleTimeView = new CircleCountdownView[4];
        @BindView(R.id.tv_teamitem_teamname) TextView name;
        @BindView(R.id.tv_team_gridview) GridView gridView;
        @BindView(R.id.tv_teamitem_layout) View layout;
        @BindView(R.id.tv_teamitem_text_contributors) TextView contrTitle;
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
            circleTimeView[0] = view.findViewById(R.id.template_circle_day);
            circleTimeView[1] = view.findViewById(R.id.template_circle_hour);
            circleTimeView[2] = view.findViewById(R.id.template_circle_min);
            circleTimeView[3] = view.findViewById(R.id.template_circle_secs);

        }
    }
    class GridAdapter extends BaseAdapter{
        ArrayList<User> userList;

        public GridAdapter(ArrayList<User> userList) {
            this.userList = userList;
        }

        @Override
        public int getCount() {
            return userList.size();
        }

        @Override
        public Object getItem(int i) {
            return userList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return userList.get(i).getId();
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = LayoutInflater.from(TeamPlayApp.getAppInstance()).inflate(R.layout.griditem_member, null );
                TextView textName = view.findViewById(R.id.griditem_user_name);
                textName.setText(userList.get(i).getName());

            }
            return view;
        }



    }

}

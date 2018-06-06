package skku.teamplay.adapter;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    private List<Boolean> isClockStarted;
    public TeamListCardAdapter(ArrayList<Team> teamList) {
        this.teamList = teamList;
    }

    public ArrayList<Team> getTeamList() {
        return teamList;
    }

    public void setTeamList(ArrayList<Team> teamList) {
        this.teamList = teamList;
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

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        long[] time = new long[4];
        if(view == null) {
            view = LayoutInflater.from(TeamPlayApp.getAppInstance()).inflate(R.layout.list_team_template_card, null);
            holder = new ViewHolder(view);
            view.setTag(holder);

            holder.name.setText(getItem(i).getName());


            Calendar today = Calendar.getInstance();
            today.set(Calendar.HOUR_OF_DAY, 0);

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

//        List<User> userList = new ArrayList<>();
//        for (int idx = 0; idx < 10; idx++){
//            userList.add(new User(Integer.toString(idx), 1));
//
//        }
        //setContributors(userList, view, holder.layout_contributor);
        return view;
    }
    public void setContributors(List<User> userList, View parent, View layout_contributor){
        View view;
        for (User user : userList){
            TextView textView;
            CircleImageView imageView;

            LayoutInflater layoutInflater = (LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.template_contributor, null);
            ((LinearLayout)layout_contributor).addView(view);

            textView = view.findViewById(R.id.template_contributor_name);
            imageView = view.findViewById(R.id.template_contributor_image);

            textView.setText(user.getName());
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView textName = view.findViewById(R.id.template_contributor_name);
                    Toast.makeText(view.getContext(), textName.getText() + " clicked", Toast.LENGTH_LONG).show();
                }
            });

            //temporarily
            switch(1){
                case 1:
                    imageView.setImageResource(R.mipmap.basic_profile_pic);
                    break;
            }


        }
    }

    public class ViewHolder {
        CircleCountdownView[] circleTimeView = new CircleCountdownView[4];
        @BindView(R.id.tv_teamitem_teamname) TextView name;
        @BindView(R.id.tv_teamitem_layout_contributor) View layout_contributor;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
            circleTimeView[0] = view.findViewById(R.id.template_circle_day);
            circleTimeView[1] = view.findViewById(R.id.template_circle_hour);
            circleTimeView[2] = view.findViewById(R.id.template_circle_min);
            circleTimeView[3] = view.findViewById(R.id.template_circle_secs);

        }
    }


}

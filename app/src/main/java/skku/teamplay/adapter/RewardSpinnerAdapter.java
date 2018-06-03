package skku.teamplay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import skku.teamplay.R;

/**
 * Created by ddjdd on 2018-05-31.
 */

public class RewardSpinnerAdapter extends BaseAdapter {

    Context context;
    List<String> data;
    LayoutInflater inflater;

    public RewardSpinnerAdapter(Context context, List<String> data){
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if(data!=null) return data.size();
        else return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.spinner_template, parent, false);
        }

        if(data != null){
            String text = data.get(position);
            ((TextView)convertView.findViewById(R.id.spinnerText)).setText(text);
        }

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflater.inflate(R.layout.spinner_dropdown_template, parent, false);
        }

        String text = data.get(position);
        ((TextView)convertView.findViewById(R.id.spinnerDropdownText)).setText(text);

        return convertView;
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
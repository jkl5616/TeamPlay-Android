package skku.teamplay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import skku.teamplay.R;
import skku.teamplay.model.Rule;

/**
 * Created by ddjdd on 2018-06-10.
 */

public class RuleSpinnerAdapter extends BaseAdapter {

    Context context;
    ArrayList<Rule> data;
    LayoutInflater inflater;

    public RuleSpinnerAdapter(Context context, ArrayList<Rule> ruelsList){
        this.context = context;
        this.data = ruelsList;
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
            Rule rule = data.get(position);
            ((TextView)convertView.findViewById(R.id.spinnerText)).setText(rule.getName());
        }

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflater.inflate(R.layout.spinner_dropdown_template, parent, false);
        }

        Rule rule = data.get(position);
        ((TextView)convertView.findViewById(R.id.spinnerDropdownText)).setText(rule.getName());

        return convertView;
    }

    @Override
    public Rule getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
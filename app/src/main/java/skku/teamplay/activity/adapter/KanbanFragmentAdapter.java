package skku.teamplay.activity.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import skku.teamplay.fragment.test.KanbanFragment;

/**
 * Created by ddjdd on 2018-05-11.
 */

public class KanbanFragmentAdapter extends FragmentStatePagerAdapter {
    private final List<KanbanFragment> fragmentList = new ArrayList<>();

    public KanbanFragmentAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public KanbanFragment getItem(int position) {
        return fragmentList.get(position);
    }

    public void addFragment(int position) {
        fragmentList.add(KanbanFragment.create(position));
    }
}

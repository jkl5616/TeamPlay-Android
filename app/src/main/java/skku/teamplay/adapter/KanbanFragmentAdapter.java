package skku.teamplay.adapter;

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

    public void addFragment(int position, int id, String title) {
        fragmentList.add(KanbanFragment.create(position, id, title));
    }
}

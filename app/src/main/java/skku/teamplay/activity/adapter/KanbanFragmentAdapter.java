package skku.teamplay.activity.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import skku.teamplay.fragment.test.KanbanFragment;

/**
 * Created by ddjdd on 2018-05-11.
 */

public class KanbanFragmentAdapter extends FragmentStatePagerAdapter {
    Context context;

    public KanbanFragmentAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Fragment getItem(int position) {

        return KanbanFragment.create(position);
    }
}

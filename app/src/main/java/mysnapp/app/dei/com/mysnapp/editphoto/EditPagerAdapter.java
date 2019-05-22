package mysnapp.app.dei.com.mysnapp.editphoto;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

public class EditPagerAdapter extends FragmentStatePagerAdapter {

    private final List<String> titles;
    private List<Fragment> list;

    EditPagerAdapter(List<Fragment> list, List<String> titles, FragmentManager fm) {
        super(fm);
        this.list = list;
        this.titles = titles;
    }

    EditPagerAdapter(List<String> titles, FragmentManager fm) {
        super(fm);
        this.list = list;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int pos) {
//        return list.get(pos);
        return new PageFragment().newInstance(null);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}

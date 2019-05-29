package mysnapp.app.dei.com.mysnapp.photodetail;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mysnapp.app.dei.com.mysnapp.data.local.entity.Image;

public class SlidePagerAdapter extends FragmentStatePagerAdapter {

    private List<Image> images;
    public Map<Integer, Fragment> mPageReferenceMap = new HashMap<>();
    public SlidePagerAdapter(List<Image> images, FragmentManager fm) {
        super(fm);
        this.images = images;
    }

    @Override
    public Fragment getItem(int i) {
        SlidePageFragment frag = SlidePageFragment.newInstance(images.get(i));
        mPageReferenceMap.put(i, frag);
        return frag;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    void addAll(List<Image> list) {
        images.clear();
        images.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
        mPageReferenceMap.remove(position);
    }
}

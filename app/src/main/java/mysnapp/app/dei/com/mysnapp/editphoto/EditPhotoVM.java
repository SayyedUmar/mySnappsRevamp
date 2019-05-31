package mysnapp.app.dei.com.mysnapp.editphoto;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import mysnapp.app.dei.com.mysnapp.data.local.entity.Border;
import mysnapp.app.dei.com.mysnapp.data.local.entity.Graphic;
import mysnapp.app.dei.com.mysnapp.data.local.entity.Image;

public class EditPhotoVM extends ViewModel {

    private Image image;
    private EditPagerAdapter pagerAdapter;
    private EditPhotoRepo repo;
    private List<String> titles = new ArrayList<>();
    private Map<Integer, Fragment> fragmentMap = new TreeMap();
    private List<Fragment> fragments = new ArrayList<>();

    public void init(Image image, FragmentActivity activity) {
        this.image = image;
        repo = new EditPhotoRepo();
        titles.add("Border");
        titles.add("Sticker");
        titles.add("Contrast");
        titles.add("Text");

        BorderFragment frag = new BorderFragment();
        frag.liveData = repo.getLiveBorders();
//        fragmentMap.put(1, frag);
//        fragmentMap.put(2, PageFragment.newInstance(titles.get(0), image, repo.getLiveBorders()));
//        fragmentMap.put(3, PageFragment.newInstance(titles.get(1), image, repo.getLiveGraphics()));
//        fragmentMap.put(4, new PageFragment());
        fragments.add(frag);
        fragments.add(PageFragment.newInstance(titles.get(0), image, repo.getLiveBorders()));
        fragments.add(PageFragment.newInstance(titles.get(1), image, repo.getLiveGraphics()));
        fragments.add(new PageFragment());

        pagerAdapter = new EditPagerAdapter(fragments, titles, activity.getSupportFragmentManager());
    }

    public Image getModel() {
        return image;
    }

    public EditPagerAdapter getPagerAdapter() {
        return pagerAdapter;
    }


    public LiveData<List<Border>> getLiveBorders() {
        return repo.getLiveBorders();
    }

    public LiveData<List<Graphic>> getLiveGraphics() {
        return repo.getLiveGraphics();
    }

    public void fetchAllBorders() {
        repo.fetchAllBorders();
    }

    public void fetchAllGraphics() {
        repo.fetchAllGraphics();
    }

    public void updateBorders() {

    }

    public void updateGraphics() {

    }
}

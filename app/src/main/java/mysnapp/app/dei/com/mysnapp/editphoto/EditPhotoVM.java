package mysnapp.app.dei.com.mysnapp.editphoto;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import mysnapp.app.dei.com.mysnapp.data.local.entity.Border;
import mysnapp.app.dei.com.mysnapp.data.local.entity.Graphic;
import mysnapp.app.dei.com.mysnapp.data.local.entity.Image;
import mysnapp.app.dei.com.mysnapp.utils.SingleLiveData;

public class EditPhotoVM extends ViewModel {

    private Image image;
    private EditPagerAdapter pagerAdapter;
    private EditPhotoRepo repo;
    private List<String> titles = new ArrayList<>();
    private Map<Integer, Fragment> fragmentMap = new TreeMap();
    private List<Fragment> fragments = new ArrayList<>();
    private SingleLiveData<Border> borderLiveData;
    private SingleLiveData<Graphic> graphicsLiveData;

    public SingleLiveData<Border> getBorderLiveData() {
        return borderLiveData;
    }

    public SingleLiveData<Graphic> getGraphicsLiveData() {
        return graphicsLiveData;
    }

    public void init(Image image, FragmentActivity activity) {
        repo = new EditPhotoRepo();
        this.image = image;
        titles.add("Border");
        titles.add("Sticker");
        titles.add("Contrast");
        titles.add("Text");

        BorderFragment borderFragment = new BorderFragment();
        borderFragment.liveData = repo.getLiveBorders();
        GraphicsFragment graphicsFragment = new GraphicsFragment();
        graphicsFragment.liveData = repo.getLiveGraphics();
//        fragmentMap.put(1, frag);
//        fragmentMap.put(2, PageFragment.newInstance(titles.get(0), image, repo.getLiveBorders()));
//        fragmentMap.put(3, PageFragment.newInstance(titles.get(1), image, repo.getLiveGraphics()));
//        fragmentMap.put(4, new PageFragment());
        fragments.add(borderFragment);
        fragments.add(graphicsFragment);
        fragments.add(PageFragment.newInstance(titles.get(0), image, repo.getLiveBorders()));
        fragments.add(new PageFragment());

        pagerAdapter = new EditPagerAdapter(fragments, titles, activity.getSupportFragmentManager());

        borderLiveData = borderFragment.singleLiveData;
        graphicsLiveData = graphicsFragment.singleLiveData;
//        borderFragment.singleLiveData.observe(activity, o ->  borderLiveData.postValue(o));
//        graphicsFragment.singleLiveData.observe(activity, o ->  graphicsLiveData.postValue(o));
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

    public void uploadImageBitmap(Bitmap bitmap) {
        repo.uploadImageBitmap(bitmap);
    }
}

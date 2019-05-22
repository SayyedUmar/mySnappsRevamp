package mysnapp.app.dei.com.mysnapp.editphoto;

import android.arch.lifecycle.ViewModel;
import android.support.v4.app.FragmentActivity;

import java.util.List;

import mysnapp.app.dei.com.mysnapp.data.local.entity.Image;

public class EditPhotoVM extends ViewModel {

    private Image image;
    private EditPagerAdapter pagerAdapter;

    public Image getModel() {
        return image;
    }

    public EditPagerAdapter getPagerAdapter() {
        return pagerAdapter;
    }

    public void init (Image image, List list, FragmentActivity ctx) {
        this.image = image;
        pagerAdapter = new EditPagerAdapter(list, ctx.getSupportFragmentManager());
    }
}

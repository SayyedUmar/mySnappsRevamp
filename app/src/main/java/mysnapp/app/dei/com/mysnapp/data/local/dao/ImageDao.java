package mysnapp.app.dei.com.mysnapp.data.local.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import mysnapp.app.dei.com.mysnapp.data.local.entity.Image;

@Dao
public interface ImageDao {
    @Query("SELECT * FROM images_table")
    LiveData<Image> getImages();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Image user);

    @Query("DELETE FROM images_table")
    void deleteAll();
}

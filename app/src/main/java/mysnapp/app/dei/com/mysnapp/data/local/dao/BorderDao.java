package mysnapp.app.dei.com.mysnapp.data.local.dao;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import mysnapp.app.dei.com.mysnapp.data.local.entity.Border;

@Dao
public interface BorderDao {

    @Query("SELECT * FROM border")
    public LiveData<List<Border>> getAllBorders();

    @Query("SELECT * FROM border")
    public List<Border> getAllSimpleBorders();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Border border);

    @Query("DELETE FROM border")
    void deleteAll();
}

package mysnapp.app.dei.com.mysnapp.data.local.dao;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import mysnapp.app.dei.com.mysnapp.data.local.entity.Graphic;

@Dao
public interface GraphicsDao {

    @Query("SELECT * FROM graphic")
    public LiveData<List<Graphic>> getAllGraphics();

    @Query("SELECT * FROM graphic")
    public List<Graphic> getAllSimpleGraphics();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Graphic border);

    @Query("DELETE FROM graphic")
    void deleteAll();
}

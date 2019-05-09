package mysnapp.app.dei.com.mysnapp.data.local.dao;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import mysnapp.app.dei.com.mysnapp.data.local.entity.Substore;

@Dao
public interface SubstoreDao {

    @Query("SELECT * FROM substores")
    LiveData<Substore> getSubstores();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Substore store);

    @Query("DELETE FROM substores")
    void deleteAll();
}

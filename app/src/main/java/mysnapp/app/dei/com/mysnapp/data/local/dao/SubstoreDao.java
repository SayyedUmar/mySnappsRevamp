package mysnapp.app.dei.com.mysnapp.data.local.dao;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import mysnapp.app.dei.com.mysnapp.data.local.entity.SubstoreEntity;

@Dao
public interface SubstoreDao {

    @Query("SELECT * FROM SubstoreEntity")
    LiveData<SubstoreEntity> getSubstores();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(SubstoreEntity store);

}

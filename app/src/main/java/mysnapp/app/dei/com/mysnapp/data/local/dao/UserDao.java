package mysnapp.app.dei.com.mysnapp.data.local.dao;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import mysnapp.app.dei.com.mysnapp.data.local.entity.User;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user LIMIT 1")
    LiveData<User> getUser();

    @Query("SELECT * FROM user LIMIT 1")
    User getSimpleUser();

    @Query("SELECT * FROM user")
    LiveData<List<User>> getAllUser();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Delete
    void delete(User user);

    @Query("DELETE FROM user")
    void deleteAll();
}

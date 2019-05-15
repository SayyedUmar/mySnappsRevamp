package mysnapp.app.dei.com.mysnapp.data.local;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

import io.reactivex.annotations.NonNull;
import mysnapp.app.dei.com.mysnapp.data.local.dao.ImageDao;
import mysnapp.app.dei.com.mysnapp.data.local.dao.SubstoreDao;
import mysnapp.app.dei.com.mysnapp.data.local.dao.UserDao;
import mysnapp.app.dei.com.mysnapp.data.local.entity.Image;
import mysnapp.app.dei.com.mysnapp.data.local.entity.Substore;
import mysnapp.app.dei.com.mysnapp.data.local.entity.User;
import mysnapp.app.dei.com.mysnapp.utils.Const;
import mysnapp.app.dei.com.mysnapp.utils.DateConverter;

@TypeConverters({DateConverter.class})
@Database(entities = {User.class, Substore.class, Image.class}, version = 2, exportSchema = true)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract UserDao userDao();
    public abstract SubstoreDao substoreDao();
    public abstract ImageDao imageDao();

    private static final Object sLock = new Object();

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (sLock) {
                if (INSTANCE == null)
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, Const.DATABASE)
                            // allow queries on the main thread.
                            // Don't do this on a real app! See PersistenceBasicSample for an example.
                            //.allowMainThreadQueries()
                            .fallbackToDestructiveMigration() //want your database to be cleared
                            //.addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                            .build();
            }
        }
        return INSTANCE;
    }

    public static AppDatabase getInMemoreyDatabase(Context context){
        if (INSTANCE == null) {
            synchronized (sLock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.inMemoryDatabaseBuilder(context.getApplicationContext(),AppDatabase.class).build();
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }



    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // Since we didn't alter the table, there's nothing else to do here.
            //database.execSQL("ALTER TABLE users " + " ADD COLUMN last_update INTEGER");
        }
    };

}

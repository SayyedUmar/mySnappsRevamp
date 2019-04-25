package mysnapp.app.dei.com.mysnapp.data.local.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class UserEntity {
    @PrimaryKey(autoGenerate = true)
    private long id;

    String FirstName;
    String LastName;
    String EmailId;
    String UserName;
    String PartnerUserID;
    String Nationality;
    String Gender;
}

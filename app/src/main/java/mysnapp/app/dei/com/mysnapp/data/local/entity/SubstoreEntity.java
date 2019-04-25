package mysnapp.app.dei.com.mysnapp.data.local.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;
import java.util.List;

@Entity(tableName = "substores")
public class SubstoreEntity {

    @PrimaryKey(autoGenerate = true)
    private long id;

    String SubStoreId;
    String SubStoreDisplayName;
    String LogicalSiteName;
    String LogicalSiteID;
    String IdentificationCode;

    Date ClaimDate;
    Date ExpiryDate;

    String SubStoreHeaderImage;
    String SubStoreBanner;
    String SubStoreThumb;

    public List<ImageEntity> ImageDetailList;
}

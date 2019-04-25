package mysnapp.app.dei.com.mysnapp.data.local.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

/*
indices = {@Index(value = {"ImageId"}, unique = true)}
        foreignKeys = @ForeignKey(entity = SubstoreEntity.class,
                parentColumns = "id",
                childColumns = "subID",
                onDelete = CASCADE)
*/

@Entity(tableName = "images")
public class ImageEntity {

    @PrimaryKey(autoGenerate = true)
    private long id;
    long subID;

    String ImageId;
    String ImageLocationID;
    String ImageName;
    String ImageUrl;
    String ImageThumbnailUrl;
    String WatermarkImagePath;
    String VideoWatermarkImagePath;
    int MediaType;
    int IsPaidImage;
    int IsPanoramic;
    int IsStockShot;

    Date CaptureDate;
    Date ExpiryDate;
}

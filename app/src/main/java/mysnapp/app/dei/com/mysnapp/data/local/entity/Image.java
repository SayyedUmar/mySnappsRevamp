package mysnapp.app.dei.com.mysnapp.data.local.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

/*
indices = {@Index(value = {"ImageId"}, unique = true)}
        foreignKeys = @ForeignKey(entity = Substore.class,
                parentColumns = "id",
                childColumns = "substoreID",
                onDelete = CASCADE)
*/

@Entity(tableName = "images_table")
public class Image {

    @PrimaryKey(autoGenerate = true)
    private long id;

    String substoreID;

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSubstoreID() {
        return substoreID;
    }

    public void setSubstoreID(String substoreID) {
        this.substoreID = substoreID;
    }

    public String getImageId() {
        return ImageId;
    }

    public void setImageId(String imageId) {
        ImageId = imageId;
    }

    public String getImageLocationID() {
        return ImageLocationID;
    }

    public void setImageLocationID(String imageLocationID) {
        ImageLocationID = imageLocationID;
    }

    public String getImageName() {
        return ImageName;
    }

    public void setImageName(String imageName) {
        ImageName = imageName;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getImageThumbnailUrl() {
        return ImageThumbnailUrl;
    }

    public void setImageThumbnailUrl(String imageThumbnailUrl) {
        ImageThumbnailUrl = imageThumbnailUrl;
    }

    public String getWatermarkImagePath() {
        return WatermarkImagePath;
    }

    public void setWatermarkImagePath(String watermarkImagePath) {
        WatermarkImagePath = watermarkImagePath;
    }

    public String getVideoWatermarkImagePath() {
        return VideoWatermarkImagePath;
    }

    public void setVideoWatermarkImagePath(String videoWatermarkImagePath) {
        VideoWatermarkImagePath = videoWatermarkImagePath;
    }

    public int getMediaType() {
        return MediaType;
    }

    public void setMediaType(int mediaType) {
        MediaType = mediaType;
    }

    public int getIsPaidImage() {
        return IsPaidImage;
    }

    public void setIsPaidImage(int isPaidImage) {
        IsPaidImage = isPaidImage;
    }

    public int getIsPanoramic() {
        return IsPanoramic;
    }

    public void setIsPanoramic(int isPanoramic) {
        IsPanoramic = isPanoramic;
    }

    public int getIsStockShot() {
        return IsStockShot;
    }

    public void setIsStockShot(int isStockShot) {
        IsStockShot = isStockShot;
    }

    public Date getCaptureDate() {
        return CaptureDate;
    }

    public void setCaptureDate(Date captureDate) {
        CaptureDate = captureDate;
    }

}

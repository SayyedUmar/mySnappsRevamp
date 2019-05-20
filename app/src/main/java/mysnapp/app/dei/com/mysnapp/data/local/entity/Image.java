package mysnapp.app.dei.com.mysnapp.data.local.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/*
indices = {@Index(value = {"ImageId"}, unique = true)}
        foreignKeys = @ForeignKey(entity = Substore.class,
                parentColumns = "id",
                childColumns = "substoreID",
                onDelete = CASCADE)
*/

@Entity(tableName = "images_table")
public class Image implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.substoreID);
        dest.writeString(this.ImageId);
        dest.writeString(this.ImageLocationID);
        dest.writeString(this.ImageName);
        dest.writeString(this.ImageUrl);
        dest.writeString(this.ImageThumbnailUrl);
        dest.writeString(this.WatermarkImagePath);
        dest.writeString(this.VideoWatermarkImagePath);
        dest.writeInt(this.MediaType);
        dest.writeInt(this.IsPaidImage);
        dest.writeInt(this.IsPanoramic);
        dest.writeInt(this.IsStockShot);
        dest.writeLong(this.CaptureDate != null ? this.CaptureDate.getTime() : -1);
    }

    public Image() {
    }

    protected Image(Parcel in) {
        this.id = in.readLong();
        this.substoreID = in.readString();
        this.ImageId = in.readString();
        this.ImageLocationID = in.readString();
        this.ImageName = in.readString();
        this.ImageUrl = in.readString();
        this.ImageThumbnailUrl = in.readString();
        this.WatermarkImagePath = in.readString();
        this.VideoWatermarkImagePath = in.readString();
        this.MediaType = in.readInt();
        this.IsPaidImage = in.readInt();
        this.IsPanoramic = in.readInt();
        this.IsStockShot = in.readInt();
        long tmpCaptureDate = in.readLong();
        this.CaptureDate = tmpCaptureDate == -1 ? null : new Date(tmpCaptureDate);
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel source) {
            return new Image(source);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };
}

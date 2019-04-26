package mysnapp.app.dei.com.mysnapp.data.local.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;
import java.util.List;

@Entity(tableName = "substores")
public class Substore {

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

    @Ignore
    public List<Image> ImageDetailList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSubStoreId() {
        return SubStoreId;
    }

    public void setSubStoreId(String subStoreId) {
        SubStoreId = subStoreId;
    }

    public String getSubStoreDisplayName() {
        return SubStoreDisplayName;
    }

    public void setSubStoreDisplayName(String subStoreDisplayName) {
        SubStoreDisplayName = subStoreDisplayName;
    }

    public String getLogicalSiteName() {
        return LogicalSiteName;
    }

    public void setLogicalSiteName(String logicalSiteName) {
        LogicalSiteName = logicalSiteName;
    }

    public String getLogicalSiteID() {
        return LogicalSiteID;
    }

    public void setLogicalSiteID(String logicalSiteID) {
        LogicalSiteID = logicalSiteID;
    }

    public String getIdentificationCode() {
        return IdentificationCode;
    }

    public void setIdentificationCode(String identificationCode) {
        IdentificationCode = identificationCode;
    }

    public Date getClaimDate() {
        return ClaimDate;
    }

    public void setClaimDate(Date claimDate) {
        ClaimDate = claimDate;
    }

    public Date getExpiryDate() {
        return ExpiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        ExpiryDate = expiryDate;
    }

    public String getSubStoreHeaderImage() {
        return SubStoreHeaderImage;
    }

    public void setSubStoreHeaderImage(String subStoreHeaderImage) {
        SubStoreHeaderImage = subStoreHeaderImage;
    }

    public String getSubStoreBanner() {
        return SubStoreBanner;
    }

    public void setSubStoreBanner(String subStoreBanner) {
        SubStoreBanner = subStoreBanner;
    }

    public String getSubStoreThumb() {
        return SubStoreThumb;
    }

    public void setSubStoreThumb(String subStoreThumb) {
        SubStoreThumb = subStoreThumb;
    }

    public List<Image> getImageDetailList() {
        return ImageDetailList;
    }

    public void setImageDetailList(List<Image> imageDetailList) {
        ImageDetailList = imageDetailList;
    }
}

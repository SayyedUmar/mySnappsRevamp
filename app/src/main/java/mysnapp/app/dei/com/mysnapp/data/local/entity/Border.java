package mysnapp.app.dei.com.mysnapp.data.local.entity;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Border {

//    @PrimaryKey(autoGenerate = true)
//    private long id;

    @PrimaryKey(autoGenerate = true)
    private long BorderID;

    private String SiteID;
    private String VenueID;
    private String BorderName;
    private String BorderFilePath;
    private String BorderThumbnailFilePath;
    private String LocationIDs;


    public long getBorderID() {
        return BorderID;
    }

    public void setBorderID(long borderID) {
        BorderID = borderID;
    }

    public String getSiteID() {
        return SiteID;
    }

    public void setSiteID(String siteID) {
        SiteID = siteID;
    }

    public String getVenueID() {
        return VenueID;
    }

    public void setVenueID(String venueID) {
        VenueID = venueID;
    }

    public String getBorderName() {
        return BorderName;
    }

    public void setBorderName(String borderName) {
        BorderName = borderName;
    }

    public String getBorderFilePath() {
        return BorderFilePath;
    }

    public void setBorderFilePath(String borderFilePath) {
        BorderFilePath = borderFilePath;
    }

    public String getBorderThumbnailFilePath() {
        return BorderThumbnailFilePath;
    }

    public void setBorderThumbnailFilePath(String borderThumbnailFilePath) {
        BorderThumbnailFilePath = borderThumbnailFilePath;
    }

    public String getLocationIDs() {
        return LocationIDs;
    }

    public void setLocationIDs(String locationIDs) {
        LocationIDs = locationIDs;
    }
}

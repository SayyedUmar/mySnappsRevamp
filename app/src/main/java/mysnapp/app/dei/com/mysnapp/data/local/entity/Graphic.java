package mysnapp.app.dei.com.mysnapp.data.local.entity;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Graphic {


//    @PrimaryKey(autoGenerate = true)
//    private long id;

    @PrimaryKey(autoGenerate = true)
    private long GraphicID;

    private String SiteID;
    private String VenueID;
    private String GraphicName;
    private String GraphicFilePath;
    private String LocationIDs;

    public long getGraphicID() {
        return GraphicID;
    }

    public void setGraphicID(long graphicID) {
        GraphicID = graphicID;
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

    public String getGraphicName() {
        return GraphicName;
    }

    public void setGraphicName(String graphicName) {
        GraphicName = graphicName;
    }

    public String getGraphicFilePath() {
        return GraphicFilePath;
    }

    public void setGraphicFilePath(String graphicFilePath) {
        GraphicFilePath = graphicFilePath;
    }

    public String getLocationIDs() {
        return LocationIDs;
    }

    public void setLocationIDs(String locationIDs) {
        LocationIDs = locationIDs;
    }
}

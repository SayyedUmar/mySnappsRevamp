package mysnapp.app.dei.com.mysnapp.model;

import java.util.List;

import mysnapp.app.dei.com.mysnapp.data.local.entity.Substore;
import mysnapp.app.dei.com.mysnapp.data.local.entity.User;


public class Data {

    public User User;
    public List<Substore> SubStoreDetails;
}



/*

class User {
    String FirstName;
    String LastName;
    String EmailId;
    String UserName;
    String PartnerUserID;
    String Nationality;
    String Gender;
}
class SubStore {

    String SubStoreId;
    String SubStoreDisplayName;
    String LogicalSiteName;
    String LogicalSiteID;
    String IdentificationCode;
    String ClaimDate;
    String ExpiryDate;

    String SubStoreHeaderImage;
    String SubStoreBanner;
    String SubStoreThumb;

    List<ImageModel> ImageDetailList;
}


class ImageModel {

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
    String CaptureDate;
    String ExpiryDate;
}*/

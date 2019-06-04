package mysnapp.app.dei.com.mysnapp.utils;

public class Const {

    public static final String USER_MODEL = "USER_MODEL";
    private static final Server SERVER = Server.ATLANTIS_BETA;

    public static final String REMEMBER_ME = "REMEMBER_ME";
    public static final String USERNAME = "USERNAME";
    public static final String PASSWORD = "PASSWORD";
    public static final String DATABASE = "ATPTESTDB";

    public static String ServicePassword;
    public static String ServiceUserId;
    public static String GUID;


    public static String ROOT_URL = "";
    public static String ROOT_URL_FILE_UPLOAD = "";

    static {
        //setting server Urls
        switch (SERVER) {
            case ATLANTIS_LIVE:
                GUID = "130894670710409803";
                ServicePassword = "ATP";
                ServiceUserId = "ATP";
                ROOT_URL = "http://mysnapps.mydeievents.com:90/AndroidService.svc/";
                ROOT_URL_FILE_UPLOAD = "http://mysnapps.mydeievents.com:90/FileService.svc/";
                break;

            case ATLANTIS_BETA:
                GUID = "130894670710409803";
                ServicePassword = "ATP";
                ServiceUserId = "ATP";
                ROOT_URL = "http://mobileservicebeta.mydeievents.com/AndroidService.svc/";
                ROOT_URL_FILE_UPLOAD = "http://mobileservicebeta.mydeievents.com/FileService.svc/";
                break;
        }
    }


    enum Server {
        ATLANTIS_BETA,
        ATLANTIS_LIVE,
    }
}

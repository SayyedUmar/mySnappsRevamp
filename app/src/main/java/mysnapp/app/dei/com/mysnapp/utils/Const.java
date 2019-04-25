package mysnapp.app.dei.com.mysnapp.utils;

public class Const {

    public static final Server SERVER = Server.ATLANTIS_BETA;

    public static final String REMEMBER_ME = "REMEMBER_ME";
    public static final String USERNAME = "USERNAME";
    public static final String PASSWORD = "PASSWORD";

    public static String ServicePassword;
    public static String ServiceUserId;
    public static String GUID;


    public static String ROOT_URL = "";

    static {
        //setting server Urls
        switch (SERVER) {
            case ATLANTIS_LIVE:
                GUID = "130894670710409803";
                ServicePassword = "ATP";
                ServiceUserId = "ATP";
                ROOT_URL = "http://mysnapps.mydeievents.com:90/AndroidService.svc/";
                break;

            case ATLANTIS_BETA:
                GUID = "130894670710409803";
                ServicePassword = "ATP";
                ServiceUserId = "ATP";
                ROOT_URL = "http://mobileservicebeta.mydeievents.com/AndroidService.svc/";
                break;
        }
    }


    enum Server {
        ATLANTIS_BETA,
        ATLANTIS_LIVE,
    }
}

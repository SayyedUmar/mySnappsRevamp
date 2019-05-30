package mysnapp.app.dei.com.mysnapp.data.remote;

import mysnapp.app.dei.com.mysnapp.utils.Const;

public class RequestModel<T> {

    private T Data;
    private String ServicePassword = Const.ServicePassword;
    private String ServiceUserId = Const.ServiceUserId;
    private String GUID = Const.GUID;
    public String PartnerUserId;

    public RequestModel(T data) {
        this.Data = data;
    }
}

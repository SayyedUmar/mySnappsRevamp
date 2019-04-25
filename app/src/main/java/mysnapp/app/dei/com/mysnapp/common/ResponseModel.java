package mysnapp.app.dei.com.mysnapp.common;


import mysnapp.app.dei.com.mysnapp.model.Data;

/**
 * The model class which holds the top popular articles data
 * Author: Lajesh D
 * Email: lajeshds2007@gmail.com
 * Created: 7/24/2018
 * Modified: 7/24/2018
 */
public class ResponseModel<T> {

    public String ResponseCode;
    public String ResponseMessage;
    public T Data;
}


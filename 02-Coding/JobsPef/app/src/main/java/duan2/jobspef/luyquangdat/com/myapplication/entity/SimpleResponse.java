package duan2.jobspef.luyquangdat.com.myapplication.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by quangnv on 7/25/17.
 */

public class SimpleResponse implements Serializable {


    @SerializedName("message")
    private String message;
    @SerializedName("status")
    private int status;

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }
}

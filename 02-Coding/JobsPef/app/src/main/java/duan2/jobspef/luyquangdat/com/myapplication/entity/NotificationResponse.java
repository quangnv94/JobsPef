package duan2.jobspef.luyquangdat.com.myapplication.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Dr.Cuong on 3/8/2017.
 */

public class NotificationResponse implements Serializable {
    @SerializedName("id")
    private int id;
    @SerializedName("message")
    private String message;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("updated_at")
    private String updated_at;
    @SerializedName("created_by")
    private String created_by;
    @SerializedName("updated_by")
    private String updated_by;
    @SerializedName("title")
    private String title;
    @SerializedName("content")
    private String content;
    @SerializedName("post_id")
    private String post_id;

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public String getCreated_by() {
        return created_by;
    }

    public String getUpdated_by() {
        return updated_by;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getPost_id() {
        return post_id;
    }
}

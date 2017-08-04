package duan2.jobspef.luyquangdat.com.myapplication.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PostResponse implements Serializable {

    @SerializedName("id")
    private String id;
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("category_id")
    private String category_id;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("created_by")
    private String created_by;
    @SerializedName("profile_id")
    private String profile_id;
    @SerializedName("images")
    private String images;
    @SerializedName("place")
    private String place;
    @SerializedName("requirement")
    private String requirement;
    @SerializedName("benefits")
    private String benefits;
    @SerializedName("time_limited")
    private String time_limited;
    @SerializedName("contact")
    private String contact;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory_id() {
        return category_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getCreated_by() {
        return created_by;
    }

    public String getProfile_id() {
        return profile_id;
    }

    public String getImages() {
        return images;
    }

    public String getPlace() {
        return place;
    }

    public String getRequirement() {
        return requirement;
    }

    public String getBenefits() {
        return benefits;
    }

    public String getTime_limited() {
        return time_limited;
    }

    public String getContact() {
        return contact;
    }
}

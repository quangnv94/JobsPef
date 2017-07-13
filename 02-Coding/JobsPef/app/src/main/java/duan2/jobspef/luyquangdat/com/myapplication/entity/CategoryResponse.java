package duan2.jobspef.luyquangdat.com.myapplication.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CategoryResponse implements Serializable {
    @SerializedName("id")
    private int id;
    @SerializedName("category_name")
    private String category_name;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("updated_at")
    private String updated_at;
    @SerializedName("created_by")
    private String created_by;
    @SerializedName("updated_by")
    private String updated_by;
    @SerializedName("icon_id")
    private String icon_id;

    public int getId() {
        return id;
    }

    public String getCategory_name() {
        return category_name;
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

    public String getIcon_id() {
        return icon_id;
    }
}

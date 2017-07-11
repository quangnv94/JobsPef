package duan2.jobspef.luyquangdat.com.myapplication.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nguye on 7/11/2017.
 */

public class CategoryItem {
    @SerializedName("id")
    private String id;

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

    public CategoryItem(String category_name, String created_at, String icon_id) {
        this.category_name = category_name;
        this.created_at = created_at;
        this.icon_id = icon_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getUpdated_by() {
        return updated_by;
    }

    public void setUpdated_by(String updated_by) {
        this.updated_by = updated_by;
    }

    public String getIcon_id() {
        return icon_id;
    }

    @Override
    public String toString() {
        return "CategoryItem{" +id+" "+
                "category_name='" + category_name + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", created_by='" + created_by + '\'' +
                ", updated_by='" + updated_by + '\'' +
                ", icon_id='" + icon_id + '\'' +
                '}';
    }

    public void setIcon_id(String icon_id) {
        this.icon_id = icon_id;
    }
}

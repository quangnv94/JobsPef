package duan2.jobspef.luyquangdat.com.myapplication.entity;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CategoryResponse implements Serializable {

    @SerializedName("id")
    private String id;
    @SerializedName("id_category")
    private String id_category;
    @SerializedName("category_name")
    private String category_name;
    @SerializedName("create_at")
    private String create_at;
    @SerializedName("updated_at")
    private String updated_at;
    @SerializedName("create_by")
    private String create_by;
    @SerializedName("update_by")
    private String update_by;
    @SerializedName("icon_id")
    private String icon_id;

    public CategoryResponse(String id_category, String category_name, Bitmap bitmap) {
        this.id_category = id_category;
        this.category_name = category_name;
        this.bitmap = bitmap;
    }

    public CategoryResponse(String id_category, String category_name) {
        this.id_category = id_category;
        this.category_name = category_name;
    }

    private Bitmap bitmap;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public String getId() {
        return id;
    }

    public String getId_category() {
        return id_category;
    }

    public String getCategory_name() {
        return category_name;
    }

    public String getCreate_at() {
        return create_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public String getCreate_by() {
        return create_by;
    }

    public String getUpdate_by() {
        return update_by;
    }

    public String getIcon_id() {
        return icon_id;
    }
}

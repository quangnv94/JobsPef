package duan2.jobspef.luyquangdat.com.myapplication.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nguye on 7/11/2017.
 */

public class ResponseValue {
    @SerializedName("entries")
    public List<CategoryItem> entries;
}

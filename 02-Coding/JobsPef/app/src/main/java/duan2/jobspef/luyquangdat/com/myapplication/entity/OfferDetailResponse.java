package duan2.jobspef.luyquangdat.com.myapplication.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OfferDetailResponse {
    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("short_description")
    private String short_description;
    @SerializedName("best_status")
    private String best_status;
    @SerializedName("city_id")
    private String city_id;
    @SerializedName("banner_image_id")
    private String banner_image_id;
    @SerializedName("category_id")
    private String category_id;
    @SerializedName("banner_url")
    private String banner_url;
    @SerializedName("description")
    private String description;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("updated_at")
    private String updated_at;
    @SerializedName("created_by")
    private String created_by;
    @SerializedName("updated_by")
    private String updated_by;
    @SerializedName("media")
    private Media media;
    @SerializedName("type")
    private String type;
    @SerializedName("preview_image_id")
    private String preview_image_id;
    @SerializedName("slider_status")
    private String slider_status;
    @SerializedName("video_id")
    private String video_id;
    @SerializedName("category")
    private Category category;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getShort_description() {
        return short_description;
    }

    public String getBest_status() {
        return best_status;
    }

    public String getCity_id() {
        return city_id;
    }

    public String getBanner_image_id() {
        return banner_image_id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public String getBanner_url() {
        return banner_url;
    }

    public String getDescription() {
        return description;
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

    public Media getMedia() {
        return media;
    }

    public String getType() {
        return type;
    }

    public String getPreview_image_id() {
        return preview_image_id;
    }

    public String getSlider_status() {
        return slider_status;
    }

    public String getVideo_id() {
        return video_id;
    }

    public Category getCategory() {
        return category;
    }

    public static class Media {
        @SerializedName("images")
        private ArrayList<String> images;
        @SerializedName("video")
        private String video;
        @SerializedName("youtube")
        private String youtube;

        public ArrayList<String> getImages() {
            return images;
        }

        public String getVideo() {
            return video;
        }

        public String getYoutube() {
            return youtube;
        }
    }

    public static class Category {
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
}

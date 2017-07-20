package duan2.jobspef.luyquangdat.com.myapplication.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OfferResponse {
    @SerializedName("total")
    private int total;
    @SerializedName("per_page")
    private int per_page;
    @SerializedName("current_page")
    private int current_page;
    @SerializedName("last_page")
    private int last_page;
    @SerializedName("next_page_url")
    private String next_page_url;
    @SerializedName("prev_page_url")
    private String prev_page_url;
    @SerializedName("from")
    private int from;
    @SerializedName("to")
    private int to;
    @SerializedName("data")
    private ArrayList<Data> data;

    public int getTotal() {
        return total;
    }

    public int getPer_page() {
        return per_page;
    }

    public int getCurrent_page() {
        return current_page;
    }

    public int getLast_page() {
        return last_page;
    }

    public String getNext_page_url() {
        return next_page_url;
    }

    public String getPrev_page_url() {
        return prev_page_url;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public ArrayList<Data> getData() {
        return data;
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

    public static class Data {
        @SerializedName("id")
        private int id;
        @SerializedName("title")
        private String title;
        @SerializedName("short_description")
        private String short_description;
        @SerializedName("preview_image_id")
        private String preview_image_id;
        @SerializedName("updated_at")
        private String updated_at;
        @SerializedName("category_id")
        private String category_id;
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

        public String getPreview_image_id() {
            return preview_image_id;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public String getCategory_id() {
            return category_id;
        }

        public Category getCategory() {
            return category;
        }
        @Override
        public boolean equals(Object obj) {
            if(obj instanceof Data){
                return this.getId() == ((Data) obj).getId();
            }
            else {
                return  false;
            }
        }
    }



}

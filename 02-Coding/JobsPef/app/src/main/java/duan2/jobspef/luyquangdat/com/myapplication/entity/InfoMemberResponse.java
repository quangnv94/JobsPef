package duan2.jobspef.luyquangdat.com.myapplication.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by quangnv on 7/27/17.
 */

public class InfoMemberResponse implements Serializable {

    @SerializedName("message")
    private Message message;
    @SerializedName("status")
    private int status;

    public Message getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public static class Message {
        @SerializedName("id")
        private String id;
        @SerializedName("profile_stt")
        private String profile_stt;
        @SerializedName("name")
        private String name;
        @SerializedName("phone_number")
        private String phone_number;
        @SerializedName("contact_email")
        private String contact_email;
        @SerializedName("address")
        private String address;
        @SerializedName("create_at")
        private String create_at;
        @SerializedName("updated_at")
        private String updated_at;
        @SerializedName("avatar_id")
        private String avatar_id;

        public String getId() {
            return id;
        }

        public String getProfile_stt() {
            return profile_stt;
        }

        public String getName() {
            return name;
        }

        public String getPhone_number() {
            return phone_number;
        }

        public String getContact_email() {
            return contact_email;
        }

        public String getAddress() {
            return address;
        }

        public String getCreate_at() {
            return create_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public String getAvatar_id() {
            return avatar_id;
        }
    }
}

package duan2.jobspef.luyquangdat.com.myapplication.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoginResponse implements Serializable{

    @SerializedName("profile")
    private Profile profile;
    @SerializedName("user")
    private User user;

    public Profile getProfile() {
        return profile;
    }

    public User getUser() {
        return user;
    }

    public static class Profile {
        @SerializedName("id")
        private String id;
        @SerializedName("profile_id")
        private String profile_id;
        @SerializedName("name")
        private String name;
        @SerializedName("phone_number")
        private String phone_number;
        @SerializedName("contact_emal")
        private String contact_emal;
        @SerializedName("address")
        private String address;
        @SerializedName("create_at")
        private String create_at;
        @SerializedName("update_at")
        private String update_at;
        @SerializedName("avatar_id")
        private String avatar_id;

        public String getId() {
            return id;
        }

        public String getProfile_id() {
            return profile_id;
        }

        public String getName() {
            return name;
        }

        public String getPhone_number() {
            return phone_number;
        }

        public String getContact_emal() {
            return contact_emal;
        }

        public String getAddress() {
            return address;
        }

        public String getCreate_at() {
            return create_at;
        }

        public String getUpdate_at() {
            return update_at;
        }

        public String getAvatar_id() {
            return avatar_id;
        }
    }

    public static class User {
        @SerializedName("id")
        private String id;
        @SerializedName("user_id")
        private String user_id;
        @SerializedName("email")
        private String email;
        @SerializedName("password")
        private String password;
        @SerializedName("facebook_id")
        private String facebook_id;
        @SerializedName("profile_id")
        private String profile_id;
        @SerializedName("create_at")
        private String create_at;
        @SerializedName("token")
        private String token;

        public String getId() {
            return id;
        }

        public String getUser_id() {
            return user_id;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }

        public String getFacebook_id() {
            return facebook_id;
        }

        public String getProfile_id() {
            return profile_id;
        }

        public String getCreate_at() {
            return create_at;
        }

        public String getToken() {
            return token;
        }
    }
}

package duan2.jobspef.luyquangdat.com.myapplication.service;


import java.util.ArrayList;

import duan2.jobspef.luyquangdat.com.myapplication.entity.CategoryResponse;
import duan2.jobspef.luyquangdat.com.myapplication.entity.InfoMemberResponse;
import duan2.jobspef.luyquangdat.com.myapplication.entity.LoginResponse;
import duan2.jobspef.luyquangdat.com.myapplication.entity.SimpleResponse;
import duan2.jobspef.luyquangdat.com.myapplication.entity.TestResponse;
import duan2.jobspef.luyquangdat.com.myapplication.entity.NotificationResponse;
import duan2.jobspef.luyquangdat.com.myapplication.entity.OfferDetailResponse;
import duan2.jobspef.luyquangdat.com.myapplication.entity.PostResponse;
import duan2.jobspef.luyquangdat.com.myapplication.entity.WhoWeAreResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServerAPI {

    @POST("/api/subscriber")
    Call<Void> setSubscribe();

    @FormUrlEncoded
    @POST("api/newuser")
    Call<SimpleResponse> register(@Field("name") String name,
                                  @Field("email") String email,
                                  @Field("pass") String password,
                                  @Field("facebook_id") String facebookid);

    @GET("/login")
    Call<LoginResponse> login(@Query("email") String email,
                              @Query("pass") String pass,
                              @Query("facebook_id") String facebookid);

    @GET("/profile")
    Call<InfoMemberResponse> getInfoMember(@Query("id") String id);

    @FormUrlEncoded
    @POST("/api/upprofile?")
    Call<SimpleResponse> updateProfile(@Query("token") String token,
                                       @Field("name") String name,
                                       @Field("avatar_id") String avatar,
                                       @Field("phone_number") String phone,
                                       @Field("address") String address);

    @FormUrlEncoded
    @POST("/api/post?")
    Call<SimpleResponse> updateCreatePost(@Query("token") String token,
                                          @Field("title") String title,
                                          @Field("category_id") String category_id,
                                          @Field("images") String image,
                                          @Field("description")String description,
                                          @Field("benefits") String benefits,
                                          @Field("time_limited") String time_limited,
                                          @Field("contact") String contact,
                                          @Field("place") String place,
                                          @Field("requirement") String requirement);

    @GET("/list")
    Call<ArrayList<PostResponse>> getPost(@Query("category_id") String category_id);

    @GET("/categorys")
    Call<ArrayList<CategoryResponse>> getCategory();

    @GET("/api/pdfs")
    Call<ArrayList<TestResponse>> getDocument();

    @GET("/api/notification")
    Call<ArrayList<NotificationResponse>> getAllNotification();

    @GET("/api/notification/{notification_id}")
    Call<NotificationResponse> getNotificationDetail(@Path("notification_id") String notification_id);

    @GET("/api/setting?key=who_we_are")
    Call<WhoWeAreResponse> getWhoWeAre();

    @FormUrlEncoded
    @POST("/api/feedback")
    Call<Void> sendFeedback(@Field("email") String email, @Field("name") String name, @Field("message") String message);

    @FormUrlEncoded
    @POST("/api/device")
    Call<Void> sendNotificationToken(@Field("device_type") String device_type, @Field("device_token") String device_token);

    @GET("/api/offer")
    Call<PostResponse> getAllOfferInCategory(@Query("category_id") String category_id, @Query("page") int page);

    @GET("/api/offer/{offer_id}")
    Call<OfferDetailResponse> getOfferDetail(@Path("offer_id") String offer_id);

}


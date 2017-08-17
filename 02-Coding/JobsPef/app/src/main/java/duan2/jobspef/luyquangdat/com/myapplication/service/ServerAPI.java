package duan2.jobspef.luyquangdat.com.myapplication.service;


import java.util.ArrayList;

import duan2.jobspef.luyquangdat.com.myapplication.entity.CategoryResponse;
import duan2.jobspef.luyquangdat.com.myapplication.entity.InfoMemberResponse;
import duan2.jobspef.luyquangdat.com.myapplication.entity.LoginResponse;
import duan2.jobspef.luyquangdat.com.myapplication.entity.SimpleResponse;
import duan2.jobspef.luyquangdat.com.myapplication.entity.PostResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ServerAPI {

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
                                          @Field("description") String description,
                                          @Field("benefits") String benefits,
                                          @Field("time_limited") String time_limited,
                                          @Field("contact") String contact,
                                          @Field("place") String place,
                                          @Field("requirement") String requirement);

    @FormUrlEncoded
    @POST("/api/post?")
    Call<SimpleResponse> sendFeedBack(@Field("name") String name,
                                          @Field("email") String email,
                                          @Field("message") String message);

    @GET("/list")
    Call<ArrayList<PostResponse>> getPost(@Query("category_id") String category_id);

    @GET("/categorys")
    Call<ArrayList<CategoryResponse>> getCategory();

    @GET("/find")
    Call<ArrayList<PostResponse>> findPost(@Query("title") String title);
}


package duan2.jobspef.luyquangdat.com.myapplication.service;



import java.util.ArrayList;

import duan2.jobspef.luyquangdat.com.myapplication.entity.CategoryResponse;
import duan2.jobspef.luyquangdat.com.myapplication.entity.TestResponse;
import duan2.jobspef.luyquangdat.com.myapplication.entity.NotificationResponse;
import duan2.jobspef.luyquangdat.com.myapplication.entity.OfferDetailResponse;
import duan2.jobspef.luyquangdat.com.myapplication.entity.OfferResponse;
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

    @GET("/api/category")
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
    Call<OfferResponse> getAllOfferInCategory(@Query("category_id") String category_id, @Query("page") int page);

    @GET("/api/offer/{offer_id}")
    Call<OfferDetailResponse> getOfferDetail(@Path("offer_id") String offer_id);

    @GET("/api/offer/{city}/best")
    Call<ArrayList<OfferResponse.Data>> getBestOfferOfCity(@Path("city") String city, @Query("id") int id, @Query("limit") String limit);

}


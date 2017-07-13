package duan2.jobspef.luyquangdat.com.myapplication.service;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import duan2.jobspef.luyquangdat.com.myapplication.common.Constants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConnectServer {

    private static ServerAPI responseAPI = null;

    public static ServerAPI getResponseAPI() {
        Gson gson = new GsonBuilder()
                .setLenient().create();
        if (responseAPI == null) {

            Retrofit.Builder builder = new Retrofit.Builder();
            builder.baseUrl(Constants.HOST);
            builder.addConverterFactory(GsonConverterFactory.create(gson));
            Retrofit retrofit = builder.build();
            responseAPI = retrofit.create(ServerAPI.class);
        }
        return responseAPI;
    }

}

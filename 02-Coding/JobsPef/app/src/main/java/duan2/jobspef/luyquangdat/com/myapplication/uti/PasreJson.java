package duan2.jobspef.luyquangdat.com.myapplication.uti;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import duan2.jobspef.luyquangdat.com.myapplication.model.CategoryItem;

/**
 * Created by nguye on 7/11/2017.
 */

public class PasreJson {
    private Context mContext;

    public PasreJson(Context mContext) {
        this.mContext = mContext;
    }

    public static String getJsonFromServer(String urxl) throws IOException {

        BufferedReader inputStream = null;

        URL jsonUrl = new URL(urxl);
        URLConnection dc = jsonUrl.openConnection();

        dc.setConnectTimeout(5000);
        dc.setReadTimeout(5000);

        inputStream = new BufferedReader(new InputStreamReader(
                dc.getInputStream()));
        // read the JSON results into a string
        String jsonResult = inputStream.readLine();
        return jsonResult;
    }

    public static ArrayList<CategoryItem> getListCategoryByJson(String urlx) {
        ArrayList<CategoryItem> values =new ArrayList<CategoryItem>();
        JsonArray jsonArray;
        try {
            String data = getJsonFromServer(urlx);
            JsonParser parser = new JsonParser();
            jsonArray = (JsonArray) parser.parse(data);
            for (int i = 0; i < jsonArray.size(); i++) {
                JsonElement item = jsonArray.get(i);
                CategoryItem categoryItem = getCategoryItemFormElement(item);
                 values.add(categoryItem);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }

        return values;
    }

    public static CategoryItem getCategoryItemFormElement(JsonElement element) {
        Gson gson = new Gson();
        CategoryItem categoryItem = gson.fromJson(element.toString(), CategoryItem.class);
        return categoryItem;
    }
}

package br.com.nespressohelper.daitan.nespressohelper;

import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by estrela on 12/26/17.
 */

public class BackEndComm{

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void get(final String url, final BackEndCommHandler handler) throws IOException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Request request = new Request.Builder().url(url).build();

                try(Response response = client.newCall(request).execute()) {
                    handler.handleResponse(response.body().string(), BackEndCommHandler.Method.GET);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();

    }

    public static ArrayList<Coffee> jsonToObj(String json) {
        ArrayList<Coffee> coffees = new ArrayList<>();
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Coffee.class, new CoffeeDeserializer());
        Gson gson = builder.create();

        coffees = gson.fromJson(json, new TypeToken<ArrayList<Coffee>>(){}.getType());

        return coffees;
    }

    public static String objToJson(Coffee coffee) {

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        builder.serializeNulls();

        String json = gson.toJson(coffee);
        return json;
    }

    public static ArrayList<String> allObjToJson(ArrayList<Coffee> coffees) {
        ArrayList<String> json = new ArrayList<>();

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        builder.serializeNulls();

        String aux = "";

        for(int i = 0; i < coffees.size(); i ++) {
            aux = gson.toJson(coffees.get(i));
            json.add(aux);
        }

        return json;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void post(final String url, final String json, final BackEndCommHandler handler) throws IOException{
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                RequestBody body = RequestBody.create(JSON, json);
                Request request = new Request.Builder().url(url).post(body).build();

                try(Response response = client.newCall(request).execute()) {
                    handler.handleResponse(response.code(), BackEndCommHandler.Method.POST);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}

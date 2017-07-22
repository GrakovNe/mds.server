package org.grakovne.mds.server.importer.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public abstract class ApiFactory {
    private static final String BASE_URL = "http://fantlab.ru/";

    private static final Gson gson = new GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
        .create();

    private static final Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build();

    public static Retrofit getRetrofit() {
        return retrofit;
    }
}

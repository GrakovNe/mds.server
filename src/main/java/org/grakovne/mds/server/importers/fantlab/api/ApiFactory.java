package org.grakovne.mds.server.importers.fantlab.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Retrofit api factory.
 */

public abstract class ApiFactory {
    private static final String BASE_URL = "http://fantlab.ru/";

    private static final Gson GSON = new GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
        .create();

    private static final Retrofit RETROFIT = new Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(GSON))
        .build();

    public static Retrofit getRetrofit() {
        return RETROFIT;
    }
}

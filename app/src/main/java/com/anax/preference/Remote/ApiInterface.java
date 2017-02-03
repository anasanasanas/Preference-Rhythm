package com.anax.preference.Remote;

import com.anax.preference.Model.Country;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * @author Anas Alaa
 * @version 1.0.0
 * @since 27/01/2017
 */

public interface ApiInterface {

    String ENDPOINT = "https://gist.githubusercontent.com/anasanasanas/";

    @Headers("Cache-Control: no-cache, no-store, must-revalidate")
    @GET("41b2c223eed7528f4bdba6422ecc5ddc/raw/2108d4aa2aeba6ba5ceff824ba6332bb3bbd135e/Countries%2520Code")
    Observable<List<Country>> getCountryCodeObservable();

    @Headers("Cache-Control: no-cache, no-store, must-revalidate")
    @GET("41b2c223eed7528f4bdba6422ecc5ddc/raw/2108d4aa2aeba6ba5ceff824ba6332bb3bbd135e/Countries%2520Code")
    Call<List<Country>> getCountryCode();


    class Creator {
        public static ApiInterface newApiService() {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ApiInterface.ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            return retrofit.create(ApiInterface.class);
        }
    }
}

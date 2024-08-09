package com.basic.weatherforecast;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private static Retrofit retrofit = null;
    private static String base_url = "https://api.weatherapi.com/v1/";

    public static WeatherApi getweatherApi(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(base_url).addConverterFactory(GsonConverterFactory.create()).build();

        }
        return retrofit.create(WeatherApi.class);
    }
}

package com.basic.weatherforecast;


import com.basic.weatherforecast.Weather.Weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {

    @GET("forecast.json")
    Call<Weather>  getWeather(@Query("key") String key,
                              @Query("q") String city ,
                              @Query("days") String days,
                              @Query("aqi")  String aqi,
                              @Query("alert") String alert
            );
}

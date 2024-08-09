package com.basic.weatherforecast.Weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class weather_forecast {

    @SerializedName("forecastday")
    @Expose
    private List<forecastDay> forecastDay;

    public weather_forecast() {
    }

    public weather_forecast(List<com.basic.weatherforecast.Weather.forecastDay> forecastDay) {
        this.forecastDay = forecastDay;
    }

    public List<com.basic.weatherforecast.Weather.forecastDay> getForecastDay() {
        return forecastDay;
    }

    public void setForecastDay(List<com.basic.weatherforecast.Weather.forecastDay> forecastDay) {
        this.forecastDay = forecastDay;
    }
}

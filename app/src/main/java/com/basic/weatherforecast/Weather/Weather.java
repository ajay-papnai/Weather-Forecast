package com.basic.weatherforecast.Weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Weather {

    @SerializedName("location")
    @Expose
    private weather_location weatherLocation;
    public weather_location getWeatherLocation() {
        return weatherLocation;
    }
    public void setWeatherLocation(weather_location weatherLocation) {
        this.weatherLocation = weatherLocation;
    }

    @SerializedName("current")
    @Expose
    private current_weather currentWeather;
    public current_weather getCurrentWeather() {
        return currentWeather;
    }
    public void setCurrentWeather(current_weather currentWeather) {
        this.currentWeather = currentWeather;
    }

    @SerializedName("forecast")
    @Expose
    private weather_forecast weatherForecast;
    public weather_forecast getWeatherForecast() {
        return weatherForecast;
    }
    public void setWeatherForecast(weather_forecast weatherForecast) {
        this.weatherForecast = weatherForecast;
    }


    public Weather() {
    }

    public Weather(weather_location weatherLocation, current_weather currentWeather, weather_forecast weatherForecast) {
        this.weatherLocation = weatherLocation;
        this.currentWeather = currentWeather;
        this.weatherForecast = weatherForecast;
    }
}

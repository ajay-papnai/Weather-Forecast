package com.basic.weatherforecast.Weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class current_weather {

    @SerializedName("temp_c")
    @Expose
    private String temperature;
    public String getTemperature() {
        return temperature;
    }
    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }


    @SerializedName("condition")
    @Expose
    private weather_condition weatherCondition;
    public weather_condition getWeatherCondition() {
        return weatherCondition;
    }
    public void setWeatherCondition(weather_condition weatherCondition) {
        this.weatherCondition = weatherCondition;
    }




    @SerializedName("wind_kph")
    @Expose
    private String wind ;
    public String getWind() {
        return wind;
    }
    public void setWind(String wind) {
        this.wind = wind;
    }


    @SerializedName("humidity")
    @Expose
    private String Humidity;
    public String getHumidity() {
        return Humidity;
    }
    public void setHumidity(String humidity) {
        Humidity = humidity;
    }




    public current_weather() {
    }

    public current_weather(String temperature, weather_condition weatherCondition, String wind, String humidity) {
        this.temperature = temperature;
        this.weatherCondition = weatherCondition;
        this.wind = wind;
        Humidity = humidity;
    }
}

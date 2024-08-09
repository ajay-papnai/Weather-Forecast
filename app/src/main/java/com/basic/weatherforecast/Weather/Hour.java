package com.basic.weatherforecast.Weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Hour {

    @SerializedName("time")
    @Expose
    private String time;
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }


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



    public Hour() {
    }

    public Hour(String time, String temperature, weather_condition weatherCondition) {
        this.time = time;
        this.temperature = temperature;
        this.weatherCondition = weatherCondition;
    }


}

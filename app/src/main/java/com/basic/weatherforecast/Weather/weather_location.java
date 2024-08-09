package com.basic.weatherforecast.Weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class weather_location {

    @SerializedName("name")
    @Expose
    private String nameOfCity;
    public void setNameOfCity(String nameOfCity) {
        this.nameOfCity = nameOfCity;
    }
    public String getNameOfCountry() {
        return nameOfCountry;
    }


    @SerializedName("country")
    @Expose
    private String nameOfCountry;
    public String getNameOfCity() {
        return nameOfCity;
    }
    public void setNameOfCountry(String nameOfCountry) {
        this.nameOfCountry = nameOfCountry;
    }


    @SerializedName("localtime")
    @Expose
    private String time;
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }



    public weather_location() {
    }

    public weather_location(String nameOfCity, String nameOfCountry, String time) {
        this.nameOfCity = nameOfCity;
        this.nameOfCountry = nameOfCountry;
        this.time = time;
    }
}

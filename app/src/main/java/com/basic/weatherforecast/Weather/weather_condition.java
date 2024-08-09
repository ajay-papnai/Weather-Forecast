package com.basic.weatherforecast.Weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class weather_condition {

    @SerializedName("text")
    @Expose
    private String about;
    public String getAbout() {
        return about;
    }
    public void setAbout(String about) {
        this.about = about;
    }


    @SerializedName("icon")
    @Expose
    private String icon;
    public String getIcon() {
        return icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }


    public weather_condition() {
    }

    public weather_condition(String about, String icon) {
        this.about = about;
        this.icon = icon;
    }



}

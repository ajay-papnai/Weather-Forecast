package com.basic.weatherforecast.Weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class forecastDay {

    @SerializedName("hour")
    @Expose
    private List<Hour> hour;

    public forecastDay() {
    }

    public List<Hour> getHour() {
        return hour;
    }

    public void setHour(List<Hour> hour) {
        this.hour = hour;
    }

    public forecastDay(List<Hour> hour) {
        this.hour = hour;
    }
}

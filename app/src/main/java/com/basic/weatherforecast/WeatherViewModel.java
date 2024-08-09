package com.basic.weatherforecast;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.basic.weatherforecast.Weather.current_weather;
import com.basic.weatherforecast.Weather.weather_forecast;
import com.basic.weatherforecast.Weather.weather_location;

import java.util.List;

public class WeatherViewModel extends AndroidViewModel {

    private Repository repository;


    public WeatherViewModel(@NonNull Application application) {
        super(application);
        this.repository = new Repository(application);
    }

    public LiveData<List<weather_location>> getWeatherLocation(String CityName){
        return repository.getLocationMutableLiveData(CityName);
    }

    public LiveData<List<current_weather>> getCurrentWeather(String CityName){
        return repository.getCurrentMutablelLiveData(CityName);
    }

    public LiveData<List<weather_forecast>> getWeatherForecast(String CityName){
        return repository.getForecastMutableLiveData(CityName);
    }
}

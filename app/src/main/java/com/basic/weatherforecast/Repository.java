package com.basic.weatherforecast;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;


import com.basic.weatherforecast.Weather.Hour;
import com.basic.weatherforecast.Weather.Weather;
import com.basic.weatherforecast.Weather.current_weather;
import com.basic.weatherforecast.Weather.forecastDay;
import com.basic.weatherforecast.Weather.weather_condition;
import com.basic.weatherforecast.Weather.weather_forecast;
import com.basic.weatherforecast.Weather.weather_location;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {

    private Application application;

    public Repository(Application application) {
        this.application = application;
    }


    private ArrayList<weather_location> locationArrayList = new ArrayList<>();
    private MutableLiveData<List<weather_location>> locationMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<List<weather_location>> getLocationMutableLiveData(String cityName){
        WeatherApi weatherApi = RetrofitInstance.getweatherApi();

        Call<Weather> weatherCall = weatherApi.getWeather("c39ca303e0cc44d7811173923240308", cityName , "1" , "yes" , "no") ;

        //Log.d("TAGY", ""+weatherCall);
        //Log.d("Repository", "API Call: " + weatherCall.request().url());
        weatherCall.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                if (response.isSuccessful()) {
                    Log.d("tagy", "response succesfull location");
                    Weather weather = response.body();
                    if (weather != null) {
                        weather_location location = weather.getWeatherLocation();
                        if (location != null) {
                            /*Log.d("Tagy", "City: " + location.getNameOfCity() +
                                    ", Country: " + location.getNameOfCountry() +
                                    ", Time: " + location.getTime());*/
                            locationArrayList.clear();
                            locationArrayList.add(location);
                            locationMutableLiveData.setValue(locationArrayList);
                        } else {
                            Log.d("tagy", "Location data is null");
                        }
                    } else {
                        Log.d("tagy", "Weather data is null");
                    }
                } else {
                    Log.d("tagy", "Response not successful: location" + response.message());
                }
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable throwable) {
                Log.d("tagy", "API Call failed: " + throwable.getMessage());
            }
        });

        return locationMutableLiveData;
    }


    private ArrayList<current_weather> currentWeatherArrayList = new ArrayList<>();
    private MutableLiveData<List<current_weather>> currentMutablelLiveData = new MutableLiveData<>();
    public MutableLiveData<List<current_weather>> getCurrentMutablelLiveData(String cityName){
        WeatherApi weatherApi = RetrofitInstance.getweatherApi();

        Call<Weather> weatherCall = weatherApi.getWeather("c39ca303e0cc44d7811173923240308", cityName , "1" , "yes" , "no") ;

        weatherCall.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                if (response.isSuccessful()){
                    Weather weather = response.body();
                    if (weather!=null){
                        current_weather currentWeather = weather.getCurrentWeather();
                        if(currentWeather!=null){
                            weather_condition weatherCondition = currentWeather.getWeatherCondition();
                            if(weatherCondition!=null){

                                currentWeatherArrayList.clear();
                                currentWeatherArrayList.add(currentWeather);
                                currentMutablelLiveData.setValue(currentWeatherArrayList);
                            }else {
                                Log.d("tagy", "weather condition null");
                            }
                        }else{Log.d("tagy" , "current weather is null");}
                    }
                }
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable throwable) {

            }


        });

        return currentMutablelLiveData;
    }


    private ArrayList<weather_forecast> weatherForecastArrayList =  new ArrayList<>();
    private MutableLiveData<List<weather_forecast>> ForecastMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<List<weather_forecast>> getForecastMutableLiveData(String CityName){

        WeatherApi weatherApi = RetrofitInstance.getweatherApi();
        Call<Weather> weatherCall = weatherApi.getWeather("c39ca303e0cc44d7811173923240308", CityName , "1" , "yes" , "no") ;

        weatherCall.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                if(response.isSuccessful()){
                    //Log.d("tagy" ,"inside forecast");
                    Weather weather = response.body();
                    if(weather!=null){
                        weather_forecast weatherForecast = weather.getWeatherForecast();
                        if(weatherForecast!=null){
                            List<forecastDay> forecastDay = weatherForecast.getForecastDay();
                            if(forecastDay!=null){
                                List<Hour> hour = forecastDay.get(0).getHour();
                                if(hour!=null){
                                    weatherForecastArrayList.clear();
                                    weatherForecastArrayList.add(weatherForecast);
                                    ForecastMutableLiveData.setValue(weatherForecastArrayList);
                                }else{Log.d("tagy", "hour is null");}
                            }else{Log.d("tagy", "forecast day null");}
                        }else{Log.d("tagy" , "weather forecast is null");}
                    }else{Log.d("tagy" , "weather is null forecast");}
                }
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable throwable) {
                Log.d("tagy", "API Call failed forecast: " + throwable.getMessage());
            }
        });

        return ForecastMutableLiveData;
    }











}

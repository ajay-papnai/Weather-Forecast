package com.basic.weatherforecast;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.basic.weatherforecast.Weather.Hour;
import com.basic.weatherforecast.Weather.current_weather;
import com.basic.weatherforecast.Weather.forecastDay;
import com.basic.weatherforecast.Weather.weather_condition;
import com.basic.weatherforecast.Weather.weather_forecast;
import com.basic.weatherforecast.Weather.weather_location;
import com.basic.weatherforecast.databinding.ActivityMainBinding;
import com.bumptech.glide.Glide;
import android.Manifest;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding activityMainBinding;
    private WeatherViewModel weatherViewModel;
    private WeatherAdapter weatherAdapter;

    private LocationManager locationManager;
    private int PERMISSION_CODE = 1;
    private String cityName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        activityMainBinding = DataBindingUtil.setContentView(this , R.layout.activity_main);

        weatherViewModel = new ViewModelProvider(this).get(WeatherViewModel.class);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if(ActivityCompat.checkSelfPermission(this , Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this , Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this , new String[]{Manifest.permission.ACCESS_FINE_LOCATION , Manifest.permission.ACCESS_COARSE_LOCATION},PERMISSION_CODE);

        }else{
            handleLocation();
        }

        handleLocation();


        activityMainBinding.inputCity.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(activityMainBinding.citySearch.getText().toString().isEmpty()){
                    Log.d("tagy","empty search");
                }else{
                    searchWeather(activityMainBinding.citySearch.getText().toString());
                    activityMainBinding.citySearch.setText("");
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(activityMainBinding.citySearch.getWindowToken(), 0);
                    }
                }
            }
        });


    }

    private void handleLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && !locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                // Prompt user to enable location services
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
                return;
            }

            boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // Prompt user to enable location services
                Toast.makeText(this, "Please enable location services", Toast.LENGTH_SHORT).show();
                return;
            }



            LocationListener locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    try {
                        if (location != null) {
                            cityName = getCityName(location.getLongitude(), location.getLatitude());
                            searchWeather(cityName);
                            locationManager.removeUpdates(this); // Stop listening after getting location
                        } else {
                            Log.d("tagy", "Location is null");
                            Toast.makeText(MainActivity.this, "Unable to get location", Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("tagy", "Error getting city name", e);
                    }
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {}

                @Override
                public void onProviderEnabled(String provider) {}

                @Override
                public void onProviderDisabled(String provider) {}
            };

            // Request location updates
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            }


            // Try to get last known location
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location == null) {
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }

            if (location != null) {
                try {
                    Log.d("tagy", "location "+ location.getLongitude()+"   "+location.getLatitude());
                    cityName = getCityName(location.getLongitude(), location.getLatitude());
                    Log.d("tagy", "city  "+cityName);
                    searchWeather(cityName);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("tagy", "Error getting city name", e);
                }
            } else {
                Log.d("tagy", "Location is null");
                //Toast.makeText(this, "Unable to get location", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Location permissions are required to access location", Toast.LENGTH_SHORT).show();
        }


    }


    private String getCityName(double longitude, double latitude) throws IOException {
        List<Address> addresses = null;
        String cityName = "";
        Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());

        try {
            addresses = gcd.getFromLocation(latitude,longitude,10);
            for(Address adr : addresses) {
                if (adr != null) {
                    String city = adr.getLocality();
                    if (city != null && !city.equals("")) {
                        cityName = city;
                    } else {
                        Log.d("TAG", "CITY NOT FOUND");
                    }
                }
            }

        }catch (IOException e){
            e.printStackTrace();
        }
        return cityName;
    }

    public void searchWeather(String CityName){
        weatherViewModel.getWeatherLocation(CityName).observe(this, new Observer<List<weather_location>>() {
            @Override
            public void onChanged(List<weather_location> weatherLocations) {
                if(weatherLocations!=null && !weatherLocations.isEmpty()){
                    weather_location weatherLocation = weatherLocations.get(0);
                    activityMainBinding.City.setText(weatherLocation.getNameOfCity());
                }
            }
        });

        weatherViewModel.getCurrentWeather(CityName).observe(this, new Observer<List<current_weather>>() {
            @Override
            public void onChanged(List<current_weather> currentWeathers) {
                if(currentWeathers!=null && !currentWeathers.isEmpty()){
                    current_weather currentWeather = currentWeathers.get(0);

                    activityMainBinding.condition.setText(currentWeather.getWeatherCondition().getAbout());
                    activityMainBinding.tempImg.setVisibility(View.VISIBLE);
                    activityMainBinding.temperature.setText(currentWeather.getTemperature()+"°C");
                    activityMainBinding.humImg.setVisibility(View.VISIBLE);
                    activityMainBinding.humidity.setText(currentWeather.getHumidity());
                    activityMainBinding.windImg.setVisibility(View.VISIBLE);
                    activityMainBinding.wind.setText(currentWeather.getWind()+" Kph");
                    //Log.d("tagy" , "https:"+currentWeather.getWeatherCondition().getIcon());
                    Glide.with(MainActivity.this).load("https:"+currentWeather.getWeatherCondition().getIcon()).into(activityMainBinding.WeatherImage);
                }
            }
        });

        activityMainBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL , false));

        weatherViewModel.getWeatherForecast(CityName).observe(this, new Observer<List<weather_forecast>>() {
            @Override
            public void onChanged(List<weather_forecast> weatherForecasts) {
                if (weatherForecasts != null && !weatherForecasts.isEmpty()) {
                    weather_forecast weatherForecast = weatherForecasts.get(0);
                    List<forecastDay> forecastDays = weatherForecast.getForecastDay();

                    if (forecastDays != null && !forecastDays.isEmpty()) {
                        forecastDay firstForecastDay = forecastDays.get(0);
                        List<Hour> hours = firstForecastDay.getHour();

                        if (hours != null && !hours.isEmpty()) {
                            weather_condition weatherCondition = hours.get(0).getWeatherCondition();
                            weatherAdapter = new WeatherAdapter(hours,weatherViewModel , getApplicationContext());
                            activityMainBinding.recyclerView.setAdapter(weatherAdapter);
                            activityMainBinding.textView2.setText("Today's Weather Forecast");

                        } else {
                            Log.d("tagy", "Hour list is null or empty");
                        }
                    } else {
                        Log.d("tagy", "Forecast day list is null or empty");
                    }
                } else {
                    Log.d("tagy", "Weather forecast list is null or empty");
                }
            }
        });



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                handleLocation(); // Call handleLocation() after permissions are granted
            } else {
                Toast.makeText(this, "Location permission is required to access location", Toast.LENGTH_SHORT).show();
            }
        }
    }

    



}
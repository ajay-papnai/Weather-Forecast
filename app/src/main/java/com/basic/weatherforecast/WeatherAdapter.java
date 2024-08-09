package com.basic.weatherforecast;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.basic.weatherforecast.Weather.Hour;
import com.basic.weatherforecast.Weather.Weather;
import com.basic.weatherforecast.databinding.WeatherListBinding;
import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {

    private List<Hour> hourList;
    private Context context;

    private WeatherViewModel weatherViewModel;

    public WeatherAdapter(List<Hour> hourList, WeatherViewModel weatherViewModel , Context context) {
        this.hourList = hourList;
        this.weatherViewModel = weatherViewModel;
        this.context=context;
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        WeatherListBinding weatherListBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.weather_list,viewGroup ,false);
        return new WeatherViewHolder(weatherListBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder weatherViewHolder, int i) {
        Hour hours = hourList.get(i);
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        SimpleDateFormat output = new SimpleDateFormat("hh:mm aa");
        Date date = null;
        try {
            date = input.parse(hours.getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        weatherViewHolder.weatherListBinding.Time.setText(output.format(date));
        weatherViewHolder.weatherListBinding.temp.setText(hours.getTemperature()+"°C");
        Glide.with(context).load("https:"+hours.getWeatherCondition().getIcon()).into(weatherViewHolder.weatherListBinding.weatherImage);
    }

    @Override
    public int getItemCount() {
        return hourList.size();
    }

    class WeatherViewHolder extends RecyclerView.ViewHolder{

        private WeatherListBinding weatherListBinding;

        public WeatherViewHolder(WeatherListBinding weatherListBinding) {
            super(weatherListBinding.getRoot());
            this.weatherListBinding=weatherListBinding;
        }
    }
}

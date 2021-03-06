package com.example.testretrofitapp.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.testretrofitapp.data.WeatherForecastRepositoryImpl
import com.example.testretrofitapp.domain.*
import kotlinx.coroutines.launch

class OverViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = WeatherForecastRepositoryImpl(application)
    private val getCurrentWeatherUseCase = GetCurrentWeatherUseCase(repository)
    private val getWeekWeatherUseCase = GetWeekWeatherUseCase(repository)
    private val loadDataUseCase = LoadDataUseCase(repository)


//    private val _status = MutableLiveData<String>()
//    val status: LiveData<String> = _status
//    //  get() = _status

    private val _currentWeatherDto = MutableLiveData<CurrentWeatherEntity>()
    val currentWeatherDto: LiveData<CurrentWeatherEntity>
        get() = _currentWeatherDto

    private val _weekWeatherDto = MutableLiveData<List<DailyWeatherEntity>>()
    val weekWeatherDto: LiveData<List<DailyWeatherEntity>>
        get() = _weekWeatherDto


    init {
        Log.d("TAG", "start init in OverViewModel.kt")
        getWeather()
    }

    fun getWeather() {
        viewModelScope.launch {
            loadDataUseCase()
            _weekWeatherDto.value = getWeekWeatherUseCase.invoke()
            _currentWeatherDto.value = getCurrentWeatherUseCase.invoke()
        }
    }
}
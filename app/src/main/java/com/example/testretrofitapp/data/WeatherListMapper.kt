package com.example.testretrofitapp.data

import android.icu.text.SimpleDateFormat
import com.example.testretrofitapp.data.database.CurrentWeatherDbModel
import com.example.testretrofitapp.data.database.DailyWeatherDbModel
import com.example.testretrofitapp.data.database.HourlyWeatherDbModel
import com.example.testretrofitapp.data.network.CurrentWeatherDto
import com.example.testretrofitapp.data.network.DailyWeatherDto
import com.example.testretrofitapp.data.network.HourlyWeatherDto
import com.example.testretrofitapp.domain.CurrentWeatherEntity
import com.example.testretrofitapp.domain.DailyWeatherEntity
import com.example.testretrofitapp.domain.HourlyWeatherEntity
import java.util.*


class WeatherListMapper {

    private val language = Locale.getDefault()
    private val formatForCurrentWeather = SimpleDateFormat("(HH:mm)", language)
    private val formatForDailyWeather = SimpleDateFormat("EEEE, dd MMMM", language)

    private fun String.myCapitalize():String {
        if (this.isNotEmpty()){
            if (this[0].isLowerCase()) this[0].titlecase(
                Locale.getDefault()
            ) else this[0].toString()
        }
       return this
    }

////////////////////////////////////  DtoToDbModel  ////////////////////////////////////////////////

    fun mapCurrentDtoToCurrentDbModel(currentWeatherDto: CurrentWeatherDto): CurrentWeatherDbModel {
        return CurrentWeatherDbModel(
            id = 0,
            dt = formatForCurrentWeather.format(currentWeatherDto.dt.toLong() * 1000),
            temp = currentWeatherDto.temp.substring(0, 2) + "\u00B0C",
            feelsLike = currentWeatherDto.feelsLike.substring(0, 2) + "\u00B0C",
            pressure = currentWeatherDto.pressure,
            humidity = currentWeatherDto.humidity,
            windSpeed = currentWeatherDto.windSpeed,
            windGust = currentWeatherDto.windGust,
            windDeg = currentWeatherDto.windDeg,
            description = currentWeatherDto.weather[0].description.myCapitalize(),
            icon = currentWeatherDto.weather[0].icon
        )
    }

    private fun mapHourlyDtoToHourlyDbModel(hourlyWeatherDto: HourlyWeatherDto): HourlyWeatherDbModel {
        return HourlyWeatherDbModel(
            dt = hourlyWeatherDto.dt,
            temp = hourlyWeatherDto.temp.substring(0, 2) + "\u00B0",
            feelsLike = hourlyWeatherDto.feelsLike.substring(0, 2) + "\u00B0",
            pressure = hourlyWeatherDto.pressure,
            humidity = hourlyWeatherDto.humidity,
            windSpeed = hourlyWeatherDto.windSpeed,
            windGust = hourlyWeatherDto.windGust,
            windDeg = hourlyWeatherDto.windDeg,
            description = hourlyWeatherDto.weather[0].description.capitalize(),
            icon = hourlyWeatherDto.weather[0].icon,
            pop = hourlyWeatherDto.pop
        )
    }

    private fun mapDailyDtoToDailyDbModel(dailyWeatherDto: DailyWeatherDto): DailyWeatherDbModel {
        return DailyWeatherDbModel(
            id = 0,
            dt = formatForDailyWeather.format(dailyWeatherDto.dt.toLong() * 1000)
                .replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                },
            tempDay = dailyWeatherDto.temp.day.substring(0, 2) + "\u00B0",
            tempNight = dailyWeatherDto.temp.night.substring(0, 2) + "\u00B0",
            feelsLikeDay = dailyWeatherDto.feelsLike.day.substring(0, 2) + "\u00B0",
            feelsLikeNight = dailyWeatherDto.feelsLike.night.substring(0, 2) + "\u00B0",
            pressure = dailyWeatherDto.pressure,
            humidity = dailyWeatherDto.humidity,
            windSpeed = dailyWeatherDto.windSpeed,
            windGust = dailyWeatherDto.windGust,
            windDeg = dailyWeatherDto.windDeg,
            description = dailyWeatherDto.weather[0].description.capitalize(),
            icon = dailyWeatherDto.weather[0].icon,
            pop = dailyWeatherDto.pop
        )
    }

    fun mapDailyDtoListToDailyDaoList(list: List<DailyWeatherDto>) =
        list.map {
            mapDailyDtoToDailyDbModel(it)
        }

    private fun mapHourlyDtoListToHourlyDbModelList(list: List<HourlyWeatherDto>) =
        list.map {
            mapHourlyDtoToHourlyDbModel(it)
        }

////////////////////////////////////  DbModelToEntity  /////////////////////////////////////////////

    fun mapCurrentDbModelToCurrentEntity(currentWeatherDbModel: CurrentWeatherDbModel): CurrentWeatherEntity {
        return CurrentWeatherEntity(
            id = currentWeatherDbModel.id,
            dt = currentWeatherDbModel.dt,
            temp = currentWeatherDbModel.temp,
            feelsLike = currentWeatherDbModel.feelsLike,
            pressure = currentWeatherDbModel.pressure,
            humidity = currentWeatherDbModel.humidity,
            windSpeed = currentWeatherDbModel.windSpeed,
            windGust = currentWeatherDbModel.windGust,
            windDeg = currentWeatherDbModel.windDeg,
            description = currentWeatherDbModel.description,
            icon = currentWeatherDbModel.icon
        )
    }

    private fun mapHourlyDbModelToHourlyEntity(hourlyWeatherDbModel: HourlyWeatherDbModel): HourlyWeatherEntity {
        return HourlyWeatherEntity(
            dt = hourlyWeatherDbModel.dt,
            temp = hourlyWeatherDbModel.temp,
            feelsLike = hourlyWeatherDbModel.feelsLike,
            pressure = hourlyWeatherDbModel.pressure,
            humidity = hourlyWeatherDbModel.humidity,
            windSpeed = hourlyWeatherDbModel.windSpeed,
            windGust = hourlyWeatherDbModel.windGust,
            windDeg = hourlyWeatherDbModel.windDeg,
            description = hourlyWeatherDbModel.description,
            icon = hourlyWeatherDbModel.icon,
            pop = hourlyWeatherDbModel.pop
        )
    }

    private fun mapDailyDbModelToDailyEntity(dailyWeatherDbModel: DailyWeatherDbModel): DailyWeatherEntity {
        return DailyWeatherEntity(
            id = dailyWeatherDbModel.id,
            dt = dailyWeatherDbModel.dt,
            tempDay = dailyWeatherDbModel.tempDay,
            tempNight = dailyWeatherDbModel.tempNight,
            feelsLikeDay = dailyWeatherDbModel.feelsLikeDay,
            feelsLikeNight = dailyWeatherDbModel.feelsLikeNight,
            pressure = dailyWeatherDbModel.pressure,
            humidity = dailyWeatherDbModel.humidity,
            windSpeed = dailyWeatherDbModel.windSpeed,
            windGust = dailyWeatherDbModel.windGust,
            windDeg = dailyWeatherDbModel.windDeg,
            description = dailyWeatherDbModel.description,
            icon = dailyWeatherDbModel.icon,
            pop = dailyWeatherDbModel.pop
        )
    }

    fun mapDailyDbModelListToDailyEntityList(list: List<DailyWeatherDbModel>) =
        list.map {
            mapDailyDbModelToDailyEntity(it)
        }

    private fun mapHourlyDaoListToHourlyEntityList(list: List<HourlyWeatherDbModel>) =
        list.map {
            mapHourlyDbModelToHourlyEntity(it)
        }
}
package com.example.maria.homework_6

import com.google.gson.annotations.SerializedName


data class DailyWeather(
        @SerializedName("list")    val infoDailyWeatherList: List<DailyWeatherList>
)

data class DailyWeatherList(
        @SerializedName("dt")       val dt: Long,
        @SerializedName("main")     val main: Main,
        @SerializedName("weather")  val weather: List<Weather>,
        @SerializedName("wind")     val wind: Wind
)

data class Main(

        @SerializedName("temp")       val temp: Float,
        @SerializedName("temp_min")   val temp_min: Float,
        @SerializedName("temp_max")   val temp_max: Float,
        @SerializedName("sea_level")  val sea_level: Float,
        @SerializedName("grnd_level") val ground_level: Float,
        @SerializedName("pressure")   val pressure: Float,
        @SerializedName("humidity")   val humidity: Int
)

data class Weather(
        @SerializedName("main")        val weather_main: String,
        @SerializedName("description") val weather_description: String,
        @SerializedName("icon")        val weather_icon: String
)


data class Wind(
        @SerializedName("speed") val speed: Float
)
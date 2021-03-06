package com.bignerdranch.android.lifeboi.datamodel

data class DailyWeatherItem(
    var sunrise: String = "",
    var sunset: String = "",
    var temp: DailyTemp,
    var pressure: String = "",
    var humidity: String = "",
    var windspeed: String = "",
    var clouds: String = "",
    var visibility: String = "",
    var pop: String = "",
    var feels_like: FeelsLikeItem,
)

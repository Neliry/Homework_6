package com.example.maria.homework_6.adapters

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.maria.homework_6.DailyWeatherList
import com.example.maria.homework_6.R
import com.example.maria.homework_6.activities.DayWeatherDetail
import java.util.*


class RecyclerViewAdapter(var mDataSet: ArrayList<DailyWeatherList>, private val temperatureUnits: Boolean) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var temperatureUnits: Boolean = false
        var pressure: Float = 0f
        var humidity: Int = 0
        var temperature: Float = 0f
        var windSpeed: Float = 0f
        var dayFullDate: String = ""
        var image: String = ""
        val weatherType: TextView = v.findViewById<View>(R.id.weather_type) as TextView
        val weatherIcon: ImageView = v.findViewById<View>(R.id.weather_icon) as ImageView
        val weatherTemp: TextView = v.findViewById<View>(R.id.weather_temp_max) as TextView
        val dayDate: TextView = v.findViewById<View>(R.id.day_date) as TextView
        val dayOfWeek: TextView = v.findViewById<View>(R.id.day_of_week_date) as TextView
        val dayTime: TextView = v.findViewById<View>(R.id.day_time) as TextView

        init {
            v.setOnClickListener {
                Log.d("OkHttpHandler", "Element $adapterPosition clicked.")
                val intent = Intent(v.context, DayWeatherDetail::class.java)

                intent.putExtra("ImageName", image)
                intent.putExtra("Description", weatherType.text)
                intent.putExtra("Pressure", pressure)
                intent.putExtra("Humidity", humidity)
                intent.putExtra("Temperature", temperature)
                intent.putExtra("WindSpeed", windSpeed)
                intent.putExtra("FullDate", dayFullDate)
                intent.putExtra("TemperatureUnits", temperatureUnits)
                v.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.weather_list_item, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val res = viewHolder.itemView.context.resources.getIdentifier("icon_" + mDataSet[position].weather[0].weather_icon, "drawable", viewHolder.itemView.context.packageName)
        var tempMax: Float = mDataSet[position].main.temp
        if (!temperatureUnits)
            tempMax -= 273.15f
        var date = DateFormat.format("dd/MM", mDataSet[position].dt.times(1000)).toString()

        viewHolder.weatherType.text = mDataSet[position].weather[0].weather_description
        viewHolder.weatherTemp.text = tempMax.toInt().toString() + "°"
        viewHolder.dayDate.text = date
        date = DateFormat.format("EEE", mDataSet[position].dt.times(1000)).toString()
        viewHolder.dayOfWeek.text = date
        date = DateFormat.format("kk:mm", mDataSet[position].dt.times(1000)).toString()
        viewHolder.dayTime.text = date
        viewHolder.weatherIcon.setImageResource(res)

        viewHolder.pressure = mDataSet[position].main.pressure
        viewHolder.humidity = mDataSet[position].main.humidity
        viewHolder.temperature = mDataSet[position].main.temp
        viewHolder.temperature = mDataSet[position].main.temp
        viewHolder.windSpeed = mDataSet[position].wind.speed
        viewHolder.dayFullDate = DateFormat.format("dd.MM.yyyy kk:mm", mDataSet[position].dt.times(1000)).toString()
        viewHolder.temperatureUnits = temperatureUnits
        viewHolder.image = "icon_" + mDataSet[position].weather[0].weather_icon
    }

    override fun getItemCount(): Int {
        return mDataSet.size
    }
}
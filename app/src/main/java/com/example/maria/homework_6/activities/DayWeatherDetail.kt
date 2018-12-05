package com.example.maria.homework_6.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.maria.homework_6.R
import kotlinx.android.synthetic.main.activity_day_weather_detail.*

class DayWeatherDetail : AppCompatActivity() {

    private var temperatureUnits: Boolean = false
    private var temperature: Float = 0f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_day_weather_detail)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        temperatureUnits = intent.getBooleanExtra("TemperatureUnits", false)
    }

    override fun onStart() {
        super.onStart()

        val imageName = intent.getStringExtra("ImageName")
        val description = intent.getStringExtra("Description")
        val pressure = intent.getFloatExtra("Pressure", 0f)
        val humidity = intent.getIntExtra("Humidity", 0)
        val windSpeed = intent.getFloatExtra("WindSpeed", 0f)
        val fullDate = intent.getStringExtra("FullDate")
        temperature = intent.getFloatExtra("Temperature", 0f)

        val res = resources.getIdentifier(imageName, "drawable", packageName)
        date_view.text = fullDate
        if (!temperatureUnits)
            temperature -= 273.15f

        temperature_view.text = "Temperature: " + temperature.toInt().toString() + "°"

        description_view.text = "Description: " + description

        pressure_view.text = "Pressure: " + pressure.toString()
        humidity_view.text = "Humidity: " + humidity.toString()
        wind_speed_view.text = "Wind speed: " + windSpeed.toString()

        weather_icon_view.setImageResource(res)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_open_settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                intent.putExtra("TemperatureUnits", temperatureUnits)
                startActivityForResult(intent, 1)
                return true
            }
            android.R.id.home -> {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("TemperatureUnits", temperatureUnits)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data == null) {
            return
        }
        temperatureUnits = data.getBooleanExtra("TemperatureUnits", false)
        if (temperatureUnits)
            temperature += 273.15f
        temperature_view.text = "Temperature: " + temperature.toInt().toString()
    }
}

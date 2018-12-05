package com.example.maria.homework_6.activities

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.maria.homework_6.DailyWeather
import com.example.maria.homework_6.DailyWeatherList
import com.example.maria.homework_6.ListItemDecoration
import com.example.maria.homework_6.R
import com.example.maria.homework_6.adapters.RecyclerViewAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import okhttp3.Request


class MainActivity : AppCompatActivity() {

    lateinit var dailyWeatherAdapter: RecyclerViewAdapter
    private var temperatureUnits: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        connect_button.setOnClickListener {
            connectionCheck()
        }

        temperatureUnits = intent.getBooleanExtra("TemperatureUnits", false)
    }

    override fun onStart() {
        super.onStart()
        connectionCheck()
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
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data == null) {
            return
        }
        temperatureUnits = data.getBooleanExtra("TemperatureUnits", false)
    }

    private fun connectionCheck() {
        loading_indicator.visibility = View.VISIBLE
        val connMgr = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connMgr.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnected) {
            rv_days_list.visibility = View.GONE
            no_internet_connection.visibility = View.GONE
            rv_days_list.layoutManager = LinearLayoutManager(this)
            rv_days_list.addItemDecoration(ListItemDecoration(this))
            OkHttpHandler().execute("http://api.openweathermap.org/data/2.5/forecast?q=Cherkasy&mode=json&cnt=7&APPID=35ddaaf4d566ac6a42ca442b58fb66b2")
        } else {
            loading_indicator.visibility = View.GONE
            no_internet_connection.visibility = View.VISIBLE
        }
    }

    private inner class OkHttpHandler : AsyncTask<String, Void, String>() {

        override fun doInBackground(vararg params: String): String? {

            val client = OkHttpClient.Builder().build()

            val request = Request.Builder()
                    .url(params[0])
                    .build()
            try {
                val response = client.newCall(request).execute()
                return response.body()?.string()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            loading_indicator.visibility = View.GONE
            rv_days_list.visibility = View.VISIBLE
            val dailyWeatherArray = ArrayList<DailyWeatherList>()

            try {
                if (result != null && result.isNotEmpty()) {


                    val weather = Gson().fromJson(result, DailyWeather::class.java)
                    for (itemWeather in weather.infoDailyWeatherList) {
                        dailyWeatherArray.add(itemWeather)
                    }
                    dailyWeatherAdapter = RecyclerViewAdapter(dailyWeatherArray, temperatureUnits)
                    rv_days_list.adapter = dailyWeatherAdapter
                }
            } catch (e: Exception) {
            }

        }
    }
}

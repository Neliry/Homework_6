package com.example.maria.homework_6.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.ArrayAdapter
import com.example.maria.homework_6.R
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        var temperatureUnits = intent.getBooleanExtra("TemperatureUnits", false)

        val genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_temperature_units, android.R.layout.simple_spinner_item)
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        temperature_units.adapter = genderSpinnerAdapter

        when (temperatureUnits) {
            false -> temperature_units.setSelection(0)
            true -> temperature_units.setSelection(1)
        }

        save_setting_button.setOnClickListener {
            temperatureUnits = temperature_units.selectedItemPosition != 0
            val intent = intent
            intent.putExtra("TemperatureUnits", temperatureUnits)
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                this.finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}

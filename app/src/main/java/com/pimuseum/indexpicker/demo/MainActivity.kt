package com.pimuseum.indexpicker.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.pimuseum.indexpicker.compose.PMCityPickerActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        setContentView(R.layout.activity_main)
        btn.setOnClickListener {
            PMCityPickerActivity.citySelectForStart(this)
        }
    }
}

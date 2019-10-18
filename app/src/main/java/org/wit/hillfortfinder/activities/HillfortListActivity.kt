package org.wit.hillfortfinder.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.wit.hillfortfinder.R
import org.wit.hillfortfinder.main.MainApp

class HillfortListActivity: AppCompatActivity() {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort_list)
        app = application as MainApp
    }
}
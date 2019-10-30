package org.wit.hillfortfinder.activities

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_signup.*
import org.jetbrains.anko.startActivityForResult
import org.wit.hillfortfinder.R
import org.wit.hillfortfinder.main.MainApp

class SignupActivity: AppCompatActivity() {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        app = application as MainApp


        toolbarSignup.title = title
        setSupportActionBar(toolbarSignup)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        signup.setOnClickListener {
            startActivityForResult<HillfortListActivity>(0)
        }
    }
}
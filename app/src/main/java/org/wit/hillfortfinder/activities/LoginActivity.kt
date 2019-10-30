package org.wit.hillfortfinder.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.wit.hillfortfinder.R
import org.wit.hillfortfinder.main.MainApp
import org.jetbrains.anko.startActivityForResult

class LoginActivity: AppCompatActivity() {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        app = application as MainApp

        toolbarLogin.title = title
        setSupportActionBar(toolbarLogin)

        login.setOnClickListener {
            startActivityForResult<HillfortListActivity>(0)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_auth, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.item_signup -> startActivityForResult<SignupActivity>(0)
        }
        return super.onOptionsItemSelected(item)
    }
}
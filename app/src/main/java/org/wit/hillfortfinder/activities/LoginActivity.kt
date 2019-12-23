package org.wit.hillfortfinder.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.wit.hillfortfinder.R
import org.wit.hillfortfinder.main.MainApp
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast

class LoginActivity: AppCompatActivity() {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        app = application as MainApp

        toolbarLogin.title = "Login"
        setSupportActionBar(toolbarLogin)



        login.setOnClickListener {
            var email: String = loginEmail.text.toString()
            var password: String = loginPassword.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                var user = app.users.loign(email, password)
                if (user != null) {
                    app.currentUser = user
                    startActivityForResult<HillfortListView>(0)
                }
                else {
                    toast("Email or password entered was incorrect")
                }
            }
            else {
                toast("Email and password are required")
            }

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
package org.wit.hillfortfinder.activities

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_signup.*
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast
import org.wit.hillfortfinder.R
import org.wit.hillfortfinder.main.MainApp
import org.wit.hillfortfinder.models.UserModel

class SignupActivity: AppCompatActivity() {

    var user = UserModel()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        app = application as MainApp

        toolbarSignup.title = "Create Account"
        setSupportActionBar(toolbarSignup)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        signup.setOnClickListener {
            user.email = signupEmail.text.toString()
            user.password = signupPassword.text.toString()
            if (user.email.isNotEmpty() && user.password.isNotEmpty()) {
                if (app.users.signup(user.copy())) {
                    app.currentUser = user
                    startActivityForResult<HillfortListView>(0)
                }
                else {
                    toast("Email is already registered")
                }
            }
            else {
                toast("Email and password are required")
            }
        }
    }
}
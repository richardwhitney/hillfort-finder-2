package org.wit.hillfortfinder.views.signup

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_signup.*
import org.jetbrains.anko.toast
import org.wit.hillfortfinder.R

class SignupView: AppCompatActivity() {

    lateinit var presenter: SignupPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        presenter = SignupPresenter(this)

        toolbarSignup.title = "Create Account"
        setSupportActionBar(toolbarSignup)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        signup.setOnClickListener {
            var email = signupEmail.text.toString()
            var password = signupPassword.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                if (!presenter.doSignup(email, password)) {
                    toast("Email is already registerd")
                }
            }
            else {
                toast("Email and password are required")
            }
        }
    }
}
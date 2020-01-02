package org.wit.hillfortfinder.views.signup

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_signup.*
import org.jetbrains.anko.toast
import org.wit.hillfortfinder.R
import org.wit.hillfortfinder.views.BaseView

class SignupView: BaseView() {

    lateinit var presenter: SignupPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        presenter = initPresenter(SignupPresenter(this)) as SignupPresenter

        progressBarSignup.visibility = View.GONE

        toolbarSignup.title = "Create Account"
        super.init(toolbarSignup, true)

        signup.setOnClickListener {
            var email = signupEmail.text.toString()
            var password = signupPassword.text.toString()
            if (!email.isNotEmpty() && !password.isNotEmpty()) {
                toast("Email and password are required")
            }
            else {
                presenter.doSignup(email, password)
            }
        }
    }

    override fun showProgress() {
        progressBarSignup.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBarSignup.visibility = View.GONE
    }
}
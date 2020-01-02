package org.wit.hillfortfinder.views.login

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.wit.hillfortfinder.R
import org.jetbrains.anko.toast
import org.wit.hillfortfinder.views.BaseView

class LoginView: BaseView() {

    lateinit var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        init(toolbarLogin, false)

        presenter = initPresenter(LoginPresenter(this)) as LoginPresenter

        progressBarLogin.visibility = View.GONE

        login.setOnClickListener {
            var email: String = loginEmail.text.toString()
            var password: String = loginPassword.text.toString()
            if (!email.isNotEmpty() && !password.isNotEmpty()) {
                toast("Email and password are required")
            }
            else {
                presenter.doLogin(email, password)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_auth, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.item_signup -> presenter.showSignup()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showProgress() {
        progressBarLogin.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBarLogin.visibility = View.GONE
    }
}
package org.wit.hillfortfinder.views.login

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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
        setSupportActionBar(toolbarLogin)

        presenter = initPresenter(LoginPresenter(this)) as LoginPresenter

        login.setOnClickListener {
            var email: String = loginEmail.text.toString()
            var password: String = loginPassword.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                if(!presenter.doLogin(email, password)) {
                    toast("Email or password was incorrect")
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
            R.id.item_signup -> presenter.showSignup()
        }
        return super.onOptionsItemSelected(item)
    }
}
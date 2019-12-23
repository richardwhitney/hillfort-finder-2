package org.wit.hillfortfinder.activities

import org.jetbrains.anko.startActivityForResult
import org.wit.hillfortfinder.main.MainApp

class LoginPresenter(val view: LoginActivity) {

    var app: MainApp

    init {
        app = view.application as MainApp
    }

    fun doLogin(email: String, password: String): Boolean {
        var user = app.users.login(email, password)
        if (user != null) {
            app.currentUser = user
            view.startActivityForResult<HillfortListView>(0)
            return true
        }
        else {
            return false
        }
    }

    fun showSignup() {
        view.startActivityForResult<SignupActivity>(0)
    }
}
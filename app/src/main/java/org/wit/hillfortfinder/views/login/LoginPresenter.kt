package org.wit.hillfortfinder.views.login

import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast
import org.wit.hillfortfinder.views.signup.SignupView
import org.wit.hillfortfinder.main.MainApp
import org.wit.hillfortfinder.views.BasePresenter
import org.wit.hillfortfinder.views.BaseView
import org.wit.hillfortfinder.views.VIEW
import org.wit.hillfortfinder.views.hillfortlist.HillfortListView

class LoginPresenter(view: BaseView): BasePresenter(view) {

    fun doLogin(email: String, password: String) {
        view?.showProgress()
        app.auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(view!!) { task ->
            if (task.isSuccessful) {
                view?.navigateTo(VIEW.LIST)
            }
            else {
                view?.toast("Login Failed: ${task.exception?.message}")
            }
            view?.hideProgress()
        }
    }

    fun showSignup() {
        view?.navigateTo(VIEW.SIGNUP)
    }
}
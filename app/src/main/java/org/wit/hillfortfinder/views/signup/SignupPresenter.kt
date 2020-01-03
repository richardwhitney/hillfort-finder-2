package org.wit.hillfortfinder.views.signup

import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast
import org.wit.hillfortfinder.main.MainApp
import org.wit.hillfortfinder.models.UserModel
import org.wit.hillfortfinder.views.BasePresenter
import org.wit.hillfortfinder.views.BaseView
import org.wit.hillfortfinder.views.VIEW
import org.wit.hillfortfinder.views.hillfortlist.HillfortListView

class SignupPresenter(view: BaseView): BasePresenter(view) {

    fun doSignup(email: String, password: String) {
        view?.showProgress()
        app.auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(view!!) { task ->
            if (task.isSuccessful) {
                view?.navigateTo(VIEW.LIST)
            }
            else {
                view?.toast("Signup Failed: ${task.exception?.message}")
            }
            view?.hideProgress()
        }
    }
}
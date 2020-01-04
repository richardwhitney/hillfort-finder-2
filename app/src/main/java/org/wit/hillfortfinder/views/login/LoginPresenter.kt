package org.wit.hillfortfinder.views.login

import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast
import org.wit.hillfortfinder.views.signup.SignupView
import org.wit.hillfortfinder.main.MainApp
import org.wit.hillfortfinder.models.firebase.HillfortFireStore
import org.wit.hillfortfinder.views.BasePresenter
import org.wit.hillfortfinder.views.BaseView
import org.wit.hillfortfinder.views.VIEW
import org.wit.hillfortfinder.views.hillfortlist.HillfortListView

class LoginPresenter(view: BaseView): BasePresenter(view) {

    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var fireStore: HillfortFireStore? = null

    init {
      if (app.hillforts is HillfortFireStore) {
          fireStore = app.hillforts as HillfortFireStore
      }
    }

    fun doLogin(email: String, password: String) {
        view?.showProgress()
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(view!!) { task ->
            if (task.isSuccessful) {
                if (fireStore != null) {
                    fireStore!!.fetchHillforts {
                        view?.hideProgress()
                        view?.navigateTo(VIEW.LIST)
                    }
                }
                else {
                    view?.hideProgress()
                    view?.navigateTo(VIEW.LIST)
                }
            }
            else {
                view?.hideProgress()
                view?.toast("Login Failed: ${task.exception?.message}")
            }
        }
    }

    fun showSignup() {
        view?.navigateTo(VIEW.SIGNUP)
    }
}
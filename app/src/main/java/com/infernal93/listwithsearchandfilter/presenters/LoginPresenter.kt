package com.infernal93.listwithsearchandfilter.presenters

import android.os.Handler
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.google.firebase.auth.FirebaseAuth
import com.infernal93.listwithsearchandfilter.R
import com.infernal93.listwithsearchandfilter.views.LoginView

/**
 * Created by Armen Mkhitaryan on 04.12.2019.
 */

@InjectViewState
class LoginPresenter: MvpPresenter<LoginView>() {

    private lateinit var mAuth: FirebaseAuth

    // Test login functional
    fun loginTest(isSuccess: Boolean) {
        viewState.startLoading()
        Handler().postDelayed({
            viewState.endLoading()
            if (isSuccess) {
                viewState.openCategory()
            } else {
                viewState.showError(textResource = R.string.login_error)
            }
        }, 500)
    }

    // Real login functional
    fun login(email: String, password: String) {

        if (email.isNotEmpty() && password.isNotEmpty()){
            mAuth = FirebaseAuth.getInstance()
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    viewState.startLoading()
                    Handler().postDelayed({
                        viewState.endLoading()
                        viewState.openCategory()
                    },500)
                } else {
                    viewState.showError(textResource = R.string.login_error)
                }
            }
        } else {
            viewState.showError(textResource = R.string.login_or_password_empty)
        }
    }

}
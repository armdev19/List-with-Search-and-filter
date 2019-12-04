package com.infernal93.listwithsearchandfilter.presenters

import android.os.Handler
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.infernal93.listwithsearchandfilter.R
import com.infernal93.listwithsearchandfilter.views.LoginView

/**
 * Created by Armen Mkhitaryan on 04.12.2019.
 */

@InjectViewState
class LoginPresenter: MvpPresenter<LoginView>() {

    fun login(isSuccess: Boolean) {
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
}
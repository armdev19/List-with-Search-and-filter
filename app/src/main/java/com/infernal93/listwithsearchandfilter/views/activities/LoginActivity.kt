package com.infernal93.listwithsearchandfilter.views.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.infernal93.listwithsearchandfilter.R
import com.infernal93.listwithsearchandfilter.presenters.LoginPresenter
import com.infernal93.listwithsearchandfilter.views.LoginView
import kotlinx.android.synthetic.main.activity_login.*
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener

class LoginActivity : MvpAppCompatActivity(), LoginView, KeyboardVisibilityEventListener {
    private val TAG = "LoginActivity"

    @InjectPresenter
    lateinit var loginPresenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Show email and password
        btn_registration.setOnClickListener {
            Toast.makeText(this@LoginActivity, R.string.login_and_password, Toast.LENGTH_LONG).show()
        }

        // Keyboard visibility
        KeyboardVisibilityEvent.setEventListener(this, this)

        // Test Log In
//        btn_login_enter.setOnClickListener {
//            loginPresenter.loginTest(isSuccess = true)
//        }

        // Real Log In
        btn_login_enter.setOnClickListener {
            loginPresenter.login(email = login_email.text.toString(), password = login_password.text.toString())
        }
    }

    override fun startLoading() {
        btn_login_enter.visibility = View.GONE
        cpv_login.visibility = View.VISIBLE
    }

    override fun endLoading() {
        btn_login_enter.visibility = View.VISIBLE
        cpv_login.visibility = View.GONE
    }

    override fun showError(textResource: Int) {
        Toast.makeText(applicationContext, getString(textResource), Toast.LENGTH_SHORT).show()
    }

    override fun openCategory() {
        startActivity(Intent(applicationContext, CategoryActivity::class.java))
    }

    override fun onVisibilityChanged(isKeyboardOpen: Boolean) {
        if (isKeyboardOpen) {
            scroll_view.scrollTo(0, scroll_view.bottom)
        } else {
            scroll_view.scrollTo(0, scroll_view.top)
        }
    }
}

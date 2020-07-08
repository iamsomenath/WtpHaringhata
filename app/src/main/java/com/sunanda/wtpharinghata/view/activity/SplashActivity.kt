package com.sunanda.wtpharinghata.view.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.sunanda.wtpharinghata.R
import com.sunanda.wtpharinghata.helper.SessionManager

class SplashActivity : AppCompatActivity() {

    internal lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash)

        init()
    }

    private fun init() {

        sessionManager = SessionManager(this)

        val SPLASH_TIME_OUT = 3000
        Handler().postDelayed({ NextTask() }, SPLASH_TIME_OUT.toLong())
    }

    private fun NextTask() {

        if (sessionManager.isLoggedIn) {
                    startActivity(Intent(this@SplashActivity, WelcomeActivity::class.java))
                    overridePendingTransition(
                        R.anim.left_in,
                        R.anim.right_out
                    )
                    finish()
                } else {
                    startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                    overridePendingTransition(
                        R.anim.left_in,
                        R.anim.right_out
                    )
                    finish()
                }
    }
}

package com.sunanda.wtpharinghata.view

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.Window
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.sunanda.wtpharinghata.*
import com.sunanda.wtpharinghata.helper.LoadingDialog
import com.sunanda.wtpharinghata.helper.NetworkChangeReceiver
import com.sunanda.wtpharinghata.helper.NetworkConnection
import com.sunanda.wtpharinghata.helper.SessionManager

class LoginActivity : AppCompatActivity() {

    internal lateinit var networkChangeReceiver: NetworkChangeReceiver
    internal var network: Boolean? = false
    internal lateinit var uname: EditText
    internal lateinit var login_password: EditText
    private var loadingDialog: LoadingDialog? = null
    internal lateinit var sessionManager: SessionManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
    }

    private fun init() {

        sessionManager = SessionManager(this)
        loadingDialog = LoadingDialog(this)

        networkChangeReceiver = NetworkChangeReceiver(this)
        network = networkChangeReceiver.isNetworkAvailable

        uname = findViewById(R.id.email)
        login_password = findViewById(R.id.password)

        if (!TextUtils.isEmpty(sessionManager.dName) && !sessionManager.dName!!.equals("dName", ignoreCase = true)) {
            uname.setText(sessionManager.dName)
            login_password.requestFocus()
        }

        findViewById<View>(R.id.btnLogin).setOnClickListener {
            if (network!!) {

                val UNAME = uname.text.toString().trim { it <= ' ' }
                val password = login_password.text.toString().trim { it <= ' ' }
                if (UNAME.isEmpty()) {
                    val shake = AnimationUtils.loadAnimation(this@LoginActivity, R.anim.shake)
                    uname.startAnimation(shake)
                    uname.error = "Please Enter User Name"
                    uname.requestFocus()
                } else if (password.isEmpty()) {

                    val shake = AnimationUtils.loadAnimation(this@LoginActivity, R.anim.shake)
                    login_password.startAnimation(shake)
                    login_password.error = "Please Enter Password"
                    login_password.requestFocus()
                } else {
                    val flag: Boolean = UNAME == "haringhatawtp" && password == "password"
                    if(flag) {
                        sessionManager.setLogin(true)
                        showDialog()
                    }
                    else
                        ShowDialog2()
                }
            } else {
                startActivity(Intent(this@LoginActivity, NetworkConnection::class.java))
                overridePendingTransition(
                    R.anim.left_enter,
                    R.anim.right_out
                )
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.left_in, R.anim.right_out)
        finish()
    }

    private fun showDialog() {

        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        dialog.setContentView(R.layout.custom_dialog)
        dialog.setCancelable(false)

        val restart = dialog.findViewById<View>(R.id.restart) as Button

        restart.setOnClickListener {
            dialog.dismiss()
            startActivity(Intent(this@LoginActivity, WelcomeActivity::class.java))
            overridePendingTransition(
                R.anim.left_in,
                R.anim.right_out
            )
            finish()
        }
        dialog.show()
    }

    private fun ShowDialog2() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        dialog.setContentView(R.layout.custom_dialog3)
        dialog.setCancelable(false)

        val btn_dialog = dialog.findViewById<View>(R.id.btn_dialog) as Button

        btn_dialog.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }
}

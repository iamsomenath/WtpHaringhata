package com.sunanda.wtpharinghata

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NetworkConnection : AppCompatActivity() {

    internal lateinit var retry: FloatingActionButton
    internal var network: Boolean? = false
    internal lateinit var networkChangeReceiver: NetworkChangeReceiver

    override fun onCreate(savedInstanceState: Bundle?) {

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_network_connection)

        networkChangeReceiver = NetworkChangeReceiver(this)

        retry = findViewById(R.id.fab_retryConnection)
        retry.setOnClickListener {
            network = networkChangeReceiver.isNetworkAvailable
            if (network!!) {
                finish()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}

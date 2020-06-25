package com.sunanda.wtpharinghata.view

import android.graphics.Typeface
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import com.sunanda.wtpharinghata.R
import com.sunanda.wtpharinghata.adapter.MainActivityAdapter
import com.sunanda.wtpharinghata.database.RowTable


class MainActivity : AppCompatActivity() {

    var tabLayout: TabLayout? = null
    var viewPager: ViewPager? = null
    var myRow = RowTable()
    var flag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = ""
        val toolbar_title = findViewById<View>(R.id.title_txt) as TextView
        toolbar_title.typeface = Typeface.createFromAsset(assets, "proxima_nova_light.ttf")
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.left_arrow)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        toolbar_title.text = "WATER SOURCES"

        if (!intent.hasExtra("CDATE")) {
            val gson = Gson()
            myRow = gson.fromJson(intent.getStringExtra("ROW"), RowTable::class.java)
        } else
            flag = true

        init()
    }

    private fun init() {
        tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        viewPager = findViewById<ViewPager>(R.id.viewPager)

        tabLayout!!.addTab(tabLayout!!.newTab().setText("RAW WATER"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("CLEAR WATER"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("TREATED WATER"))
        tabLayout!!.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = MainActivityAdapter(this, supportFragmentManager, tabLayout!!.tabCount)
        viewPager!!.adapter = adapter

        viewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager!!.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })

        when {
            flag -> viewPager!!.currentItem = 0
            TextUtils.isEmpty(myRow.raw) -> viewPager!!.currentItem = 1
            else -> viewPager!!.currentItem = 0
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            showDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        showDialog()
    }

    private fun showDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Do you want to go Back?")
        builder.setMessage("It may loose your filled information!")
        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            dialog.dismiss()
            super.onBackPressed()
            overridePendingTransition(R.anim.right_in, R.anim.left_out)
            finish()
        }
        /*builder.setNegativeButton("Maybe") { dialog, which ->
            Toast.makeText(applicationContext,
                android.R.string.no, Toast.LENGTH_SHORT).show()
        }*/
        builder.setNeutralButton(android.R.string.no) { dialog, which ->
            dialog.dismiss()
        }
        builder.show()
        builder.setCancelable(false)
    }
}

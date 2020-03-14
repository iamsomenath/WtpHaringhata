package com.sunanda.wtpharinghata.view

import android.graphics.Typeface
import android.os.AsyncTask
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sunanda.wtpharinghata.R
import com.sunanda.wtpharinghata.adapter.TasksAdapter
import com.sunanda.wtpharinghata.database.DatabaseClient
import com.sunanda.wtpharinghata.database.RowTable
import com.sunanda.wtpharinghata.database.Task
import java.util.*
import kotlin.collections.ArrayList

class TaskListActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var adapter : TasksAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)

        recyclerView = findViewById(R.id.recyclerview_tasks)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = ""
        val toolbar_title = findViewById<View>(R.id.title_txt) as TextView
        toolbar_title.typeface = Typeface.createFromAsset(assets, "proxima_nova_light.ttf")
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.left_arrow)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        toolbar_title.text = "Saved Lists"
    }

    override fun onResume() {
        super.onResume()
        getRowTable()
    }

    private fun getRowTable() {

        class GetRowTable : AsyncTask<Void, Void, List<RowTable>>() {

            override fun doInBackground(vararg voids: Void): List<RowTable> {
                return DatabaseClient.getInstance(applicationContext)
                    .appDatabase
                    .rowTableDao()
                    .getAllRowData
            }

            override fun onPostExecute(rowTable : List<RowTable>) {
                super.onPostExecute(rowTable)
                adapter = TasksAdapter(this@TaskListActivity, rowTable as ArrayList<RowTable>)
                recyclerView.adapter = adapter

                if (rowTable.isEmpty()) {

                    val builder = AlertDialog.Builder(this@TaskListActivity)
                    builder.setMessage("⚠ You don't have any saved data!")
                    builder.setPositiveButton("OK"){dialogInterface, which ->
                        dialogInterface.dismiss()
                        overridePendingTransition(R.anim.right_in, R.anim.left_out)
                        finish()
                    }
                    val alertDialog: AlertDialog = builder.create()
                    alertDialog.setCancelable(false)
                    alertDialog.show()
                }
            }
        }

        val gt = GetRowTable()
        gt.execute()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            super.onBackPressed()
            overridePendingTransition(R.anim.right_in, R.anim.left_out)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.right_in, R.anim.left_out)
        finish()
    }
}

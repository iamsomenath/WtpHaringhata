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
import com.sunanda.wtpharinghata.database.Task

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
        getTasks()
    }

    private fun getTasks() {

        class GetTasks : AsyncTask<Void, Void, List<Task>>() {

            override fun doInBackground(vararg voids: Void): List<Task> {
                return DatabaseClient.getInstance(applicationContext)
                    .appDatabase
                    .taskDao()
                    .getAllData
            }

            override fun onPostExecute(tasks: List<Task>) {
                super.onPostExecute(tasks)
                adapter = TasksAdapter(this@TaskListActivity, tasks as ArrayList<Task>)
                recyclerView.adapter = adapter

                if (tasks.isEmpty()) {

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

        val gt = GetTasks()
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

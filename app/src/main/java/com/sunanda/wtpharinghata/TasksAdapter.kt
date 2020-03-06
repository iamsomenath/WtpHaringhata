package com.sunanda.wtpharinghata

import android.app.Activity
import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson


class TasksAdapter(private val mCtx: Context, private val taskList: ArrayList<Task>) :

    RecyclerView.Adapter<TasksAdapter.TasksViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        val view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_tasks, parent, false)
        return TasksViewHolder(view)
    }

    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {

        val t = taskList[position]
        holder.textViewTask.text = t.wtpname
        holder.textViewDesc.text = t.entrydate
        holder.textViewFinishBy.text = t.type
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    inner class TasksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var textViewTask: TextView
        var textViewDesc: TextView
        var textViewFinishBy: TextView

        var remove: Button
        var edit: Button
        var upload: Button

        init {
            textViewTask = itemView.findViewById(R.id.textViewTask)
            textViewDesc = itemView.findViewById(R.id.textViewDesc)
            textViewFinishBy = itemView.findViewById(R.id.textViewFinishBy)

            remove = itemView.findViewById(R.id.remove)
            edit = itemView.findViewById(R.id.edit)
            upload = itemView.findViewById(R.id.upload)

            remove.setOnClickListener(this)
            edit.setOnClickListener(this)
            upload.setOnClickListener(this)

            //itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {

            val task = taskList[adapterPosition]

            if (view.id == R.id.remove) {

                val builder = AlertDialog.Builder(mCtx)
                builder.setMessage("Are you sure to Remove?")
                builder.setPositiveButton(
                    "Yes"
                ) { dialogInterface, i -> deleteTask(task, adapterPosition) }
                builder.setNegativeButton("No") { dialogInterface, i -> }

                val ad = builder.create()
                ad.setCancelable(false)
                ad.show()

            } else if (view.id == R.id.edit) {

                EditTask(task)

            } else {

                val builder = AlertDialog.Builder(mCtx)
                builder.setMessage("Are you sure to Upload?")
                builder.setPositiveButton(
                    "Yes"
                ) { dialogInterface, i -> uploadTask(task, adapterPosition) }
                builder.setNegativeButton("No") { dialogInterface, i -> }

                val ad = builder.create()
                ad.setCancelable(false)
                ad.show()
            }
        }
    }

    private fun EditTask(task: Task) {
        val activity = mCtx as Activity
        val gson = Gson()
        val json = gson.toJson(task)
        //Toast.makeText(mCtx, "Edit$json", Toast.LENGTH_LONG).show()
        val intent = Intent(mCtx, MainActivity::class.java)
        intent.putExtra("TASK", json)
        mCtx.startActivity(intent)
        activity.overridePendingTransition(R.anim.left_enter, R.anim.right_out)
    }

    private fun deleteTask(task: Task, adapterPosition: Int) {
        class DeleteTask : AsyncTask<Void, Void, Void>() {

            override fun doInBackground(vararg voids: Void): Void? {
                DatabaseClient.getInstance(mCtx).appDatabase
                    .taskDao()
                    .delete(task)
                return null
            }

            override fun onPostExecute(aVoid: Void?) {
                super.onPostExecute(aVoid)
                Toast.makeText(mCtx, "Item deleted successfully", Toast.LENGTH_LONG).show()

                notifyItemRemoved(adapterPosition)
                taskList.removeAt(adapterPosition)
                notifyItemRangeChanged(adapterPosition, taskList.size)
            }
        }

        val dt = DeleteTask()
        dt.execute()
    }

    private fun uploadTask(task: Task, adapterPosition: Int) {

        val gson = Gson()
        val json = gson.toJson(task)
        Toast.makeText(mCtx, "Uploaded$json", Toast.LENGTH_LONG).show()
        Log.d("TEST", "Uploaded$json")

        //notifyItemRemoved(adapterPosition)
        //taskList.removeAt(adapterPosition)
        //notifyItemRangeChanged(adapterPosition, taskList.size)
    }
}
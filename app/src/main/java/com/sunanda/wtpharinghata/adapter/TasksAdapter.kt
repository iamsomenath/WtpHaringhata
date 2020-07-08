package com.sunanda.wtpharinghata.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.sunanda.wtpharinghata.R
import com.sunanda.wtpharinghata.database.DatabaseClient
import com.sunanda.wtpharinghata.database.RowTable
import com.sunanda.wtpharinghata.rest.APIDataService
import com.sunanda.wtpharinghata.helper.LoadingDialog
import com.sunanda.wtpharinghata.helper.RetrofitInstance
import com.sunanda.wtpharinghata.helper.SessionManager
import com.sunanda.wtpharinghata.view.activity.MainActivity
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TasksAdapter(private val mCtx: Context, private val rowList: ArrayList<RowTable>) :

    RecyclerView.Adapter<TasksAdapter.TasksViewHolder>() {

    var sessionManager : SessionManager = SessionManager(mCtx)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        val view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_tasks, parent, false)
        return TasksViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {

        val t = rowList[position]
        holder.textViewTask.text = t.wtp_name
        holder.textViewDesc.text = t.rdate + " " + t.rtime
        if (TextUtils.isEmpty(t.raw) && TextUtils.isEmpty(t.clear))
            holder.textViewType.text = "TREATED WATER"
        else if (TextUtils.isEmpty(t.treated) && TextUtils.isEmpty(t.clear))
            holder.textViewType.text = "RAW WATER"
        else if (TextUtils.isEmpty(t.treated) && TextUtils.isEmpty(t.raw))
            holder.textViewType.text = "CLEAR WATER"
        else if (TextUtils.isEmpty(t.raw))
            holder.textViewType.text = "CLEAR WATER, TREATED WATER"
        else if (TextUtils.isEmpty(t.treated))
            holder.textViewType.text = "RAW WATER, CLEAR WATER"
        else if (TextUtils.isEmpty(t.clear))
            holder.textViewType.text = "RAW WATER, TREATED WATER"
        else
            holder.textViewType.text = "RAW WATER, CLEAR WATER, TREATED WATER"
    }

    override fun getItemCount(): Int {
        return rowList.size
    }

    inner class TasksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var mailLayout: RelativeLayout
        var textViewTask: TextView
        var textViewDesc: TextView
        var textViewType: TextView

        var remove: Button
        var edit: Button
        var upload: Button

        init {
            mailLayout = itemView.findViewById(R.id.mailLayout)
            textViewTask = itemView.findViewById(R.id.textViewTask)
            textViewDesc = itemView.findViewById(R.id.textViewDesc)
            textViewType = itemView.findViewById(R.id.textViewFinishBy)

            remove = itemView.findViewById(R.id.remove)
            edit = itemView.findViewById(R.id.edit)
            upload = itemView.findViewById(R.id.upload)

            remove.setOnClickListener(this)
            edit.setOnClickListener(this)
            upload.setOnClickListener(this)

            //itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {

            val row = rowList[adapterPosition]

            when {
                view.id == R.id.remove -> {

                    val builder = AlertDialog.Builder(mCtx)
                    builder.setMessage("Are you sure to Remove?")
                    builder.setPositiveButton(
                        "Yes"
                    ) { dialogInterface, i -> deleteRow(row, adapterPosition) }
                    builder.setNegativeButton("No") { dialogInterface, i -> }

                    val ad = builder.create()
                    ad.setCancelable(false)
                    ad.show()

                }
                view.id == R.id.edit -> EditRow(row)
                else -> {

                    val builder = AlertDialog.Builder(mCtx)
                    builder.setMessage("Are you sure to Upload?")
                    builder.setPositiveButton(
                        "Yes"
                    ) { dialogInterface, i ->

                        /*if (!TextUtils.isEmpty(row.raw)) {
                            UploadRow(row, adapterPosition)
                        }
                        if (!TextUtils.isEmpty(row.treated)) {
                            UploadRowTREATED(row, adapterPosition)
                        }
                        if (!TextUtils.isEmpty(row.clear)) {
                            UploadRowCLEAR(row, adapterPosition)
                        }*/
                        UploadRow(row, adapterPosition)
                    }
                    builder.setNegativeButton("No") { dialogInterface, i -> }

                    val ad = builder.create()
                    ad.setCancelable(false)
                    ad.show()
                }
            }
        }
    }

    private fun EditRow(row: RowTable) {

        val activity = mCtx as Activity
        val gson = Gson()
        val json = gson.toJson(row)
        //Toast.makeText(mCtx, "Edit$json", Toast.LENGTH_LONG).show()
        val intent = Intent(mCtx, MainActivity::class.java)
        intent.putExtra("ROW", json)
        mCtx.startActivity(intent)
        activity.overridePendingTransition(
            R.anim.left_enter,
            R.anim.right_out
        )
    }

    private fun deleteRow(row: RowTable, adapterPosition: Int) {

        class DeleteRow : AsyncTask<Void, Void, Void>() {

            override fun doInBackground(vararg voids: Void): Void? {
                DatabaseClient.getInstance(mCtx).appDatabase
                    .rowTableDao()
                    .delete(row)
                return null
            }

            override fun onPostExecute(aVoid: Void?) {
                super.onPostExecute(aVoid)
                //Toast.makeText(mCtx, "Item deleted successfully", Toast.LENGTH_LONG).show()
                val tempList = sessionManager.getArrayList()
                tempList.remove(row.sid)
                sessionManager.saveArrayList(tempList)
                notifyItemRemoved(adapterPosition)
                rowList.removeAt(adapterPosition)
                notifyItemRangeChanged(adapterPosition, rowList.size)
                if (rowList.size == 0) {
                    val activity = mCtx as Activity
                    activity.overridePendingTransition(
                        R.anim.left_enter,
                        R.anim.right_out
                    )
                    activity.finish()
                }
            }
        }

        val dt = DeleteRow()
        dt.execute()
    }

    private fun UploadRow(row: RowTable, adapterPosition: Int) {

        val loadingDialog = LoadingDialog(mCtx)
        loadingDialog.showDialog()

        val service = RetrofitInstance.retrofitInstance3.create(APIDataService::class.java)
        val call = service.postData_NewParameter(Gson().toJson(row).toString())

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                loadingDialog.hideDialog()

                try {
                    val jsonObject = JSONObject(response.body()!!.string())
                    if (jsonObject.getBoolean("response")) {
                        Toast.makeText(mCtx, "Data uploaded successfully!", Toast.LENGTH_SHORT).show()
                        if (TextUtils.isEmpty(row.treated)) {
                            deleteRow(row, adapterPosition)
                        }
                    } else {
                        Toast.makeText(mCtx, "Unable to upload data. Please try again!", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: JSONException) {
                    Toast.makeText(mCtx, "Unable to upload data! Something went wrong!", Toast.LENGTH_SHORT).show()
                    //e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                loadingDialog.hideDialog()
                Toast.makeText(
                    mCtx, "It seems that your device don't or low network connection to upload data!!",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    private fun UploadRowTREATED(row: RowTable, adapterPosition: Int) {

        val loadingDialog = LoadingDialog(mCtx)
        loadingDialog.showDialog()

        val service = RetrofitInstance.retrofitInstance3.create(APIDataService::class.java)
        val call = service.postData_NewParameter(row.treated!!)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                loadingDialog.hideDialog()

                try {
                    val jsonObject = JSONObject(response.body()!!.string())
                    if (jsonObject.getBoolean("response")) {
                        Toast.makeText(mCtx, "Data uploaded successfully!", Toast.LENGTH_SHORT).show()
                        deleteRow(row, adapterPosition)
                    } else {
                        Toast.makeText(mCtx, "Unable to upload data. Please try again!", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(mCtx, "Unable to upload data! Something went wrong!", Toast.LENGTH_SHORT).show()
                    //e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                loadingDialog.hideDialog()
                Toast.makeText(
                    mCtx,
                    "It seems that your device don't or low network connection to upload data!!",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }


    private fun UploadRowCLEAR(row: RowTable, adapterPosition: Int) {

        val loadingDialog = LoadingDialog(mCtx)
        loadingDialog.showDialog()

        val service = RetrofitInstance.retrofitInstance3.create(APIDataService::class.java)
        //val call = service.postData_NewParameter(row.clear!!)
        val call = service.postData_NewParameter(row.clear!!)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                loadingDialog.hideDialog()

                try {
                    val jsonObject = JSONObject(response.body()!!.string())
                    if (jsonObject.getBoolean("response")) {
                        Toast.makeText(mCtx, "Data uploaded successfully!", Toast.LENGTH_SHORT).show()
                        deleteRow(row, adapterPosition)
                    } else {
                        Toast.makeText(mCtx, "Unable to upload data. Please try again!", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(mCtx, "Unable to upload data! Something went wrong!", Toast.LENGTH_SHORT).show()
                    //e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                loadingDialog.hideDialog()
                Toast.makeText(
                    mCtx,
                    "It seems that your device don't or low network connection to upload data!!",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }
}
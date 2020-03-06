package com.sunanda.wtpharinghata

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.sunanda.wtp_app.util.SessionManager
import kotlinx.android.synthetic.main.activity_welcome.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class WelcomeActivity : AppCompatActivity() {

    internal lateinit var name: TextView
    internal lateinit var email: TextView
    internal lateinit var sessionManager: SessionManager
    private var JSON: String? = null
    var arrayList_details: ArrayList<String> = ArrayList()
    var spChoiceStr = ""
    var selected_date = ""
    var myCalendar = Calendar.getInstance()

    private fun initValue() {

        sessionManager = SessionManager(this)

        name = findViewById(R.id.name)
        email = findViewById(R.id.email)

        name.text = "HARINGHATA"
        email.text = sessionManager.dCode


        findViewById<View>(R.id.logout).setOnClickListener {
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.exit_dialog)
            val btn_cancel = dialog.findViewById<View>(R.id.btn_cancel) as Button
            val btn_okay = dialog.findViewById<View>(R.id.btn_okay) as Button
            btn_cancel.setOnClickListener {
                dialog.dismiss()
            }
            btn_okay.setOnClickListener {
                sessionManager.destroyLoginSession()
                finish()
            }
            dialog.show()
            dialog.setCancelable(false)
        }

        findViewById<View>(R.id.upload).setOnClickListener {
            startActivity(Intent(this@WelcomeActivity, TaskListActivity::class.java))
            overridePendingTransition(R.anim.left_enter, R.anim.right_out)
        }

        findViewById<View>(R.id.submit).setOnClickListener {
            if (spChoiceStr.equals("Select WTP", false)) {
                showMessage("PLease Select WTP", name)
                return@setOnClickListener
            }
            if (dob1.text == "DD/MM/YYYY") {
                showMessage("PLease Select Date", name)
                return@setOnClickListener
            }
            val intent = Intent(this@WelcomeActivity, MainActivity::class.java)
            intent.putExtra("DATE", selected_date)
            intent.putExtra("WTP", spChoiceStr)
            startActivity(intent)
            overridePendingTransition(R.anim.left_enter, R.anim.right_out)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        initValue()
        JSON = loadJSONFromAsset()
        val json_data = JSONArray(JSON)
        val size: Int = json_data.length()
        arrayList_details = ArrayList()

        arrayList_details.add("Select WTP")
        for (i in 0 until size) {
            val json_objectdetail: JSONObject = json_data.getJSONObject(i)
            var str: String
            str = json_objectdetail.getString("WTP_Name")
            arrayList_details.add(str)
        }
        selectWTP()


        val date = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            myCalendar = Calendar.getInstance()
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "dd/MM/yyyy"
            val sdf = SimpleDateFormat(myFormat, Locale.US)

            dob1.text = sdf.format(myCalendar.time)
            selected_date = dob1.text as String
        }
        dob1.setOnClickListener {
            myCalendar = Calendar.getInstance()
            val dialog = DatePickerDialog(
                this, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            )
            dialog.datePicker.maxDate = Date().time
            dialog.show()
        }
    }

    private fun showMessage(str: String, view: View) {
        //val parentLayout = findViewById(android.R.id.content)
        val snackbar = Snackbar.make(view, str, Snackbar.LENGTH_LONG)
        snackbar.setActionTextColor(Color.RED)
        val sbView = snackbar.view
        val textView = sbView.findViewById(R.id.snackbar_text) as TextView
        textView.setTextColor(Color.parseColor("#FFFFFF"))
        textView.typeface = Typeface.createFromAsset(assets, "proxima_nova_light.ttf")
        textView.textSize = 17f
        textView.gravity = Gravity.CENTER_VERTICAL
        snackbar.show()
    }

    fun loadJSONFromAsset(): String {
        val json: String?
        try {
            val `is` = assets.open("Data.json")
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            json = String(buffer)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null.toString()
        }
        //Log.d("TEST", json);
        return "$json"
    }

    private fun selectWTP() {

        val adap2 = ArrayAdapter(
            this,
            R.layout.new_spinner_item, arrayList_details
        )
        spChoice.adapter = adap2 as SpinnerAdapter?

        spChoice.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                spChoiceStr = spChoice.selectedItem.toString()
                spChoice.setSelection(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }

    override fun onBackPressed() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.custom_dialog2)
        val btn_no = dialog.findViewById<View>(R.id.btn_no) as Button
        val btn_yes = dialog.findViewById<View>(R.id.btn_yes) as Button
        btn_no.setOnClickListener {
            dialog.dismiss()
        }
        btn_yes.setOnClickListener {
            finish()
        }
        dialog.show()
        dialog.setCancelable(false)
    }
}
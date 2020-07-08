package com.sunanda.wtpharinghata.view.activity

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.sunanda.wtpharinghata.R
import com.sunanda.wtpharinghata.databinding.ActivityWelcomeBinding
import com.sunanda.wtpharinghata.helper.LoadingDialog
import com.sunanda.wtpharinghata.helper.SessionManager
import com.sunanda.wtpharinghata.model.District
import com.sunanda.wtpharinghata.model.WTP_Pojo
import com.sunanda.wtpharinghata.viewmodel.CallbackListenerMain
import com.sunanda.wtpharinghata.viewmodel.ViewModelMain
import kotlinx.android.synthetic.main.activity_welcome.*
import org.json.JSONObject
import java.io.IOException
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class WelcomeActivity : AppCompatActivity(), CallbackListenerMain {

    lateinit var loadingDialog: LoadingDialog
    internal lateinit var name: TextView
    internal lateinit var email: TextView
    internal lateinit var sessionManager: SessionManager
    var arrayListDistrict: ArrayList<District> = ArrayList()
    var arrayListDistrictCode: ArrayList<String> = ArrayList()
    var arrayListDistrictName: ArrayList<String> = ArrayList()
    var arrayListWTP: ArrayList<WTP_Pojo> = ArrayList()
    var arrayListWTPCode: ArrayList<String> = ArrayList()
    var arrayListWTPName: ArrayList<String> = ArrayList()
    lateinit var selectedWTP: WTP_Pojo
    var spChoiceStr = ""
    var spChoiceID = ""
    var spDistrictStr = ""
    var spDistrictID = ""
    var collect_selected_date = ""
    var receive_selected_date = ""
    var test_selected_date = ""
    var formattedTime = ""
    var sampleID = ""
    var myCalendar = Calendar.getInstance()

    lateinit var viewModel: ViewModelMain

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityWelcomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_welcome)
        viewModel = ViewModelProviders.of(this, null).get(ViewModelMain::class.java)
        binding.welcomeviewmodel = viewModel
        viewModel.authListener = this

        initValue()

        val date = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            myCalendar = Calendar.getInstance()
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "dd/MM/yyyy"
            val sdf = SimpleDateFormat(myFormat, Locale.US)

            dob1.text = sdf.format(myCalendar.time)
            collect_selected_date = dob1.text as String
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
        val date2 = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            myCalendar = Calendar.getInstance()
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "dd/MM/yyyy"
            val sdf = SimpleDateFormat(myFormat, Locale.US)

            dob2.text = sdf.format(myCalendar.time)
            receive_selected_date = dob2.text as String
        }
        dob2.setOnClickListener {
            myCalendar = Calendar.getInstance()
            val dialog = DatePickerDialog(
                this, date2, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            )
            dialog.datePicker.maxDate = Date().time
            dialog.show()
        }
        val date3 = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            myCalendar = Calendar.getInstance()
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "dd/MM/yyyy"
            val sdf = SimpleDateFormat(myFormat, Locale.US)

            dob3.text = sdf.format(myCalendar.time)
            test_selected_date = dob3.text as String
        }
        dob3.setOnClickListener {
            myCalendar = Calendar.getInstance()
            val dialog = DatePickerDialog(
                this, date3, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            )
            dialog.datePicker.maxDate = Date().time
            dialog.show()
        }
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    private fun initValue() {

        sessionManager = SessionManager(this)

        loadingDialog = LoadingDialog(this)
        name = findViewById(R.id.name)
        email = findViewById(R.id.email)

        name.text = "HARINGHATA"
        email.text = sessionManager.dCode

        logout.setOnClickListener {
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

        upload.setOnClickListener {
            startActivity(Intent(this@WelcomeActivity, TaskListActivity::class.java))
            overridePendingTransition(
                R.anim.left_enter,
                R.anim.right_out
            )
        }

        submit.setOnClickListener {
            if (spChoiceStr.equals("Select WTP", false)) {
                showMessage("Please Select WTP", name)
                return@setOnClickListener
            }
            if (dob1.text == "DD/MM/YYYY") {
                showMessage("PLease Select Collection Date", name)
                return@setOnClickListener
            }
            if (dob2.text == "DD/MM/YYYY") {
                showMessage("Please Select Receive Date", name)
                return@setOnClickListener
            }
            if (dob3.text == "DD/MM/YYYY") {
                showMessage("Please Select Test Date", name)
                return@setOnClickListener
            }
            if (s1.text.isNullOrEmpty() || s1.text.length != 4) {
                showMessage("Please Enter Sample ID", name)
                return@setOnClickListener
            }

            try {
                sampleID = sample_id.text.toString() + s1.text.toString()
                val tempList = sessionManager.getArrayList()
                if (tempList.contains(sampleID)) {
                    showMessage("The sample ID was already in use! Enter different Sample ID", name)
                    return@setOnClickListener
                }
            }catch(e: Exception){}
            checkForSampleID(sampleID)
        }

        /*generate.setOnClickListener {
            val SPLASH_TIME_OUT = 2500
            val loadingDialog = LoadingDialog(this)
            loadingDialog.showDialog()
            Handler().postDelayed({
                loadingDialog.hideDialog()
                val rand = Random()
                sample_id.text = (rand.nextInt((999 - 100) + 1) + 100).toString() + "-" +
                        (rand.nextInt((9999 - 1000) + 1) + 1000).toString()
            }, SPLASH_TIME_OUT.toLong())
        }*/

        arrayListDistrict = ArrayList()
        fetchAllDistricts()
    }

    private fun checkForSampleID(s: String) {
        viewModel.isValidSampleId(s)
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

    @SuppressLint("SetTextI18n")
    override fun onResume() {
        super.onResume()
        //fetchAllDistricts()
        dob1.text = "DD/MM/YYYY"
        dob2.text = "DD/MM/YYYY"
        dob3.text = "DD/MM/YYYY"
        s1.setText("")
    }

    override fun onStarted() {
        loadingDialog.showDialog()
    }

    override fun onFailure(message: String) {
        loadingDialog.hideDialog()
    }

    override fun onNetworkFailure(message: String) {
        loadingDialog.hideDialog()
    }

    private fun fetchAllDistricts() {
        viewModel.fetchData()
    }

    override fun onDistrictResponse(response: String) {
        loadingDialog.hideDialog()

        val jsonObject = JSONObject(response)
        val str = jsonObject.getJSONObject("data").getJSONArray("districts").toString()
        val gson = GsonBuilder().create()
        val groupListType = object : TypeToken<ArrayList<District>>() {}.type
        arrayListDistrict = gson.fromJson(str, groupListType)
        arrayListDistrictCode = ArrayList()
        arrayListDistrictName = ArrayList()
        for (i in 0 until arrayListDistrict.size) {
            arrayListDistrictCode.add(arrayListDistrict[i].District_Code!!)
            arrayListDistrictName.add(arrayListDistrict[i].DistrictName!!)
        }

        val adap2 = ArrayAdapter(this, R.layout.new_spinner_item, arrayListDistrictName)
        spDistrict.adapter = adap2

        spDistrict.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                spDistrictID = arrayListDistrictCode[position]
                spDistrictStr = arrayListDistrictName[position]
                spDistrict.setSelection(position)
                fetchWTP(spDistrictID)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }

    private fun fetchWTP(spDistrictID: String) {
        viewModel.fetchWTP(spDistrictID)
    }

    override fun onWTPResponse(response: String) {
        loadingDialog.hideDialog()

        val jsonObject = JSONObject(response)
        val str = jsonObject.getJSONObject("data").getJSONArray("gcms_data").toString()
        val gson = GsonBuilder().create()
        val groupListType = object : TypeToken<ArrayList<WTP_Pojo>>() {}.type
        arrayListWTP = gson.fromJson(str, groupListType)
        arrayListWTPCode = ArrayList()
        arrayListWTPName = ArrayList()
        for (i in 0 until arrayListWTP.size) {
            arrayListWTPCode.add(arrayListWTP[i].wtpId!!)
            arrayListWTPName.add(arrayListWTP[i].wtpName!!)
        }

        val adap = ArrayAdapter(this, R.layout.new_spinner_item, arrayListWTPName)
        spChoice.adapter = adap

        spChoice.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            @SuppressLint("SetTextI18n")
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                spChoiceID = arrayListWTPCode[position]
                spChoiceStr = arrayListWTPName[position]
                spChoice.setSelection(position)
                selectedWTP = arrayListWTP[position]
                if (selectedWTP.wtpId!!.length == 1)
                    sample_id.text = "00" + selectedWTP.wtpId + "-"
                else if (selectedWTP.wtpId!!.length == 2)
                    sample_id.text = "0" + selectedWTP.wtpId + "-"
                else
                    sample_id.text = selectedWTP.wtpId + "-"
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    override fun onSampleIdResponse(response: String) {

        loadingDialog.hideDialog()

        val jsonObject = JSONObject(response)
        if (!jsonObject.getBoolean("valid")) {
            showMessage("The sample ID was already in use! Enter different Sample ID", name)
            return
        }

        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val currentDate = sdf.format(Date())
        myCalendar = Calendar.getInstance()
        val df1 = SimpleDateFormat("HH:mm:ss")
        formattedTime = df1.format(myCalendar.time)
        val intent = Intent(this@WelcomeActivity, MainActivity::class.java)
        intent.putExtra("CDATE", collect_selected_date)
        intent.putExtra("CURDATE", currentDate)
        intent.putExtra("RDATE", receive_selected_date)
        intent.putExtra("TDATE", test_selected_date)
        intent.putExtra("SAMPLEID", sampleID)
        intent.putExtra("TIME", formattedTime)
        intent.putExtra("WTP", spChoiceStr)
        intent.putExtra("VAL", selectedWTP as Serializable)
        intent.putExtra("UID", UUID.randomUUID().toString())
        startActivity(intent)
        overridePendingTransition(
            R.anim.left_enter,
            R.anim.right_out
        )
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
package com.sunanda.wtpharinghata.view

import android.graphics.Color
import android.graphics.Typeface
import android.os.AsyncTask
import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.sunanda.wtpharinghata.R
import com.sunanda.wtpharinghata.database.DatabaseClient
import com.sunanda.wtpharinghata.database.RowTable
import com.sunanda.wtpharinghata.database.Task
import com.sunanda.wtpharinghata.helper.DigitsInputFilter

class TreatedWaterFragment : Fragment() {

    lateinit var submit: Button
    lateinit var myView: View
    lateinit var alachlor: EditText
    lateinit var atrazine: EditText
    lateinit var aldrin: EditText
    lateinit var alpha: EditText
    lateinit var beta: EditText
    lateinit var butachlor: EditText
    lateinit var chlorpyriphos: EditText
    lateinit var delta: EditText
    lateinit var dichlor: EditText
    lateinit var endosulfan_sulphate: EditText
    lateinit var ethion: EditText
    lateinit var lindane: EditText
    lateinit var isoproturon: EditText
    lateinit var malathion: EditText
    lateinit var methyl: EditText
    lateinit var monocrotophos: EditText
    lateinit var phorate: EditText
    lateinit var bromoform: EditText
    lateinit var dibromochloromethane: EditText
    lateinit var bromochloromethane: EditText
    lateinit var chloroform: EditText
    // new added field
    lateinit var endosulfan1: EditText
    lateinit var endosulfan2: EditText
    lateinit var op_ddt: EditText
    lateinit var pp_ddt: EditText
    lateinit var bromodichloromethane: EditText
    lateinit var chlorodibromomethane: EditText

    internal var alachlor_flag = false
    internal var alpha_flag = false
    internal var atrazine_flag = false
    internal var beta_flag = false
    internal var butachlor_flag = false
    internal var chlorpyriphos_flag = false
    internal var delta_flag = false
    internal var endosulfan_sulphate_flag = false
    internal var endosulfan1_flag = false
    internal var endosulfan2_flag = false
    internal var ethion_flag = false
    internal var lindane_flag = false
    internal var malathion_flag = false
    internal var op_ddt_flag = false
    internal var methyl_flag = false
    internal var phorate_flag = false
    internal var pp_ddt_flag = false
    internal var bromodichloromethane_flag = false
    internal var bromoform_flag = false
    internal var chlorodibromomethane_flag = false
    internal var chloroform_flag = false

    var currentDate = ""
    var myCollectionDate = ""
    var myReceiveDate = ""
    var myTestDate = ""
    var sampleId = ""
    var myTime = ""
    var wtpname = ""
    var myRow = RowTable()
    var myTask = Task()
    var flag = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        myView = inflater.inflate(R.layout.fragment_treated_water, container, false)
        submit = myView.findViewById(R.id.saveData)

        alachlor = myView.findViewById(R.id.alachlor)
        atrazine = myView.findViewById(R.id.atrazine)
        aldrin = myView.findViewById(R.id.aldrin)
        alpha = myView.findViewById(R.id.alpha)
        beta = myView.findViewById(R.id.beta)
        butachlor = myView.findViewById(R.id.butachlor)
        chlorpyriphos = myView.findViewById(R.id.chlorpyriphos)
        delta = myView.findViewById(R.id.delta)
        dichlor = myView.findViewById(R.id.dichlor)
        endosulfan_sulphate = myView.findViewById(R.id.endosulfan_sulphate)
        ethion = myView.findViewById(R.id.ethion)
        lindane = myView.findViewById(R.id.lindane)
        isoproturon = myView.findViewById(R.id.isoproturon)
        malathion = myView.findViewById(R.id.malathion)
        methyl = myView.findViewById(R.id.methyl)
        monocrotophos = myView.findViewById(R.id.monocrotophos)
        phorate = myView.findViewById(R.id.phorate)
        bromoform = myView.findViewById(R.id.bromoform)
        dibromochloromethane = myView.findViewById(R.id.dibromochloromethane)
        bromochloromethane = myView.findViewById(R.id.bromochloromethane)
        chloroform = myView.findViewById(R.id.chloroform)
        // new field added
        endosulfan1 = myView.findViewById(R.id.endosulfan1)
        endosulfan2 = myView.findViewById(R.id.endosulfan2)
        op_ddt = myView.findViewById(R.id.op_ddt)
        pp_ddt = myView.findViewById(R.id.pp_ddt)
        bromodichloromethane = myView.findViewById(R.id.bromodichloromethane)
        chlorodibromomethane = myView.findViewById(R.id.chlorodibromomethane)


        if (activity!!.intent.hasExtra("CDATE")) {
            currentDate = activity!!.intent.getStringExtra("CURDATE")!!
            myCollectionDate = activity!!.intent.getStringExtra("CDATE")!!
            myReceiveDate = activity!!.intent.getStringExtra("RDATE")!!
            myTestDate = activity!!.intent.getStringExtra("TDATE")!!
            sampleId = activity!!.intent.getStringExtra("SAMPLEID")!!
            myTime = activity!!.intent.getStringExtra("TIME")!!
            wtpname = activity!!.intent.getStringExtra("WTP")!!
        } else {
            val gson = Gson()
            myRow = gson.fromJson(activity!!.intent.getStringExtra("ROW"), RowTable::class.java)
            myCollectionDate = myRow.cdate!!
            myReceiveDate = myRow.rdate!!
            myTestDate = myRow.tdate!!
            sampleId = myRow.sid!!
            myTime = myRow.rtime!!
            wtpname = myRow.wtp_name!!

            if (!TextUtils.isEmpty(myRow.treated)) {
                val parser = JsonParser()
                val mJson = parser.parse(myRow.treated)
                val gson2 = Gson()
                myTask = gson2.fromJson<Task>(mJson, Task::class.java)

                flag = true
                setData()
            }
        }

        alachlor.filters = arrayOf<android.text.InputFilter>(
            DigitsInputFilter(1, 4, 9.9999)
        )
        atrazine.filters = arrayOf<android.text.InputFilter>(
            DigitsInputFilter(1, 4, 9.9999)
        )
        aldrin.filters = arrayOf<android.text.InputFilter>(
            DigitsInputFilter(1, 4, 9.9999)
        )
        alpha.filters = arrayOf<android.text.InputFilter>(
            DigitsInputFilter(1, 4, 9.9999)
        )
        beta.filters = arrayOf<android.text.InputFilter>(
            DigitsInputFilter(1, 4, 9.9999)
        )
        butachlor.filters = arrayOf<android.text.InputFilter>(
            DigitsInputFilter(1, 4, 9.9999)
        )
        chlorpyriphos.filters = arrayOf<android.text.InputFilter>(
            DigitsInputFilter(1, 4, 9.9999)
        )
        delta.filters = arrayOf<android.text.InputFilter>(
            DigitsInputFilter(1, 4, 9.9999)
        )
        dichlor.filters = arrayOf<android.text.InputFilter>(
            DigitsInputFilter(1, 4, 9.9999)
        )
        endosulfan_sulphate.filters = arrayOf<android.text.InputFilter>(
            DigitsInputFilter(1, 4, 9.9999)
        )
        ethion.filters = arrayOf<android.text.InputFilter>(
            DigitsInputFilter(1, 4, 9.9999)
        )
        lindane.filters = arrayOf<android.text.InputFilter>(
            DigitsInputFilter(1, 4, 9.9999)
        )
        isoproturon.filters = arrayOf<android.text.InputFilter>(
            DigitsInputFilter(1, 4, 9.9999)
        )
        malathion.filters = arrayOf<android.text.InputFilter>(
            DigitsInputFilter(1, 4, 9.9999)
        )
        methyl.filters = arrayOf<android.text.InputFilter>(
            DigitsInputFilter(1, 4, 9.9999)
        )
        monocrotophos.filters = arrayOf<android.text.InputFilter>(
            DigitsInputFilter(1, 4, 9.9999)
        )
        phorate.filters = arrayOf<android.text.InputFilter>(
            DigitsInputFilter(1, 4, 9.9999)
        )
        bromoform.filters = arrayOf<android.text.InputFilter>(
            DigitsInputFilter(1, 4, 9.9999)
        )
        dibromochloromethane.filters = arrayOf<android.text.InputFilter>(
            DigitsInputFilter(1, 4, 9.9999)
        )
        bromochloromethane.filters = arrayOf<android.text.InputFilter>(
            DigitsInputFilter(1, 4, 9.9999)
        )
        chloroform.filters = arrayOf<android.text.InputFilter>(
            DigitsInputFilter(1, 4, 9.9999)
        )
        // new field added
        endosulfan1.filters = arrayOf<android.text.InputFilter>(
            DigitsInputFilter(1, 4, 9.9999)
        )
        endosulfan2.filters = arrayOf<android.text.InputFilter>(
            DigitsInputFilter(1, 4, 9.9999)
        )
        op_ddt.filters = arrayOf<android.text.InputFilter>(
            DigitsInputFilter(1, 4, 9.9999)
        )
        pp_ddt.filters = arrayOf<android.text.InputFilter>(
            DigitsInputFilter(1, 4, 9.9999)
        )
        bromodichloromethane.filters = arrayOf<android.text.InputFilter>(
            DigitsInputFilter(1, 4, 9.9999)
        )
        chlorodibromomethane.filters = arrayOf<android.text.InputFilter>(
            DigitsInputFilter(1, 4, 9.9999)
        )

        submit.setOnClickListener {

            val dialogBuilder = AlertDialog.Builder(activity!!)
            dialogBuilder.setMessage("Do you want to save these entry?")
                .setCancelable(false)
                .setPositiveButton("Proceed") { dialog, id ->
                    /*if (flag)
                        updateTask()
                    else*/
                    saveTask()
                }
                .setNegativeButton("Cancel") { dialog, id ->
                    dialog.cancel()
                }
            val alert = dialogBuilder.create()
            alert.setTitle("Save Entry")
            alert.setCancelable(false)
            alert.show()
        }

        myView.findViewById<CheckBox>(R.id.cb_alachlor).setOnCheckedChangeListener { buttonView, isChecked ->
            alachlor_flag = !alachlor_flag
            if (isChecked)
                myView.findViewById<TextInputLayout>(R.id.tl_alachlor).visibility = View.GONE
            else
                myView.findViewById<TextInputLayout>(R.id.tl_alachlor).visibility = View.VISIBLE
        }
        myView.findViewById<CheckBox>(R.id.cb_alpha).setOnCheckedChangeListener { buttonView, isChecked ->
            alpha_flag = !alpha_flag
            if (isChecked)
                myView.findViewById<TextInputLayout>(R.id.tl_alpha).visibility = View.GONE
            else
                myView.findViewById<TextInputLayout>(R.id.tl_alpha).visibility = View.VISIBLE
        }
        myView.findViewById<CheckBox>(R.id.cb_atrazine).setOnCheckedChangeListener { buttonView, isChecked ->
            atrazine_flag = !atrazine_flag
            if (isChecked)
                myView.findViewById<TextInputLayout>(R.id.tl_atrazine).visibility = View.GONE
            else
                myView.findViewById<TextInputLayout>(R.id.tl_atrazine).visibility = View.VISIBLE
        }
        myView.findViewById<CheckBox>(R.id.cb_beta).setOnCheckedChangeListener { buttonView, isChecked ->
            beta_flag = !beta_flag
            if (isChecked)
                myView.findViewById<TextInputLayout>(R.id.tl_beta).visibility = View.GONE
            else
                myView.findViewById<TextInputLayout>(R.id.tl_beta).visibility = View.VISIBLE
        }
        myView.findViewById<CheckBox>(R.id.cb_butachlor).setOnCheckedChangeListener { buttonView, isChecked ->
            butachlor_flag = !butachlor_flag
            if (isChecked)
                myView.findViewById<TextInputLayout>(R.id.tl_butachlor).visibility = View.GONE
            else
                myView.findViewById<TextInputLayout>(R.id.tl_butachlor).visibility = View.VISIBLE
        }
        myView.findViewById<CheckBox>(R.id.cb_chlorpyriphos).setOnCheckedChangeListener { buttonView, isChecked ->
            chlorpyriphos_flag = !chlorpyriphos_flag
            if (isChecked)
                myView.findViewById<TextInputLayout>(R.id.tl_chlorpyriphos).visibility = View.GONE
            else
                myView.findViewById<TextInputLayout>(R.id.tl_chlorpyriphos).visibility = View.VISIBLE
        }
        myView.findViewById<CheckBox>(R.id.cb_delta).setOnCheckedChangeListener { buttonView, isChecked ->
            delta_flag = !delta_flag
            if (isChecked)
                myView.findViewById<TextInputLayout>(R.id.tl_delta).visibility = View.GONE
            else
                myView.findViewById<TextInputLayout>(R.id.tl_delta).visibility = View.VISIBLE
        }
        myView.findViewById<CheckBox>(R.id.cb_endosulfan_sulphate).setOnCheckedChangeListener { buttonView, isChecked ->
            endosulfan_sulphate_flag = !endosulfan_sulphate_flag
            if (isChecked)
                myView.findViewById<TextInputLayout>(R.id.tl_endosulfan_sulphate).visibility = View.GONE
            else
                myView.findViewById<TextInputLayout>(R.id.tl_endosulfan_sulphate).visibility = View.VISIBLE
        }
        myView.findViewById<CheckBox>(R.id.cb_endosulfan1).setOnCheckedChangeListener { buttonView, isChecked ->
            endosulfan1_flag = !endosulfan1_flag
            if (isChecked)
                myView.findViewById<TextInputLayout>(R.id.tl_endosulfan1).visibility = View.GONE
            else
                myView.findViewById<TextInputLayout>(R.id.tl_endosulfan1).visibility = View.VISIBLE
        }
        myView.findViewById<CheckBox>(R.id.cb_endosulfan2).setOnCheckedChangeListener { buttonView, isChecked ->
            endosulfan2_flag = !endosulfan2_flag
            if (isChecked)
                myView.findViewById<TextInputLayout>(R.id.tl_endosulfan2).visibility = View.GONE
            else
                myView.findViewById<TextInputLayout>(R.id.tl_endosulfan2).visibility = View.VISIBLE
        }
        myView.findViewById<CheckBox>(R.id.cb_ethion).setOnCheckedChangeListener { buttonView, isChecked ->
            ethion_flag = !ethion_flag
            if (isChecked)
                myView.findViewById<TextInputLayout>(R.id.tl_ethion).visibility = View.GONE
            else
                myView.findViewById<TextInputLayout>(R.id.tl_ethion).visibility = View.VISIBLE
        }
        myView.findViewById<CheckBox>(R.id.cb_lindane).setOnCheckedChangeListener { buttonView, isChecked ->
            lindane_flag = !lindane_flag
            if (isChecked)
                myView.findViewById<TextInputLayout>(R.id.tl_lindane).visibility = View.GONE
            else
                myView.findViewById<TextInputLayout>(R.id.tl_lindane).visibility = View.VISIBLE
        }
        myView.findViewById<CheckBox>(R.id.cb_malathion).setOnCheckedChangeListener { buttonView, isChecked ->
            malathion_flag = !malathion_flag
            if (isChecked)
                myView.findViewById<TextInputLayout>(R.id.tl_malathion).visibility = View.GONE
            else
                myView.findViewById<TextInputLayout>(R.id.tl_malathion).visibility = View.VISIBLE
        }
        myView.findViewById<CheckBox>(R.id.cb_op_ddt).setOnCheckedChangeListener { buttonView, isChecked ->
            op_ddt_flag = !op_ddt_flag
            if (isChecked)
                myView.findViewById<TextInputLayout>(R.id.tl_op_ddt).visibility = View.GONE
            else
                myView.findViewById<TextInputLayout>(R.id.tl_op_ddt).visibility = View.VISIBLE
        }
        myView.findViewById<CheckBox>(R.id.cb_methyl).setOnCheckedChangeListener { buttonView, isChecked ->
            methyl_flag = !methyl_flag
            if (isChecked)
                myView.findViewById<TextInputLayout>(R.id.tl_methyl).visibility = View.GONE
            else
                myView.findViewById<TextInputLayout>(R.id.tl_methyl).visibility = View.VISIBLE
        }
        myView.findViewById<CheckBox>(R.id.cb_phorate).setOnCheckedChangeListener { buttonView, isChecked ->
            phorate_flag = !phorate_flag
            if (isChecked)
                myView.findViewById<TextInputLayout>(R.id.tl_phorate).visibility = View.GONE
            else
                myView.findViewById<TextInputLayout>(R.id.tl_phorate).visibility = View.VISIBLE
        }
        myView.findViewById<CheckBox>(R.id.cb_pp_ddt).setOnCheckedChangeListener { buttonView, isChecked ->
            pp_ddt_flag = !pp_ddt_flag
            if (isChecked)
                myView.findViewById<TextInputLayout>(R.id.tl_pp_ddt).visibility = View.GONE
            else
                myView.findViewById<TextInputLayout>(R.id.tl_pp_ddt).visibility = View.VISIBLE
        }
        myView.findViewById<CheckBox>(R.id.cb_bromodichloromethane).setOnCheckedChangeListener { buttonView, isChecked ->
            bromodichloromethane_flag = !bromodichloromethane_flag
            if (isChecked)
                myView.findViewById<TextInputLayout>(R.id.tl_bromodichloromethane).visibility = View.GONE
            else
                myView.findViewById<TextInputLayout>(R.id.tl_bromodichloromethane).visibility = View.VISIBLE
        }
        myView.findViewById<CheckBox>(R.id.cb_bromoform).setOnCheckedChangeListener { buttonView, isChecked ->
            bromoform_flag = !bromoform_flag
            if (isChecked)
                myView.findViewById<TextInputLayout>(R.id.tl_bromoform).visibility = View.GONE
            else
                myView.findViewById<TextInputLayout>(R.id.tl_bromoform).visibility = View.VISIBLE
        }
        myView.findViewById<CheckBox>(R.id.cb_chlorodibromomethane).setOnCheckedChangeListener { buttonView, isChecked ->
            chlorodibromomethane_flag = !chlorodibromomethane_flag
            if (isChecked)
                myView.findViewById<TextInputLayout>(R.id.tl_chlorodibromomethane).visibility = View.GONE
            else
                myView.findViewById<TextInputLayout>(R.id.tl_chlorodibromomethane).visibility = View.VISIBLE
        }
        myView.findViewById<CheckBox>(R.id.cb_chloroform).setOnCheckedChangeListener { buttonView, isChecked ->
            chloroform_flag = !chloroform_flag
            if (isChecked)
                myView.findViewById<TextInputLayout>(R.id.tl_chloroform).visibility = View.GONE
            else
                myView.findViewById<TextInputLayout>(R.id.tl_chloroform).visibility = View.VISIBLE
        }

        return myView
    }

    private fun setData() {

        alachlor.setText(myTask.alachlor)
        if (myTask.alachlor.isNullOrEmpty()) {
            myView.findViewById<CheckBox>(R.id.cb_alachlor).isChecked = true
            alachlor_flag = true
            myView.findViewById<TextInputLayout>(R.id.tl_alachlor).visibility = View.GONE
        }
        atrazine.setText(myTask.atrazine)
        if (myTask.atrazine.isNullOrEmpty()) {
            myView.findViewById<CheckBox>(R.id.cb_atrazine).isChecked = true
            atrazine_flag = true
            myView.findViewById<TextInputLayout>(R.id.tl_atrazine).visibility = View.GONE
        }
        //aldrin.setText(myTask.aldrin)
        alpha.setText(myTask.alpha_hch)
        if (myTask.alpha_hch.isNullOrEmpty()) {
            myView.findViewById<CheckBox>(R.id.cb_alpha).isChecked = true
            alpha_flag = true
            myView.findViewById<TextInputLayout>(R.id.tl_alpha).visibility = View.GONE
        }
        beta.setText(myTask.beta_hch)
        if (myTask.beta_hch.isNullOrEmpty()) {
            myView.findViewById<CheckBox>(R.id.cb_beta).isChecked = true
            beta_flag = true
            myView.findViewById<TextInputLayout>(R.id.tl_beta).visibility = View.GONE
        }
        butachlor.setText(myTask.butachlor)
        if (myTask.butachlor.isNullOrEmpty()) {
            myView.findViewById<CheckBox>(R.id.cb_butachlor).isChecked = true
            butachlor_flag = true
            myView.findViewById<TextInputLayout>(R.id.tl_butachlor).visibility = View.GONE
        }
        chlorpyriphos.setText(myTask.chlorpyriphos)
        if (myTask.chlorpyriphos.isNullOrEmpty()) {
            myView.findViewById<CheckBox>(R.id.cb_chlorpyriphos).isChecked = true
            chlorpyriphos_flag = true
            myView.findViewById<TextInputLayout>(R.id.tl_chlorpyriphos).visibility = View.GONE
        }
        delta.setText(myTask.delta_hch)
        if (myTask.delta_hch.isNullOrEmpty()) {
            myView.findViewById<CheckBox>(R.id.cb_delta).isChecked = true
            delta_flag = true
            myView.findViewById<TextInputLayout>(R.id.tl_delta).visibility = View.GONE
        }
        //dichlor.setText(myTask.dichlor)
        endosulfan_sulphate.setText(myTask.endosulfan)
        if (myTask.endosulfan.isNullOrEmpty()) {
            myView.findViewById<CheckBox>(R.id.cb_endosulfan_sulphate).isChecked = true
            endosulfan_sulphate_flag = true
            myView.findViewById<TextInputLayout>(R.id.tl_endosulfan_sulphate).visibility = View.GONE
        }
        ethion.setText(myTask.ethion)
        if (myTask.ethion.isNullOrEmpty()) {
            myView.findViewById<CheckBox>(R.id.cb_ethion).isChecked = true
            ethion_flag = true
            myView.findViewById<TextInputLayout>(R.id.tl_ethion).visibility = View.GONE
        }
        lindane.setText(myTask.gamma)
        if (myTask.gamma.isNullOrEmpty()) {
            myView.findViewById<CheckBox>(R.id.cb_lindane).isChecked = true
            lindane_flag = true
            myView.findViewById<TextInputLayout>(R.id.tl_lindane).visibility = View.GONE
        }
        //isoproturon.setText(myTask.isoproturon)
        malathion.setText(myTask.malathion)
        if (myTask.malathion.isNullOrEmpty()) {
            myView.findViewById<CheckBox>(R.id.cb_malathion).isChecked = true
            malathion_flag = true
            myView.findViewById<TextInputLayout>(R.id.tl_malathion).visibility = View.GONE
        }
        methyl.setText(myTask.methyl)
        if (myTask.methyl.isNullOrEmpty()) {
            myView.findViewById<CheckBox>(R.id.cb_methyl).isChecked = true
            methyl_flag = true
            myView.findViewById<TextInputLayout>(R.id.tl_methyl).visibility = View.GONE
        }
        //monocrotophos.setText(myTask.monocrotophos)
        phorate.setText(myTask.phorate)
        if (myTask.alachlor.isNullOrEmpty()) {
            myView.findViewById<CheckBox>(R.id.cb_phorate).isChecked = true
            phorate_flag = true
            myView.findViewById<TextInputLayout>(R.id.tl_phorate).visibility = View.GONE
        }
        bromoform.setText(myTask.bromoform)
        if (myTask.bromoform.isNullOrEmpty()) {
            myView.findViewById<CheckBox>(R.id.cb_bromoform).isChecked = true
            bromoform_flag = true
            myView.findViewById<TextInputLayout>(R.id.tl_bromoform).visibility = View.GONE
        }
        //dibromochloromethane.setText(myTask.dibromochloromethane)
        //bromochloromethane.setText(myTask.bromochloromethane)
        chloroform.setText(myTask.chloroform)
        if (myTask.chloroform.isNullOrEmpty()) {
            myView.findViewById<CheckBox>(R.id.cb_chloroform).isChecked = true
            chloroform_flag = true
            myView.findViewById<TextInputLayout>(R.id.tl_chloroform).visibility = View.GONE
        }
        // new field added
        endosulfan1.setText(myTask.endosulfan1)
        if (myTask.endosulfan1.isNullOrEmpty()) {
            myView.findViewById<CheckBox>(R.id.cb_endosulfan1).isChecked = true
            endosulfan1_flag = true
            myView.findViewById<TextInputLayout>(R.id.tl_endosulfan1).visibility = View.GONE
        }
        endosulfan2.setText(myTask.endosulfan2)
        if (myTask.endosulfan2.isNullOrEmpty()) {
            myView.findViewById<CheckBox>(R.id.cb_endosulfan2).isChecked = true
            endosulfan2_flag = true
            myView.findViewById<TextInputLayout>(R.id.tl_endosulfan2).visibility = View.GONE
        }
        op_ddt.setText(myTask.op_ddt)
        if (myTask.alachlor.isNullOrEmpty()) {
            myView.findViewById<CheckBox>(R.id.cb_op_ddt).isChecked = true
            op_ddt_flag = true
            myView.findViewById<TextInputLayout>(R.id.tl_op_ddt).visibility = View.GONE
        }
        pp_ddt.setText(myTask.pp_ddt)
        if (myTask.pp_ddt.isNullOrEmpty()) {
            myView.findViewById<CheckBox>(R.id.cb_pp_ddt).isChecked = true
            pp_ddt_flag = true
            myView.findViewById<TextInputLayout>(R.id.tl_pp_ddt).visibility = View.GONE
        }
        bromodichloromethane.setText(myTask.bromodichloromethane)
        if (myTask.bromodichloromethane.isNullOrEmpty()) {
            myView.findViewById<CheckBox>(R.id.cb_bromodichloromethane).isChecked = true
            bromodichloromethane_flag = true
            myView.findViewById<TextInputLayout>(R.id.tl_bromodichloromethane).visibility = View.GONE
        }
        chlorodibromomethane.setText(myTask.chlorodibromomethane)
        if (myTask.chlorodibromomethane.isNullOrEmpty()) {
            myView.findViewById<CheckBox>(R.id.cb_chlorodibromomethane).isChecked = true
            chlorodibromomethane_flag = true
            myView.findViewById<TextInputLayout>(R.id.tl_chlorodibromomethane).visibility = View.GONE
        }
    }

    private fun saveTask() {

        /*if (alachlor.text.toString().isEmpty() && atrazine.text.toString().isEmpty() && aldrin.text.toString().isEmpty() &&
            alpha.text.toString().isEmpty() && beta.text.toString().isEmpty() && butachlor.text.toString().isEmpty() &&
            chlorpyriphos.text.toString().isEmpty() && delta.text.toString().isEmpty() && dichlor.text.toString().isEmpty() &&
            endosulfan.text.toString().isEmpty() && ethion.text.toString().isEmpty() && lindane.text.toString().isEmpty() &&
            isoproturon.text.toString().isEmpty() && malathion.text.toString().isEmpty() && methyl.text.toString().isEmpty() &&
            monocrotophos.text.toString().isEmpty() && phorate.text.toString().isEmpty() && bromoform.text.toString().isEmpty() &&
            dibromochloromethane.text.toString().isEmpty() && bromochloromethane.text.toString().isEmpty() &&
            chloroform.text.toString().isEmpty() && endosulfan1.text.toString().isEmpty() && endosulfan2.text.toString().isEmpty()
            && op_ddt.text.toString().isEmpty() && pp_ddt.text.toString().isEmpty() && bromodichloromethane.text.toString().isEmpty()
            && chlorodibromomethane.text.toString().isEmpty()
        ) {
            showMessage("Please enter at least one parameter value", submit)
            return
        }*/
        if (alachlor.text.toString().isEmpty() && !alachlor_flag) {
            alachlor.error = ""
            alachlor.requestFocus()
            showMessage("Please enter Alachlor parameter value", submit)
            return
        }
        if (alpha.text.toString().isEmpty() && !alpha_flag) {
            alpha.error = ""
            alpha.requestFocus()
            showMessage("Please enter Alpha HCH parameter value", submit)
            return
        }
        if (atrazine.text.toString().isEmpty() && !atrazine_flag) {
            atrazine.error = ""
            atrazine.requestFocus()
            showMessage("Please enter Atrazine parameter value", submit)
            return
        }
        if (beta.text.toString().isEmpty() && !beta_flag) {
            beta.error = ""
            beta.requestFocus()
            showMessage("Please enter Beta HCH parameter value", submit)
            return
        }
        if (butachlor.text.toString().isEmpty() && !butachlor_flag) {
            butachlor.error = ""
            butachlor.requestFocus()
            showMessage("Please enter Butachlor parameter value", submit)
            return
        }
        if (chlorpyriphos.text.toString().isEmpty() && !chlorpyriphos_flag) {
            chlorpyriphos.error = ""
            chlorpyriphos.requestFocus()
            showMessage("Please enter Chlorpyriphos parameter value", submit)
            return
        }
        if (delta.text.toString().isEmpty() && !delta_flag) {
            delta.error = ""
            delta.requestFocus()
            showMessage("Please enter Delta HCH parameter value", submit)
            return
        }
        if (endosulfan_sulphate.text.toString().isEmpty() && !endosulfan_sulphate_flag) {
            endosulfan_sulphate.error = ""
            endosulfan_sulphate.requestFocus()
            showMessage("Please enter Endosulfan Sulphate parameter value", submit)
            return
        }
        if (endosulfan1.text.toString().isEmpty() && !endosulfan1_flag) {
            endosulfan1.error = ""
            endosulfan1.requestFocus()
            showMessage("Please enter Endosulfan-1 parameter value", submit)
            return
        }
        if (endosulfan2.text.toString().isEmpty() && !endosulfan2_flag) {
            endosulfan2.error = ""
            endosulfan2.requestFocus()
            showMessage("Please enter Endosulfan-2 parameter value", submit)
            return
        }
        if (ethion.text.toString().isEmpty() && !ethion_flag) {
            ethion.error = ""
            ethion.requestFocus()
            showMessage("Please enter Ethion parameter value", submit)
            return
        }
        if (lindane.text.toString().isEmpty() && !lindane_flag) {
            lindane.error = ""
            lindane.requestFocus()
            showMessage("Please enter Lindane parameter value", submit)
            return
        }
        if (malathion.text.toString().isEmpty() && !malathion_flag) {
            malathion.error = ""
            malathion.requestFocus()
            showMessage("Please enter Malathion parameter value", submit)
            return
        }
        if (op_ddt.text.toString().isEmpty() && !op_ddt_flag) {
            op_ddt.error = ""
            op_ddt.requestFocus()
            showMessage("Please enter o,p'-DDT parameter value", submit)
            return
        }
        if (methyl.text.toString().isEmpty() && !methyl_flag) {
            methyl.error = ""
            methyl.requestFocus()
            showMessage("Please enter Parathion - Methyl parameter value", submit)
            return
        }
        if (phorate.text.toString().isEmpty() && !phorate_flag) {
            phorate.error = ""
            phorate.requestFocus()
            showMessage("Please enter Phorate parameter value", submit)
            return
        }
        if (pp_ddt.text.toString().isEmpty() && !pp_ddt_flag) {
            pp_ddt.error = ""
            pp_ddt.requestFocus()
            showMessage("Please enter p,p'-DDT parameter value", submit)
            return
        }
        if (bromodichloromethane.text.toString().isEmpty() && !bromodichloromethane_flag) {
            bromodichloromethane.error = ""
            bromodichloromethane.requestFocus()
            showMessage("Please enter Bromodichloromethane parameter value", submit)
            return
        }
        if (bromoform.text.toString().isEmpty() && !bromoform_flag) {
            bromoform.error = ""
            bromoform.requestFocus()
            showMessage("Please enter Bromoform parameter value", submit)
            return
        }
        if (chlorodibromomethane.text.toString().isEmpty() && !chlorodibromomethane_flag) {
            chlorodibromomethane.error = ""
            chlorodibromomethane.requestFocus()
            showMessage("Please enter Chlorodibromomethane parameter value", submit)
            return
        }
        if (chloroform.text.toString().isEmpty() && !chloroform_flag) {
            chloroform.error = ""
            chloroform.requestFocus()
            showMessage("Please enter Chloroform parameter value", submit)
            return
        }

        val alachlor_str = alachlor.text.toString().trim()
        val atrazine_str = atrazine.text.toString().trim()
        val aldrin_str = aldrin.text.toString().trim()
        val alpha_hch_str = alpha.text.toString().trim()
        val beta_hch_str = beta.text.toString().trim()
        val butachlor_str = butachlor.text.toString().trim()
        val chlorpyriphos_str = chlorpyriphos.text.toString().trim()
        val delta_hch_str = delta.text.toString().trim()
        val dichlor_str = dichlor.text.toString().trim()
        val endosulfan_str = endosulfan_sulphate.text.toString().trim()
        val ethion_str = ethion.text.toString().trim()
        val gamma_str = lindane.text.toString().trim()
        val isoproturon_str = isoproturon.text.toString().trim()
        val malathion_str = malathion.text.toString().trim()
        val methyl_str = methyl.text.toString().trim()
        val monocrotophos_str = monocrotophos.text.toString().trim()
        val phorate_str = phorate.text.toString().trim()
        val Bromoform_str = bromoform.text.toString().trim()
        val dibromochloromethane_str = dibromochloromethane.text.toString().trim()
        val bromochloromethane_str = bromochloromethane.text.toString().trim()
        val chloroform_str = chloroform.text.toString().trim()
        // new field added
        val endosulfan1_str = endosulfan1.text.toString().trim()
        val endosulfan2_str = endosulfan2.text.toString().trim()
        val op_ddt_str = op_ddt.text.toString().trim()
        val pp_ddt_str = pp_ddt.text.toString().trim()
        val bromodichloromethane_str = bromodichloromethane.text.toString().trim()
        val chlorodibromomethane_str = chlorodibromomethane.text.toString().trim()

        //creating a task
        val task = Task()
        task.taskId = activity!!.intent.getStringExtra("UID")
        task.alachlor = alachlor_str
        task.atrazine = atrazine_str
        task.aldrin = aldrin_str
        task.alpha_hch = alpha_hch_str
        task.beta_hch = beta_hch_str
        task.butachlor = butachlor_str
        task.chlorpyriphos = chlorpyriphos_str
        task.delta_hch = delta_hch_str
        task.dichlor = dichlor_str
        task.endosulfan = endosulfan_str
        task.ethion = ethion_str
        task.gamma = gamma_str
        task.isoproturon = isoproturon_str
        task.malathion = malathion_str
        task.methyl = methyl_str
        task.monocrotophos = monocrotophos_str
        task.phorate = phorate_str
        task.bromoform = Bromoform_str
        task.dibromochloromethane = dibromochloromethane_str
        task.bromochloromethane = bromochloromethane_str
        task.chloroform = chloroform_str
        task.type = "TREATED WATER"
        task.wtpname = wtpname
        task.entrydate = "$currentDate $myTime"
        // new added filed
        task.endosulfan1 = endosulfan1_str
        task.endosulfan2 = endosulfan2_str
        task.op_ddt = op_ddt_str
        task.pp_ddt = pp_ddt_str
        task.bromodichloromethane = bromodichloromethane_str
        task.chlorodibromomethane = chlorodibromomethane_str
        task.collection_date = myCollectionDate
        task.receive_date = myReceiveDate
        task.test_date = myTestDate

        val gson = Gson()
        val json = gson.toJson(task)

        val rowTable = RowTable()
        rowTable.rdate = myReceiveDate
        rowTable.cdate = myCollectionDate
        rowTable.tdate = myTestDate
        rowTable.sid = sampleId
        rowTable.wtp_name = wtpname
        rowTable.rtime = myTime
        rowTable.treated = json

        isExistsTreate(sampleId, rowTable)
    }


    /*private fun isExistsRow(rdate: String, rtime: String, wtp: String, rowTable: RowTable) {

        class getData : AsyncTask<Void, Void, Int>() {

            override fun doInBackground(vararg voids: Void): Int {

                //adding to database
                val cnt = DatabaseClient.getInstance(activity!!)
                    .appDatabase
                    .rowTableDao()
                    .isExists(rdate, rtime, wtp)
                return cnt
            }

            override fun onPostExecute(cnt: Int) {
                super.onPostExecute(cnt)
                if (cnt == 0) {
                    saveTreated(rowTable)
                } else {
                    updateTreated(rowTable, cnt)
                }
            }
        }

        val st = getData()
        st.execute()
    }*/

    private fun isExistsTreate(sid: String, rowTable: RowTable) {

        class getData : AsyncTask<Void, Void, Int>() {

            override fun doInBackground(vararg voids: Void): Int {

                //adding to database
                val cnt = DatabaseClient.getInstance(activity!!)
                    .appDatabase
                    .rowTableDao()
                    .isExists(sid)
                return cnt
            }

            override fun onPostExecute(cnt: Int) {
                super.onPostExecute(cnt)
                if (cnt == 0) {
                    saveTreated(rowTable)
                } else {
                    updateTreated(rowTable, cnt)
                }
            }
        }

        val st = getData()
        st.execute()
    }

    private fun saveTreated(rowTable: RowTable) {

        class SaveTreated : AsyncTask<Void, Void, Void>() {

            override fun doInBackground(vararg voids: Void): Void? {

                //adding to database
                DatabaseClient.getInstance(activity!!)
                    .appDatabase
                    .rowTableDao()
                    .insert(rowTable)
                return null
            }

            override fun onPostExecute(aVoid: Void?) {
                super.onPostExecute(aVoid)
                Toast.makeText(activity!!, "Treated Water Data saved successfully", Toast.LENGTH_LONG).show()
                //clearFields()
            }
        }

        val st = SaveTreated()
        st.execute()
    }

    private fun updateTreated(rowTable: RowTable, rid: Int) {

        class UpdateTreated : AsyncTask<Void, Void, Void>() {

            override fun doInBackground(vararg voids: Void): Void? {

                //adding to database
                DatabaseClient.getInstance(activity!!)
                    .appDatabase
                    .rowTableDao()
                    .updateRowTreated(rowTable.treated!!, rid)
                return null
            }

            override fun onPostExecute(aVoid: Void?) {
                super.onPostExecute(aVoid)
                Toast.makeText(activity!!, "Treated Water Data updated successfully", Toast.LENGTH_LONG).show()
                //clearFields()
            }
        }

        val st = UpdateTreated()
        st.execute()
    }

    private fun clearFields() {
        alachlor.setText("")
        atrazine.setText("")
        aldrin.setText("")
        alpha.setText("")
        beta.setText("")
        butachlor.setText("")
        chlorpyriphos.setText("")
        delta.setText("")
        dichlor.setText("")
        endosulfan_sulphate.setText("")
        ethion.setText("")
        lindane.setText("")
        isoproturon.setText("")
        malathion.setText("")
        methyl.setText("")
        monocrotophos.setText("")
        phorate.setText("")
        bromoform.setText("")
        dibromochloromethane.setText("")
        bromochloromethane.setText("")
        chloroform.setText("")
    }

    private fun showMessage(str: String, view: View) {
        //val parentLayout = findViewById(android.R.id.content)
        val snackbar = Snackbar.make(view, str, Snackbar.LENGTH_LONG)
        snackbar.setActionTextColor(Color.RED)
        val sbView = snackbar.view
        val textView = sbView.findViewById(R.id.snackbar_text) as TextView
        textView.setTextColor(Color.parseColor("#FFFFFF"))
        textView.typeface = Typeface.createFromAsset(activity!!.assets, "proxima_nova_light.ttf")
        textView.textSize = 17f
        textView.gravity = Gravity.CENTER_VERTICAL
        snackbar.show()
    }
}
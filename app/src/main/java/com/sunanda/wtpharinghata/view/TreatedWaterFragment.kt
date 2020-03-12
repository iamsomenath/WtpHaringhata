package com.sunanda.wtpharinghata.view

import android.graphics.Color
import android.graphics.Typeface
import android.os.AsyncTask
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.sunanda.wtpharinghata.helper.DigitsInputFilter
import com.sunanda.wtpharinghata.R
import com.sunanda.wtpharinghata.database.DatabaseClient
import com.sunanda.wtpharinghata.database.Task

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
    lateinit var endosulfan: EditText
    lateinit var ethion: EditText
    lateinit var gamma: EditText
    lateinit var isoproturon: EditText
    lateinit var malathion: EditText
    lateinit var methyl: EditText
    lateinit var monocrotophos: EditText
    lateinit var phorate: EditText
    lateinit var bromoform: EditText
    lateinit var dibromochloromethane: EditText
    lateinit var bromochloromethane: EditText
    lateinit var chloroform: EditText

    var myDate = ""
    var wtpname = ""
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
        endosulfan = myView.findViewById(R.id.endosulfan)
        ethion = myView.findViewById(R.id.ethion)
        gamma = myView.findViewById(R.id.gamma)
        isoproturon = myView.findViewById(R.id.isoproturon)
        malathion = myView.findViewById(R.id.malathion)
        methyl = myView.findViewById(R.id.methyl)
        monocrotophos = myView.findViewById(R.id.monocrotophos)
        phorate = myView.findViewById(R.id.phorate)
        bromoform = myView.findViewById(R.id.bromoform)
        dibromochloromethane = myView.findViewById(R.id.dibromochloromethane)
        bromochloromethane = myView.findViewById(R.id.bromochloromethane)
        chloroform = myView.findViewById(R.id.chloroform)


        if (activity!!.intent.hasExtra("DATE")) {
            myDate = activity!!.intent.getStringExtra("DATE")
            wtpname = activity!!.intent.getStringExtra("WTP")
        } else {
            val gson = Gson()
            myTask = gson.fromJson(activity!!.intent.getStringExtra("TASK"), Task::class.java)
            myDate = myTask.entrydate.toString()
            wtpname = myTask.wtpname.toString()
            if (myTask.type == "TREATED WATER") {
                flag = true
                setData()
            }
        }

        alachlor.filters = arrayOf<android.text.InputFilter>(
            DigitsInputFilter(4, 4, 9999.9999)
        )
        atrazine.filters = arrayOf<android.text.InputFilter>(
            DigitsInputFilter(4, 4, 9999.9999)
        )
        aldrin.filters = arrayOf<android.text.InputFilter>(
            DigitsInputFilter(4, 4, 9999.9999)
        )
        alpha.filters = arrayOf<android.text.InputFilter>(
            DigitsInputFilter(4, 4, 9999.9999)
        )
        beta.filters = arrayOf<android.text.InputFilter>(
            DigitsInputFilter(4, 4, 9999.9999)
        )
        butachlor.filters = arrayOf<android.text.InputFilter>(
            DigitsInputFilter(4, 4, 9999.9999)
        )
        chlorpyriphos.filters = arrayOf<android.text.InputFilter>(
            DigitsInputFilter(4, 4, 9999.9999)
        )
        delta.filters = arrayOf<android.text.InputFilter>(
            DigitsInputFilter(4, 4, 9999.9999)
        )
        dichlor.filters = arrayOf<android.text.InputFilter>(
            DigitsInputFilter(4, 4, 9999.9999)
        )
        endosulfan.filters = arrayOf<android.text.InputFilter>(
            DigitsInputFilter(4, 4, 9999.9999)
        )
        ethion.filters = arrayOf<android.text.InputFilter>(
            DigitsInputFilter(4, 4, 9999.9999)
        )
        gamma.filters = arrayOf<android.text.InputFilter>(
            DigitsInputFilter(4, 4, 9999.9999)
        )
        isoproturon.filters = arrayOf<android.text.InputFilter>(
            DigitsInputFilter(4, 4, 9999.9999)
        )
        malathion.filters = arrayOf<android.text.InputFilter>(
            DigitsInputFilter(4, 4, 9999.9999)
        )
        methyl.filters = arrayOf<android.text.InputFilter>(
            DigitsInputFilter(4, 4, 9999.9999)
        )
        monocrotophos.filters = arrayOf<android.text.InputFilter>(
            DigitsInputFilter(4, 4, 9999.9999)
        )
        phorate.filters = arrayOf<android.text.InputFilter>(
            DigitsInputFilter(4, 4, 9999.9999)
        )
        bromoform.filters = arrayOf<android.text.InputFilter>(
            DigitsInputFilter(4, 4, 9999.9999)
        )
        dibromochloromethane.filters = arrayOf<android.text.InputFilter>(
            DigitsInputFilter(4, 4, 9999.9999)
        )
        bromochloromethane.filters = arrayOf<android.text.InputFilter>(
            DigitsInputFilter(4, 4, 9999.9999)
        )
        chloroform.filters = arrayOf<android.text.InputFilter>(
            DigitsInputFilter(4, 4, 9999.9999)
        )

        submit.setOnClickListener {

            val dialogBuilder = AlertDialog.Builder(activity!!)
            dialogBuilder.setMessage("Do you want to save these entry?")
                .setCancelable(false)
                .setPositiveButton("Proceed") { dialog, id ->

                    if (flag)
                        updateTask()
                    else
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

        return myView
    }

    private fun setData() {

        alachlor.setText(myTask.alachlor)
        atrazine.setText(myTask.atrazine)
        aldrin.setText(myTask.aldrin)
        alpha.setText(myTask.alpha_hch)
        beta.setText(myTask.beta_hch)
        butachlor.setText(myTask.butachlor)
        chlorpyriphos.setText(myTask.chlorpyriphos)
        delta.setText(myTask.delta_hch)
        dichlor.setText(myTask.dichlor)
        endosulfan.setText(myTask.endosulfan)
        ethion.setText(myTask.ethion)
        gamma.setText(myTask.gamma)
        isoproturon.setText(myTask.isoproturon)
        malathion.setText(myTask.malathion)
        methyl.setText(myTask.methyl)
        monocrotophos.setText(myTask.monocrotophos)
        phorate.setText(myTask.phorate)
        bromoform.setText(myTask.bromoform)
        dibromochloromethane.setText(myTask.dibromochloromethane)
        bromochloromethane.setText(myTask.bromochloromethane)
        chloroform.setText(myTask.chloroform)
    }

    private fun saveTask() {

        if (alachlor.text.toString().isEmpty() && atrazine.text.toString().isEmpty() && aldrin.text.toString().isEmpty() &&
            alpha.text.toString().isEmpty() && beta.text.toString().isEmpty() && butachlor.text.toString().isEmpty() &&
            chlorpyriphos.text.toString().isEmpty() && delta.text.toString().isEmpty() && dichlor.text.toString().isEmpty() &&
            endosulfan.text.toString().isEmpty() && ethion.text.toString().isEmpty() && gamma.text.toString().isEmpty() &&
            isoproturon.text.toString().isEmpty() && malathion.text.toString().isEmpty() && methyl.text.toString().isEmpty() &&
            monocrotophos.text.toString().isEmpty() && phorate.text.toString().isEmpty() && bromoform.text.toString().isEmpty() &&
            dibromochloromethane.text.toString().isEmpty() && bromochloromethane.text.toString().isEmpty() &&
            chloroform.text.toString().isEmpty()
        ) {
            showMessage("Please enter at least one parameter value", submit)
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
        val endosulfan_str = endosulfan.text.toString().trim()
        val ethion_str = ethion.text.toString().trim()
        val gamma_str = gamma.text.toString().trim()
        val isoproturon_str = isoproturon.text.toString().trim()
        val malathion_str = malathion.text.toString().trim()
        val methyl_str = methyl.text.toString().trim()
        val monocrotophos_str = monocrotophos.text.toString().trim()
        val phorate_str = phorate.text.toString().trim()
        val Bromoform_str = bromoform.text.toString().trim()
        val dibromochloromethane_str = dibromochloromethane.text.toString().trim()
        val bromochloromethane_str = bromochloromethane.text.toString().trim()
        val chloroform_str = chloroform.text.toString().trim()


        class SaveTask : AsyncTask<Void, Void, Void>() {

            override fun doInBackground(vararg voids: Void): Void? {

                //creating a task
                val task = Task()
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
                task.entrydate = myDate

                //adding to database
                DatabaseClient.getInstance(activity!!)
                    .appDatabase
                    .taskDao()
                    .insert(task)
                return null
            }

            override fun onPostExecute(aVoid: Void?) {
                super.onPostExecute(aVoid)
                Toast.makeText(activity!!, "Treated Water Data saved successfully", Toast.LENGTH_LONG).show()
                clearFields()
            }
        }

        val st = SaveTask()
        st.execute()
    }

    private fun updateTask() {

        if (alachlor.text.toString().isEmpty() && atrazine.text.toString().isEmpty() && aldrin.text.toString().isEmpty() &&
            alpha.text.toString().isEmpty() && beta.text.toString().isEmpty() && butachlor.text.toString().isEmpty() &&
            chlorpyriphos.text.toString().isEmpty() && delta.text.toString().isEmpty() && dichlor.text.toString().isEmpty() &&
            endosulfan.text.toString().isEmpty() && ethion.text.toString().isEmpty() && gamma.text.toString().isEmpty() &&
            isoproturon.text.toString().isEmpty() && malathion.text.toString().isEmpty() && methyl.text.toString().isEmpty() &&
            monocrotophos.text.toString().isEmpty() && phorate.text.toString().isEmpty() && bromoform.text.toString().isEmpty() &&
            dibromochloromethane.text.toString().isEmpty() && bromochloromethane.text.toString().isEmpty() &&
            chloroform.text.toString().isEmpty()
        ) {
            showMessage("Please enter at least one parameter value", submit)
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
        val endosulfan_str = endosulfan.text.toString().trim()
        val ethion_str = ethion.text.toString().trim()
        val gamma_str = gamma.text.toString().trim()
        val isoproturon_str = isoproturon.text.toString().trim()
        val malathion_str = malathion.text.toString().trim()
        val methyl_str = methyl.text.toString().trim()
        val monocrotophos_str = monocrotophos.text.toString().trim()
        val phorate_str = phorate.text.toString().trim()
        val Bromoform_str = bromoform.text.toString().trim()
        val dibromochloromethane_str = dibromochloromethane.text.toString().trim()
        val bromochloromethane_str = bromochloromethane.text.toString().trim()
        val chloroform_str = chloroform.text.toString().trim()


        class UpdateTask : AsyncTask<Void, Void, Void>() {

            override fun doInBackground(vararg voids: Void): Void? {

                //creating a task
                val task = Task()
                task.id = myTask.id
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
                task.type = "RAW WATER"
                task.wtpname = wtpname
                task.entrydate = myDate

                //adding to database
                DatabaseClient.getInstance(activity!!)
                    .appDatabase
                    .taskDao()
                    .update(task)
                return null
            }

            override fun onPostExecute(aVoid: Void?) {
                super.onPostExecute(aVoid)
                Toast.makeText(activity!!, "Raw Water Data updated successfully", Toast.LENGTH_LONG).show()
                clearFields()
            }
        }

        val st = UpdateTask()
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
        endosulfan.setText("")
        ethion.setText("")
        gamma.setText("")
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
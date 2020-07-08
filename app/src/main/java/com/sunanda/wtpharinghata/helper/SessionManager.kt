package com.sunanda.wtpharinghata.helper

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class SessionManager @SuppressLint("CommitPrefEdits")

constructor(internal var _context: Context) {

    // Shared Preferences
    internal var pref: SharedPreferences

    internal var editor: Editor

    // Shared pref mode
    internal var PRIVATE_MODE = 0

    val isLoggedIn: Boolean
        get() = pref.getBoolean(KEY_IS_LOGGED_IN, false)

    val dCode: String?
        get() = pref.getString(KEY_DCODE, "dCode")

    val dName: String?
        get() = pref.getString(KEY_DNAME, "dName")

    val keyId: String?
        get() = pref.getString(KEY_ID, "id")

    val prefsDate: String?
        get() = pref.getString(PREFS_DATE, "")


    init {
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = pref.edit()
    }

    fun setLogin(isLoggedIn: Boolean) {
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn)
        editor.commit()
    }

    fun destroyLoginSession() {
        editor.putBoolean(KEY_IS_LOGGED_IN, false)
        editor.clear()
        // commit changes
        editor.commit()
    }

    fun saveArrayList(list: ArrayList<String>) {
        val gson = Gson()
        val json = gson.toJson(list)
        editor.putString("SAMPLE_ID", json)
        editor.apply()
    }

    fun getArrayList(): ArrayList<String> {
        val gson = Gson()
        val json = pref.getString("SAMPLE_ID", null)
        val type = object : TypeToken<ArrayList<String>>() {}.type
        return gson.fromJson(json, type)
    }

    companion object {
        // LogCat tag
        private val TAG = SessionManager::class.java.simpleName

        // Shared preferences file dCode
        private val PREF_NAME = "Instrument"

        private val KEY_IS_LOGGED_IN = "isLoggedIn"
        private val KEY_ID = "id"
        private val KEY_DNAME = "dName"
        private val KEY_DCODE = "dCode"
        private val UTYPE = "type"
        private val KEY_REF = "ref"
        private val PREFS_DATE = "0000-00-00"
    }
}
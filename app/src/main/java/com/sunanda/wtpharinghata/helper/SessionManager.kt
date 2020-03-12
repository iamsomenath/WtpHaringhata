package com.sunanda.wtpharinghata.helper

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor

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
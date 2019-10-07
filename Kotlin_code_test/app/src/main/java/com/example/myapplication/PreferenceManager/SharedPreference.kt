package com.example.myapplication.PreferenceManager

import android.content.Context
import android.content.SharedPreferences

class SharedPreference(val context: Context) {

    private val PREFS_NAME = "userProfile"

    val sharedPref: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)


    /*save strings*/

    fun save(KEY_NAME: String, value: String) {

        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(KEY_NAME, value)
        editor.commit()
    }

    /*get strings*/
    fun getValueString(KEY_NAME: String): String? {

        return sharedPref.getString(KEY_NAME, null)
    }
}
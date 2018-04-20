package com.kindergarden.michau.e_kindergarden

import android.content.Context
import android.preference.PreferenceManager

class SharedPreferencesHelper constructor(context: Context) {
    val APPID_NAME = "APPID"
    val EMPTY_PROP = ""

    val preference = PreferenceManager.getDefaultSharedPreferences(context)

    fun getStringProp(propName: String) : String {
        return preference.getString(propName, EMPTY_PROP)
    }

    fun setProp(propName: String, propValue: String) {
        val editor = preference.edit()
        editor.putString(propName, propValue)
        editor.apply()
    }

}
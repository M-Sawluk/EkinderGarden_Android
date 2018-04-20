package com.kindergarden.michau.e_kindergarden

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_register.*
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject


class RegisterActivity : AppCompatActivity() {
    val PREFERENCES_KEY = "APP_SHRED_PREFS"
    val APP_ID = "APP_ID"
    val httpClient = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        register_button.setOnClickListener { proceedRegister() }
    }

    private fun storeAppId(appId: String) {
        getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE)
                .edit()
                .putString(APP_ID, appId)
                .apply()
    }

    private fun proceedRegister() {
        var url = "http://192.168.0.50:8888/authenticate/" + pesel.text + "/" + name.text + "/" + surname.text + "/" + email.text + "/" + pin1.text
        val request = Request.Builder()
                .url(url)
                .build()

        val policy = StrictMode.ThreadPolicy.Builder()
                .permitAll().build()
        StrictMode.setThreadPolicy(policy)
        val execute = httpClient.newCall(request).execute()
        if (execute.isSuccessful) {
            val body = execute.body()?.string()
            val jObject = JSONObject(body)
            val appId = jObject.getString("data")
            storeAppId(appId)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } else {
            Log.i("FAIL", "FAILED")
        }
    }

}

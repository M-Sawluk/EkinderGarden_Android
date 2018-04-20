package com.kindergarden.michau.e_kindergarden

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    val PREFERENCES_KEY = "APP_SHRED_PREFS"
    val APP_ID = "APP_ID"
    val EMPTY_ID = ""
    var isOpen = false
    val httpClient = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab_add.setOnClickListener { animateFabs() }
        fab_child.setOnClickListener { deleteAppId() }
        register_btn.setOnClickListener { goToRegister() }
        login_btn.setOnClickListener { login(password.text) }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getAppId() : String {
        return getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE)
                .getString(APP_ID, EMPTY_ID)
    }

    private fun deleteAppId() {
        getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE)
                .edit()
                .putString(APP_ID, EMPTY_ID)
                .apply()
    }

    private fun animateFabs() {
        val animationForward = AnimationUtils.loadAnimation(this, R.anim.rotate_forward)
        val fabClose = AnimationUtils.loadAnimation(this, R.anim.fab_close)
        val fabOpen = AnimationUtils.loadAnimation(this, R.anim.fab_open)
        val animateBackWard = AnimationUtils.loadAnimation(this, R.anim.rotate_backward)
        if (isOpen) {
            val appId = getAppId()
            fab_add.startAnimation(animationForward)
            fab_child.startAnimation(fabClose)
            fab_child.hide()
            isOpen = false
        } else {
            fab_add.startAnimation(animateBackWard)
            fab_child.startAnimation(fabOpen)
            fab_child.show()
            isOpen = true
        }
    }

    private fun goToRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun login(pin: Editable) {
        var url = "http://192.168.0.50:8888/authorize/" + getAppId() + "/" + pin
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
            val intent = Intent(this, UserLogon::class.java)
            intent.putExtra("json", jObject.toString())
            startActivity(intent)
        } else {
            Log.i("FAILURES", "LOGIN FAILED")
        }
    }


}

package com.kindergarden.michau.e_kindergarden

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_user_logon.*

class UserLogon : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_logon)
        val stringExtra = intent.getStringExtra("json")
        frame.setText(stringExtra)
    }
}

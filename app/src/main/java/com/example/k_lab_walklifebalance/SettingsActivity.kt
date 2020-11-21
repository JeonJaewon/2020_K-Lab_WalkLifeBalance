package com.example.k_lab_walklifebalance

import android.content.pm.PackageManager
import android.graphics.Typeface
import android.os.Bundle
import android.telephony.SmsManager
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : BaseActivity() {
    lateinit var  bottomNav : BottomNavigationView
    private lateinit var global: Globals

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        init()
        global = this.applicationContext as Globals

        class soundSwitchListener : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(
                buttonView: CompoundButton,
                isChecked: Boolean
            ) {
                if (isChecked) global.setSoundEnable(true) else global.setSoundEnable(false)
            }
        }
        class vibrationSwitchListener : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(
                buttonView: CompoundButton,
                isChecked: Boolean
            ) {
                if (isChecked) global.setVibrationEnabled(true) else global.setVibrationEnabled(false)
            }
        }
        class notificationSwitchListener : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(
                buttonView: CompoundButton,
                isChecked: Boolean
            ) {
                if (isChecked) global.setNotificationEnabled(true) else global.setNotificationEnabled(false)
            }
        }
        class fallMessageSwitchListener : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(
                buttonView: CompoundButton,
                isChecked: Boolean
            ) {
                if (isChecked) global.setFallMessageEnabled(true) else global.setFallMessageEnabled(false)
            }
        }
        soundset.setOnCheckedChangeListener(soundSwitchListener())
        vibeset.setOnCheckedChangeListener(vibrationSwitchListener())
        pushset.setOnCheckedChangeListener(notificationSwitchListener())
        smsset.setOnCheckedChangeListener(fallMessageSwitchListener())

        var toolbar = settings_toolbar as Toolbar
        toolbar.title = ""
        setSupportActionBar(toolbar)
        sendsmsbtn.setOnClickListener {
            val permission = ContextCompat.checkSelfPermission(this,android.Manifest.permission.SEND_SMS)
            if(permission != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.SEND_SMS),1000)
            }else{
                sendSMS()
            }
        }

    }
    fun sendSMS(){
        val phoneNumber = telephone.text
        val smsManager = SmsManager.getDefault()
        val sendMessage = "I'm in a falling accident situation.\nPlease deal with the situation after checking it."
        smsManager.sendTextMessage(phoneNumber.toString(),null, sendMessage,null,null)
        Toast.makeText(this,"Send Complete",Toast.LENGTH_SHORT)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            1000 ->{
                if(grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this,"Permission denied",Toast.LENGTH_SHORT)
                }else{
                    sendSMS()
                }
            }
        }
    }

    fun init(){
        var items = resources.getStringArray(R.array.textsize)
        var myAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
        spinner_textsize.adapter = myAdapter

        spinner_textsize.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                //TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when(position){
                    0->{}
                    1->{}
                }
            }

        }
    }
}

package com.example.k_lab_walklifebalance

import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.SmsManager
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : BaseActivity() {
    lateinit var  bottomNav : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        init()

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

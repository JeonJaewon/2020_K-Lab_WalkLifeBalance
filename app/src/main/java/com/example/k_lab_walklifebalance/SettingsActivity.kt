package com.example.k_lab_walklifebalance

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_help.*
import kotlinx.android.synthetic.main.activity_settings.*
import java.util.jar.Manifest

class SettingsActivity : BaseActivity() {
    lateinit var  bottomNav : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        init()

        var toolbar = settings_toolbar as Toolbar
        toolbar.title = ""
        setSupportActionBar(toolbar)

        // TODO: 하단바 삭제. 문제 없을시 지워버리셈
//        bottomNav = settings_bottom_nav as BottomNavigationView
//        bottomNav.setOnNavigationItemSelectedListener {
//            super.onNavigationItemSelected(it)
//        }
        // TODO(어떤 bootom nav 아이템이 선택되어 있게 할지)
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
        smsManager.sendTextMessage(phoneNumber.toString(),null,"hello",null,null)
        Toast.makeText(this,"보내기 완료",Toast.LENGTH_SHORT)
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
                    Toast.makeText(this,"권한 거부됨",Toast.LENGTH_SHORT)
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

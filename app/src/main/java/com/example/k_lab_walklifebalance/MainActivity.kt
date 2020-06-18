package com.example.k_lab_walklifebalance

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import app.akexorcist.bluetotohspp.library.BluetoothSPP
import app.akexorcist.bluetotohspp.library.BluetoothSPP.BluetoothConnectionListener
import app.akexorcist.bluetotohspp.library.BluetoothState
import app.akexorcist.bluetotohspp.library.DeviceList
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity(){

    private lateinit var bt: BluetoothSPP
    lateinit var  bottomNav : BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initBluetooth()

        var toolbar = main_toolbar as Toolbar
        toolbar.title = ""
        setSupportActionBar(toolbar)

        bottomNav = main_bottom_nav as BottomNavigationView
        bottomNav.setOnNavigationItemSelectedListener {
            super.onNavigationItemSelected(it)
        }
        bottomNav.selectedItemId = R.id.home_menu

        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragment_container, HomeFragment()).commit()
        supportFragmentManager.popBackStack()
    }

    private fun initBluetooth() {
        bt = BluetoothSPP(this) // 초기화
        if(!bt.isBluetoothEnabled){ // 블루투스 사용 불가능일시
            Toast.makeText(getApplicationContext(),"Bluetooth is not available",Toast.LENGTH_SHORT).show();
            finish();
        }
        val tmp = mpuX
        val tmp2 = mpuY
        val tmp3 = mpuZ
        val tmp4 = load_cell
        bt.setOnDataReceivedListener { data, message ->
            //데이터 수신
//            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            var array = message.split(",")
            tmp.text = array[0].toString()
            tmp2.text = array[1].toString()
            tmp3.text = array[2].toString()
            tmp4.text = array[3].toString()

        }
        bt.setBluetoothConnectionListener(object : BluetoothConnectionListener {
            //연결됐을 때
            override fun onDeviceConnected(name: String, address: String) {
                Toast.makeText(
                    applicationContext
                    , "Connected to $name\n$address"
                    , Toast.LENGTH_SHORT
                ).show()
            }

            override fun onDeviceDisconnected() { //연결해제
                Toast.makeText(
                    applicationContext
                    , "Connection lost", Toast.LENGTH_SHORT
                ).show()
            }

            override fun onDeviceConnectionFailed() { //연결실패
                Toast.makeText(
                    applicationContext
                    , "Unable to connect", Toast.LENGTH_SHORT
                ).show()
            }
        })

        val btBtn = btnConnect // 연결 시도
        btBtn.setOnClickListener{
            if (bt.serviceState == BluetoothState.STATE_CONNECTED) {
                bt.disconnect()
            } else {
                val intent = Intent(applicationContext, DeviceList::class.java)
                startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        bt.stopService()
    }

    override fun onStart() {
        super.onStart()
        if (!bt.isBluetoothEnabled) { //
            val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(intent, BluetoothState.REQUEST_ENABLE_BT)
        } else {
            if (!bt.isServiceAvailable) {
                bt.setupService()
                bt.startService(BluetoothState.DEVICE_OTHER) //DEVICE_ANDROID는 안드로이드 기기 끼리
                setup()
            }
        }
    }

    fun setup() {
//        val btnSend: Button = findViewById(R.id.btnSend) //데이터 전송
//        btnSend.setOnClickListener(object : OnClickListener() {
//            fun onClick(v: View?) {
//                bt.send("Text", true)
//            }
//        })
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
            if (resultCode == Activity.RESULT_OK) bt.connect(data)
        } else if (requestCode == BluetoothState.REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                bt.setupService()
                bt.startService(BluetoothState.DEVICE_OTHER)
                setup()
            } else {
                Toast.makeText(
                    applicationContext
                    , "Bluetooth was not enabled."
                    , Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }


}


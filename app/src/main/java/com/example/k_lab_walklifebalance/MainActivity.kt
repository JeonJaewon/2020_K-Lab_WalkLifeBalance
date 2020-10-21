package com.example.k_lab_walklifebalance

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import app.akexorcist.bluetotohspp.library.BluetoothSPP
import app.akexorcist.bluetotohspp.library.BluetoothSPP.BluetoothConnectionListener
import app.akexorcist.bluetotohspp.library.BluetoothState
import app.akexorcist.bluetotohspp.library.DeviceList
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : BaseActivity(){
    private lateinit var bt: BluetoothSPP
    private lateinit var storageManager: StorageManager
    lateinit var bottomNav : BottomNavigationView
    private var receivedData = listOf<String>()
    val todayDate = SimpleDateFormat("yyyy-MM-dd/", Locale.getDefault()).format(Date())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initBluetooth()
        initStorageManager()
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

    private fun initStorageManager() {
        storageManager = StorageManager(this)
        storageManager.initLocalStorage()
    }

    private fun initBluetooth() {
        bt = BluetoothSPP(this) // 초기화
        if(!bt.isBluetoothEnabled){ // 블루투스 사용 불가능일시
            Toast.makeText(applicationContext,"Bluetooth is not available",Toast.LENGTH_SHORT).show();
            finish();
        }

        bt.setOnDataReceivedListener { data, message ->
            receivedData = message.split(",") // 여기서 배열에 저장
        }
        bt.setBluetoothConnectionListener(object : BluetoothConnectionListener {
            //연결됐을 때
            override fun onDeviceConnected(name: String, address: String) {
                Toast.makeText(
                    applicationContext
                    , "Connected to $name\n$address"
                    , Toast.LENGTH_SHORT
                ).show()
                val handler = Handler()
                handler.postDelayed(object : Runnable {
                    override fun run() {
                        if(receivedData.isNotEmpty()){
                            storageManager.writeLocalStorage(receivedData)
                            val s = storageManager.readLocalStorageForDay(todayDate)
                            Toast.makeText(applicationContext,
                                s[0].toString()+"/"+s[1].toString()+"/"+s[2].toString()+"/"
                                        +s[3].toString()+"/"+s[4].toString()+"/"+s[5].toString()+"/"
                                        +s[6].toString()+"/",
                                Toast.LENGTH_SHORT).show()
                        }
                        handler.postDelayed(this, 5000)//1 sec delay
                    }
                }, 0)
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

//        val btBtn = btnConnect // 연결 시도
//        btBtn.setOnClickListener{
//            if (bt.serviceState == BluetoothState.STATE_CONNECTED) {
//                bt.disconnect()
//            } else {
//                val intent = Intent(applicationContext, DeviceList::class.java)
//                startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE)
//            }
//        }
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


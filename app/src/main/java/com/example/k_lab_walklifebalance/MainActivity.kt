package com.example.k_lab_walklifebalance

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.content.pm.PackageManager
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Bundle
import android.os.Handler
import android.telephony.SmsManager
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import app.akexorcist.bluetotohspp.library.BluetoothSPP
import app.akexorcist.bluetotohspp.library.BluetoothSPP.BluetoothConnectionListener
import app.akexorcist.bluetotohspp.library.BluetoothState
import app.akexorcist.bluetotohspp.library.DeviceList
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_settings.*
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : BaseActivity(){
    private var isfirstGetData = true
    private var shapeNumber = -1
    private var fallIndex = -1
    var fragment = HomeFragment()
    private lateinit var gaitAnalyticsManager: GaitAnalyticsManager
    private lateinit var loadAnalyticsManager: LoadAnalyticsManager
    private lateinit var strideAnalyticsManager: StrideAnalyticsManager
    private var loadPercentData = MutableList<Double>(3) {0.0}
    private lateinit var bt: BluetoothSPP
    private lateinit var storageManager: StorageManager
    lateinit var bottomNav: BottomNavigationView
    private var receivedData = listOf<String>()
    private lateinit var global: Globals
    val todayDate = SimpleDateFormat("yyyy-MM-dd/", Locale.getDefault()).format(Date())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initBluetooth()
        initManager()
        global = this.applicationContext as Globals
        var toolbar = main_toolbar as Toolbar
        toolbar.title = ""
        setSupportActionBar(toolbar)

        checkPermission()

        bottomNav = main_bottom_nav as BottomNavigationView
        bottomNav.setOnNavigationItemSelectedListener {
            super.onNavigationItemSelected(it)
        }
        bottomNav.selectedItemId = R.id.home_menu
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragment_container, HomeFragment()).commit()
        supportFragmentManager.popBackStack()
    }

    private fun initManager(){
        storageManager = StorageManager(this)
        storageManager.initLocalStorage()
        gaitAnalyticsManager = GaitAnalyticsManager()
        loadAnalyticsManager = LoadAnalyticsManager()
        strideAnalyticsManager = StrideAnalyticsManager()
    }

    private fun checkPermission(){
        val permission = ContextCompat.checkSelfPermission(this,android.Manifest.permission.SEND_SMS)
        if(permission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.SEND_SMS),1000)
        }
    }

    private fun sendSMS(){
        val phoneNumber = global.getPhoneNumber()
        val smsManager = SmsManager.getDefault()
        val sendMessage = "I'm in a falling accident situation.\nPlease deal with the situation after checking it."
        smsManager.sendTextMessage(phoneNumber.toString(),null, sendMessage,null,null)
        Toast.makeText(this,"Send Complete",Toast.LENGTH_SHORT)
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
                Log.e("connection","Success")
                val handler = Handler()
                handler.postDelayed(object : Runnable {
                    override fun run() {
                        if(receivedData.isNotEmpty()){
                            storageManager.writeLocalStorage(receivedData)
                            val s = storageManager.readLocalStorage()
                            val LoadData = storageManager.readLocalStorage()
                            if(isfirstGetData) {
                                gaitAnalyticsManager.setStandardData(s[0])
                                strideAnalyticsManager.setStandardData(s[2])
                                isfirstGetData = !isfirstGetData
                            } else {
                                loadPercentData = loadAnalyticsManager.Calculate(LoadData[3],LoadData[4],LoadData[5],LoadData[6])
                                global.setLoadData(loadPercentData)
                                shapeNumber = gaitAnalyticsManager.checkGaitShape(s[0])
                                fallIndex = strideAnalyticsManager.checkFall(s[2])
                                Log.e("폴인", fallIndex.toString())

                                when(shapeNumber){
                                    0 -> {
                                        global.setGaitShape(0)
                                        Toast.makeText(applicationContext,"정상",Toast.LENGTH_SHORT).show()
                                    }
                                    1 -> {
                                        global.setGaitShape(1)
                                        Toast.makeText(applicationContext,"팔자",Toast.LENGTH_SHORT).show()
                                    }
                                    2 -> {
                                        global.setGaitShape(2)
                                        Toast.makeText(applicationContext,"안짱",Toast.LENGTH_SHORT).show()
                                    }
                                }
                                when(fallIndex){
                                    0 -> {
                                        Toast.makeText(applicationContext,"낙상이 아닙니다",Toast.LENGTH_SHORT).show()
                                    }
                                    1 -> {
                                        sendSMS()
                                        Toast.makeText(applicationContext,"낙상!",Toast.LENGTH_SHORT).show()
                                        val toneGen1 = ToneGenerator(AudioManager.STREAM_MUSIC, 1000)
                                        toneGen1.startTone(ToneGenerator.TONE_CDMA_ABBR_ALERT, 450)
                                        val transaction = supportFragmentManager.beginTransaction()
                                        transaction.replace(R.id.fragment_container, HomeFragment())
                                        transaction.commit()
                                    }
                                }
                            }
                        }
                        handler.postDelayed(this, 100)//1 sec delay
                    }
                }, 3000)
            }

            override fun onDeviceDisconnected() { //연결해제
                Toast.makeText(
                    applicationContext
                    , "Connection lost", Toast.LENGTH_SHORT
                ).show()
                Log.e("connection","disconnect")
            }

            override fun onDeviceConnectionFailed() { //연결실패
                Toast.makeText(
                    applicationContext
                    , "Unable to connect", Toast.LENGTH_SHORT
                ).show()
                Log.e("connection","fail")
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


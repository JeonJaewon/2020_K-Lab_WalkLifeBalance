package com.example.k_lab_walklifebalance

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.widget.Toolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_help.*
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

        bottomNav = settings_bottom_nav as BottomNavigationView
        bottomNav.setOnNavigationItemSelectedListener {
            super.onNavigationItemSelected(it)
        }
        // TODO(어떤 bootom nav 아이템이 선택되어 있게 할지)

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
                    2->{}
                    3->{}
                    4->{}
                    5->{}
                    6->{}
                    7->{}
                }
            }

        }
    }
}

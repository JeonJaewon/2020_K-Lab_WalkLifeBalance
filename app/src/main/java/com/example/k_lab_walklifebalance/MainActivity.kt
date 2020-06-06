package com.example.k_lab_walklifebalance

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(){
    lateinit var  bottomNav : BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var toolbar = main_toolbar as Toolbar
        toolbar.title = ""
        setSupportActionBar(toolbar)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragment_container, HomeFragment())
        transaction.commit()
        bottomNav = main_bottom_nav as BottomNavigationView
        bottomNav.setOnNavigationItemSelectedListener {
            super.onNavigationItemSelected(it)
        }
        bottomNav.selectedItemId = R.id.home_menu
    }


}

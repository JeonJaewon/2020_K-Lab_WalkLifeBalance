package com.example.k_lab_walklifebalance

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_help.*
import kotlinx.android.synthetic.main.activity_main.*

class HelpActivity : BaseActivity()  {
    lateinit var  bottomNav : BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)

        var toolbar = help_toolbar as Toolbar
        toolbar.title = ""
        setSupportActionBar(toolbar)

        // TODO: 하단바 삭제. 문제 없을시 지워버리셈
//        bottomNav = help_bottom_nav as BottomNavigationView
//        bottomNav.setOnNavigationItemSelectedListener {
//            super.onNavigationItemSelected(it)
//        }
        // TODO(어떤 bootom nav 아이템이 선택되어 있게 할지)
    }
}

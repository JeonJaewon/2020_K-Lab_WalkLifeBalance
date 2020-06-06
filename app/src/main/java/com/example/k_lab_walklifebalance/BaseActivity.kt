package com.example.k_lab_walklifebalance

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView

// 툴바, 바텀네비게이션 사용하는 액티비티는 baseActivity를 상속받아서 구현
open class BaseActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    // 상단 툴바 코드
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.tool_bar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.help_menu -> {
                Toast.makeText(this,"help", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.settings_menu -> {
                Toast.makeText(this,"setting", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item : MenuItem): Boolean {
        when(item.itemId){
            R.id.statistics_menu ->{
                val fm = supportFragmentManager
                val trans = fm.beginTransaction()
                // 동일한 이름의 클래스가 backstack에 있을시 삭제하는 구문
                fm.popBackStack(this.javaClass.simpleName, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                trans.replace(
                    R.id.fragment_container,
                    StatisticsFragment()
                ).commit()
                trans.addToBackStack(this.javaClass.simpleName)
                return true
            }
            R.id.home_menu ->{
                val fm = supportFragmentManager
                val trans = fm.beginTransaction()
                fm.popBackStack(this.javaClass.simpleName, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                trans.replace(
                    R.id.fragment_container,
                    HomeFragment()
                ).commit()
                trans.addToBackStack(this.javaClass.simpleName)
                return true
            }
            R.id.profile_menu ->{
                val fm = supportFragmentManager
                val trans = fm.beginTransaction()
                fm.popBackStack(this.javaClass.simpleName, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                trans.replace(
                    R.id.fragment_container,
                    ProfileFragment()
                ).commit()
                trans.addToBackStack(this.javaClass.simpleName)
                return true
            }
            else->{ return false }
        }
    }
}
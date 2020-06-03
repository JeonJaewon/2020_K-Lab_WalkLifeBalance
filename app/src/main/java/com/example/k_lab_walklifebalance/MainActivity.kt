package com.example.k_lab_walklifebalance

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.tool_bar.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 상단바 툴바 지정해주고 제목 숨기기
        val toolbar = tool_bar
        toolbar.title = ""
        setSupportActionBar(toolbar)
    }

    // 상단 툴바 코드
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.tool_bar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.help_menu -> {
                Toast.makeText(this,"help",Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.settings_menu -> {
                Toast.makeText(this,"setting",Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

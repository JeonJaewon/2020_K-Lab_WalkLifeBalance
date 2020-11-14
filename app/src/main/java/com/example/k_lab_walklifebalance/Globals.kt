package com.example.k_lab_walklifebalance

import android.app.Application

class Globals : Application() {
    private var gaitShape = 0
    fun getGaitShape(): Int {
        return gaitShape
    }

    fun setGaitShape(value: Int) {
        gaitShape = value
    }
}
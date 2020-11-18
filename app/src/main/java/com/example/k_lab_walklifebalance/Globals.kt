package com.example.k_lab_walklifebalance

import android.app.Application

class Globals : Application() {
    private var gaitShape = 0
    private val LoadPercentData = MutableList<Double>(3) { 0.0 }

    fun setLoadData(Data:MutableList<Double>){
        for(i in 0..Data.size - 1){
            LoadPercentData[i] = Data[i]
        }
    }
    fun getLoadData():MutableList<Double>{
        return LoadPercentData
    }

    fun getGaitShape(): Int {
        return gaitShape
    }

    fun setGaitShape(value: Int) {
        gaitShape = value
    }
}
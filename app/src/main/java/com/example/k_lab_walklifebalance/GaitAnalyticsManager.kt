package com.example.k_lab_walklifebalance

import android.util.Log
import android.widget.Toast

class GaitAnalyticsManager(): AnalyticsManager(){
    private var standardData = -1.0
    private var shapeNumbering = -1

    fun setStandardData(stdData:Double){
        standardData = stdData
    }

    // numbering 0 : 올바름
    // numbering 1 : 팔자걸음
    // numbering 2 : 안짱걸음
    fun checkGaitShape(liveData:Double):Int{
        if(Math.abs(liveData - standardData) <= 5.0)
            shapeNumbering = 0
         else if(liveData - standardData > 5.0)
            shapeNumbering = 1
         else if(liveData - standardData < 5.0)
            shapeNumbering = 2
        Log.e("data list", liveData.toString())
        Log.e("data list", standardData.toString())
        return shapeNumbering
    }
}
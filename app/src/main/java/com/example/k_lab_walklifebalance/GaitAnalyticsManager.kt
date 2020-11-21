package com.example.k_lab_walklifebalance

import android.util.Log
import android.widget.Toast
import kotlin.math.abs

class GaitAnalyticsManager(): AnalyticsManager(){
    private var standardData = -1.0
    private var shapeNumbering = -1
    private var tempDataList = Array<Double>(20,{0.0})
    private var nList = 0
    fun setStandardData(stdData:Double){
        standardData = stdData
    }
    // numbering 0 : 올바름
    // numbering 1 : 팔자걸음
    // numbering 2 : 안짱걸음
    fun checkGaitShape(liveData:Double):Int {
        tempDataList[nList++] = liveData
        shapeNumbering = -1
        if (nList == 20){
            var dataStr = ""
            var avg = 0.0
            for (i in 0..19) {
                dataStr += tempDataList[i].toString() + " / "
                avg += tempDataList[i]
            }
            avg /= 20.0
            var absGap = avg-standardData
            Log.e("2초간 데이터 수집 리스트", dataStr)
            Log.e("기준 데이터", standardData.toString())
            Log.e("평균", absGap.toString())
            if(absGap >= - 6.0 && absGap <= 6.0)
                shapeNumbering = 0
            else if(absGap > 6.0)
                shapeNumbering = 1
            else if(absGap < - 6.0)
                shapeNumbering = 2

            nList = 0
        }
        return shapeNumbering
    }
}
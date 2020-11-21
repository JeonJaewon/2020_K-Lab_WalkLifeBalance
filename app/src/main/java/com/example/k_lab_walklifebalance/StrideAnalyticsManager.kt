package com.example.k_lab_walklifebalance

import android.util.Log
import kotlin.math.abs

class StrideAnalyticsManager: AnalyticsManager(){
    private val FALL_CNT_STANDARD = 15 // 낙상 가능성 횟수가 이 이상이면 낙상으로 처리
    private val FALL_WEIGHT_STANDARD = 30  // 낙상으로 처리할 수치
    private val FALL_TIME_STANDARD = 50  //5초
    private var standardData = -1.0
    private var tempDataList = Array<Double>(FALL_TIME_STANDARD) {0.0}
    private var fallPossibilityCnt = 0
    private var nList = 0

    fun setStandardData(stdData: Double){
        standardData = stdData
    }
    fun checkFall(liveData: Double): Int{
        var ret = -1
        if(abs(standardData - liveData) > FALL_WEIGHT_STANDARD)
            fallPossibilityCnt++
        if (nList == FALL_TIME_STANDARD){
            if (fallPossibilityCnt < FALL_CNT_STANDARD)
                ret = 0
            else
                ret = 1
            nList = 0
            fallPossibilityCnt = 0
        }
        nList++
        return ret
    }
}
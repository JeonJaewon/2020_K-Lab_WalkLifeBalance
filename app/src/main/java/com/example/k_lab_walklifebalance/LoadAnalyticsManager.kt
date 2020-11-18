package com.example.k_lab_walklifebalance

import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class LoadAnalyticsManager(): AnalyticsManager() {

    // num 0, num2 : 좌우
    // num 1 : 앞
    // num 3 : 뒤
    fun Calculate(num0:Double,num1:Double,num2:Double,num3:Double):ArrayList<Double>{
        var temp = (num0 + num2) / 2.0
        var front = (num1 / (temp + num1 + num3)) * 100
        var middle = (temp / (temp + num1 + num3)) * 100
        var back = (num3 / (temp + num1 + num3)) * 100
        var data = arrayListOf<Double>(front,middle,back)
        return data
    }


}
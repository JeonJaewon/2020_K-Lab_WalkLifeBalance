package com.example.k_lab_walklifebalance

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.lang.ArithmeticException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.round

class StorageManager(val mContext: Context) {
    fun initLocalStorage() {
        // 내부 저장소에 기본 위치 저장
        val os = mContext.openFileOutput("SENSOR_DATA_STORAGE", AppCompatActivity.MODE_APPEND)
    }

    fun writeLocalStorage(datas: List<String>) {

        val os = mContext.openFileOutput("SENSOR_DATA_STORAGE", AppCompatActivity.MODE_APPEND)
        val bw = BufferedWriter(OutputStreamWriter(os))
        // 모든 구분자는 / 입니다.
        val date: String = SimpleDateFormat("yyyy-MM-dd/", Locale.getDefault()).format(Date())
        bw.write(date)
        for (data in datas) {
            if (data != " ")
                bw.write(data + "/")
        }
        bw.write("\n") // 끝에 개행해줌
        bw.flush()
    }

    fun readLocalStorageForDay(todayDate: String): Array<Double> { // SimpleDateFormat("yyyy/MM/dd/") 으로 넘겨주면 해당 날짜의 값들을 평균내서 리턴
        val file = mContext.getFileStreamPath("SENSOR_DATA_STORAGE")
        var retList = arrayOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
        if (file != null && file.exists()) {
            val os = mContext.openFileInput("SENSOR_DATA_STORAGE")
            val br = BufferedReader(InputStreamReader(os))
            var line = br.readLine()
            var dateForLine: String// 해당 라인의 날짜
            var length = 0
            while (line != null) {
                dateForLine = line.split("/")[0] + "/"
                if (dateForLine == todayDate) { // 오늘 날짜인것만 반복
                    val tmpList = line.split("/").slice(1..7).map { it.toDouble() }
                        .toTypedArray() // 날짜 제외한 숫자값 7개만
                    for (i in 0..6) {
                        retList[i] += tmpList[i]
                    }
                    length++
                }
                line = br.readLine()
            }
            try {
                retList = retList.map { roundForSecondPlace(it / length) }.toTypedArray() // 평균내기 소수점 아래 두번째자리에서 반올림
            } catch (e: ArithmeticException) { // division by zero
                e.printStackTrace()
            }
        }
        return retList
    }

    fun roundForSecondPlace(input: Double): Double { //소수점 아래 두자리에서 반올림
        return round(input * 1000) / 1000
    }

}
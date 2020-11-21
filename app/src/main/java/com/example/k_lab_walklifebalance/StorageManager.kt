package com.example.k_lab_walklifebalance

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.round

class StorageManager(val mContext: Context) {
    lateinit var storageName:String
    fun initLocalStorage() {
        // 내부 저장소에 기본 위치 저장
        val os = mContext.openFileOutput("SENSOR_DATA_STORAGE", AppCompatActivity.MODE_APPEND)
    }

    fun initLocalStorage(name:String) {
        // 내부 저장소에 기본 위치 저장
        storageName = name
        val os = mContext.openFileOutput(name, AppCompatActivity.MODE_APPEND)
        val bw = BufferedWriter(OutputStreamWriter(os))
        bw.write(0)
        bw.flush()
    }

    fun writeStepToLocalStorage(data: Int){
        val os2 = mContext.openFileInput(storageName)
        val br = BufferedReader(InputStreamReader(os2))

        var line = br.read()

        val os = mContext.openFileOutput(storageName, AppCompatActivity.MODE_PRIVATE) // private으로 열 때 기존 파일이 날라갑니다. . .
        val bw = BufferedWriter(OutputStreamWriter(os))

        Log.e("데이터",line.toString())
        var stepNum = line.toInt() + data

        bw.write(stepNum)
        bw.flush()
    }
    fun readStep():Int{
        val os = mContext.openFileInput(storageName)
        val br = BufferedReader(InputStreamReader(os))
        var line = br.read()
        return line.toInt()
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

    fun readLocalStorage(): Array<Double> { // SimpleDateFormat("yyyy/MM/dd/") 으로 넘겨주면 해당 날짜의 값들을 평균내서 리턴
        val file = mContext.getFileStreamPath("SENSOR_DATA_STORAGE")
        var retList: Array<Double> = arrayOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
        if (file != null && file.exists()) {
            val os = mContext.openFileInput("SENSOR_DATA_STORAGE")
            val br = BufferedReader(InputStreamReader(os))
            var line = br.readLine()
            var lastLine: String? = ""
            while (line != null) {
                lastLine = line
                line = br.readLine()
            }
            if (lastLine != null){
                retList = lastLine.split("/").slice(1..7).map { it.toDouble() }
                    .toTypedArray() // 날짜 제외한 숫자값 7개만
            }
        }
        return retList
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
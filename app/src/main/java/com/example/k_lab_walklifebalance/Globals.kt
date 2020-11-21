package com.example.k_lab_walklifebalance

import android.app.Application

class Globals : Application() {
    private var gaitShape = 0
    private var isUserFall = false
    private var phoneNumber = ""
    private val LoadPercentData = MutableList<Double>(3) { 0.0 }
    private var soundEnabled = true
    private var vibrationEnabled = true
    private var notificationEnabled = true
    private var fallMessageEnabled = true
    private var biggerFontEnable = false
    private val feedBacks = mutableListOf<FeedBack>(FeedBack("Your gait is very balanced", true), FeedBack("Your load balance is very balanced", true))

    fun setPhoneNumber(phone:String){
        phoneNumber = phone
    }

    fun getPhoneNumber():String{
        return phoneNumber
    }

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

    fun getIsUserFall(): Boolean {
        return isUserFall
    }
    fun setIsUserFall(value: Boolean) {
        isUserFall = value
    }

    fun getSoundEnabled(): Boolean {
        return soundEnabled
    }
    fun setSoundEnable(value: Boolean) {
        soundEnabled = value
    }

    fun getVibrationEnabled(): Boolean {
        return vibrationEnabled
    }
    fun setVibrationEnabled(value: Boolean) {
        vibrationEnabled = value
    }

    fun getNotificationEnabled(): Boolean {
        return notificationEnabled
    }
    fun setNotificationEnabled(value: Boolean) {
        notificationEnabled = value
    }

    fun getFallMessageEnabled(): Boolean {
        return fallMessageEnabled
    }
    fun setFallMessageEnabled(value: Boolean) {
        fallMessageEnabled = value
    }

    fun getBiggerFontEnable(): Boolean{
        return biggerFontEnable
    }
    fun setBiggerFontEnable(value: Boolean){
        biggerFontEnable = value
    }

    fun getFeedBacks(): MutableList<FeedBack>{
        return feedBacks
    }
    fun setFeedBacks(index: Int, value: FeedBack){
        feedBacks[index] = value
    }
}
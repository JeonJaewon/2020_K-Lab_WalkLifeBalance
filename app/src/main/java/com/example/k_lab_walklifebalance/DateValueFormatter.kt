package com.example.k_lab_walklifebalance

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import java.util.*

class DateValueFormatter : ValueFormatter(), IAxisValueFormatter {
    override fun getFormattedValue(value: Float, axis: AxisBase?): String {
        return Date(value.toLong()).toString()
    }
}

package com.example.k_lab_walklifebalance

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable

class LoadingDialog(context: MainActivity) : Dialog(context){

    init {
        setCanceledOnTouchOutside(false)

        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        setContentView(R.layout.dialog_loading)
    }
}
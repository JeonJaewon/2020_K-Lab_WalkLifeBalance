package com.example.k_lab_walklifebalance

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_stand.*
import java.util.logging.Logger.global

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [StandFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StandFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var global :Globals

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        global = activity?.applicationContext as Globals
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_stand, container, false);
        var frontValue = v.findViewById<TextView>(R.id.front_value);
        var middleValue = v.findViewById<TextView>(R.id.middle_value);
        var endValue = v.findViewById<TextView>(R.id.end_value);
        var dataList = global.getLoadData()
        Log.e("main activity 값 : ", dataList[0].toString() +" / "+ dataList[1].toString() +" / "+
                dataList[2].toString())
        val frontTmp = Math.round(dataList[0]*10)/10f
        val midTmp = Math.round(dataList[1]*10)/10f
        val endTmp = Math.round(dataList[2]*10)/10f
        if (frontTmp > 60.0 || midTmp > 60.0 || endTmp > 60.0){
            global.setFeedBacks(1, FeedBack("Your load balance is unbalanced", false))
        } else {
            global.setFeedBacks(1, FeedBack("Your load balance is balanced", true))
        }

        frontValue.text = (frontTmp).toString()+"%"
        middleValue.text = (midTmp).toString()+"%"
        endValue.text = (endTmp).toString()+"%"
        return v
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment StandFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            StandFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
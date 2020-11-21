package com.example.k_lab_walklifebalance

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [StrideFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StrideFragment : Fragment() {
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_stride, container, false);
        var strideText = v.findViewById<TextView>(R.id.stride_text)
        var descriptionText = v.findViewById<TextView>(R.id.description_text)
        var strideImage = v.findViewById<ImageView>(R.id.stride_image)
        var isUserFall = global.getIsUserFall()
        when(isUserFall){
            false -> {
                strideText.text = "Detecting Fall"
                strideImage.setImageResource(R.drawable.detect_pic)
                descriptionText.text = resources.getString(R.string.fallDetectingDescription)
            }
            true -> {
                strideText.text = "Fall Accident"
                strideImage.setImageResource(R.drawable.fall_pic)
                descriptionText.text = resources.getString(R.string.fallDetectedDescription)
            }
        }
        return v
        // Inflate the layout for this fragment

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment StrideFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            StrideFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
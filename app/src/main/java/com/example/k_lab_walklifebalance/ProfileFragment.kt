package com.example.k_lab_walklifebalance

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private lateinit var global :Globals

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        val v = inflater.inflate(R.layout.fragment_profile, container, false);
        var gaitImg = v.findViewById<ImageView>(R.id.thumb_gait)
        var loadImg = v.findViewById<ImageView>(R.id.thumb_load)
        var gaitText = v.findViewById<TextView>(R.id.feedback_text_gait)
        var loadText = v.findViewById<TextView>(R.id.feeback_text_load)
        val feedBacks = global.getFeedBacks()
        gaitText.text = feedBacks[0].text
        loadText.text = feedBacks[1].text
        if (feedBacks[0].isGood)
            gaitImg.setImageResource(R.drawable.ic_baseline_thumb_up_24)
        else
            gaitImg.setImageResource(R.drawable.ic_baseline_thumb_down_24)
        if (feedBacks[1].isGood)
            loadImg.setImageResource(R.drawable.ic_baseline_thumb_up_24)
        else
            loadImg.setImageResource(R.drawable.ic_baseline_thumb_down_24)
        return v
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
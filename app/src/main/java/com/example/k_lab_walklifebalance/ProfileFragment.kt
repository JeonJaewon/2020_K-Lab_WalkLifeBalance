package com.example.k_lab_walklifebalance

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*

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

    @Suppress("UNREACHABLE_CODE")
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_profile, container, false);
        var gaitImg = v.findViewById<ImageView>(R.id.thumb_gait)
        var loadImg = v.findViewById<ImageView>(R.id.thumb_load)
        var gaitText = v.findViewById<TextView>(R.id.feedback_text_gait)
        var loadText = v.findViewById<TextView>(R.id.feedback_text_load)
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

        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_profile, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    @SuppressLint("SetTextI18n")
    private fun init() {
        button.setOnClickListener {
            var builder = AlertDialog.Builder(context)
            builder.setTitle("Edit Profile")

            var v1 = layoutInflater.inflate(R.layout.dialog_custom,null)
            builder.setView(v1)

            var listener = DialogInterface.OnClickListener { p0, p1 ->
                var alert = p0 as AlertDialog
                var name: EditText? = alert.findViewById<EditText>(R.id.name_edit)
                var male: CheckBox? = alert.findViewById<CheckBox>(R.id.male)
                //var female: CheckBox? = alert.findViewById<CheckBox>(R.id.female)
                var age: EditText? = alert.findViewById<EditText>(R.id.age_edit)
                var height: EditText? = alert.findViewById<EditText>(R.id.height_edit)
                var weight: EditText? = alert.findViewById<EditText>(R.id.weight_edit)
                var sex=""

                if (male != null) {
                    if(male.isChecked)
                        sex = "male"
                    else
                        sex="female"
                }
                user_name.text = "${name?.text}"
                user_sex.text = sex
                user_age.text = "${age?.text} years old"
                user_height.text = "${height?.text}cm"
                user_weight.text = "${weight?.text}kg"
            }

            builder.setPositiveButton("OK",listener)
            builder.setNegativeButton("Cancel",null)

            builder.show()
        }
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
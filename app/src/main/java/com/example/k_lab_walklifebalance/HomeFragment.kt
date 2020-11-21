package com.example.k_lab_walklifebalance

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    var angleFragment = AngleFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val trans = childFragmentManager.beginTransaction()
        trans.add(R.id.home_fragment_container, StrideFragment())
        trans.commit()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        stride_btn.setOnClickListener {
            val trans = childFragmentManager.beginTransaction()
            trans.replace(
                R.id.home_fragment_container,
                StrideFragment()
            )
            trans.addToBackStack(null)
            trans.commit()
            title_text.text = "Fall detection"
        }
        gait_btn.setOnClickListener {
            var bundle = arguments
            var angleFragment = AngleFragment()
            if(bundle != null) {
                var shapeNumber = bundle?.getInt("index")
                bundle.putInt("index",shapeNumber)
            }
            angleFragment.arguments = bundle

            val trans = childFragmentManager.beginTransaction()
            trans.replace(
                R.id.home_fragment_container,
                angleFragment
            )
            trans.addToBackStack(null)
            trans.commit()
            title_text.text = "Gait"
        }
        load_btn.setOnClickListener {
            val trans = childFragmentManager.beginTransaction()
            trans.replace(
                R.id.home_fragment_container,
                StandFragment()
            )
            trans.addToBackStack(null)
            trans.commit()
            title_text.text = "Load"
        }
    }

}
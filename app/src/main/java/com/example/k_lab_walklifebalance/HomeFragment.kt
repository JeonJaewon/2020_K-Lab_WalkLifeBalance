package com.example.k_lab_walklifebalance

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        //기본 프래그먼트인 보폭으로 초기화
        val trans = childFragmentManager.beginTransaction()
        trans.add(R.id.home_fragment_container, StrideFragment())
        trans.commit()

        stride_btn.setOnClickListener {
            val trans = childFragmentManager.beginTransaction()
            trans.replace(
                R.id.home_fragment_container,
                StrideFragment()
            )
            trans.addToBackStack(null)
            trans.commit()
            title_text.text = "Stride"
        }
        gait_btn.setOnClickListener {
            val trans = childFragmentManager.beginTransaction()
            trans.replace(
                R.id.home_fragment_container,
                AngleFragment()
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
package com.example.k_lab_walklifebalance

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.fragment_statistics.*
import kotlin.concurrent.thread
import kotlin.random.Random

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [StatisticsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StatisticsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    var floatdata = 35.4f
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_statistics, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment StatisticsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            StatisticsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun init() {
        var items = resources.getStringArray(R.array.statistics_data)
        var myAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, items)


        spinner_data.adapter = myAdapter

        spinner_data.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                //TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when(position){
                    0->{
                        setChart()
                        statistics_data.text = "Monthly"
                    }
                    1->{
                        setChart()
                        statistics_data.text = "Weekly"
                    }
                    2->{
                        setChart()
                        statistics_data.text = "Daily"
                    }
                }
            }
        }
    }

    fun setChart(){
        val xAxis = chart.xAxis
        xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM       //x축 데이터의 위치를 아래로
            textSize = 10f                              //텍스트 크기지정
            setDrawGridLines(false)                     //배경 그리드 라인 세팅
            granularity = 1f                            //x축 데이터 표시 간격
            axisMinimum = 3f                            //x축 데이터의 최소 표시값
            isGranularityEnabled = true                 //x축 간격을 제한하는 세분화 기능
        }
        chart.apply {
            axisRight.isEnabled = false                 //y축의 오른쪽 데이터 비활성화
            axisLeft.axisMaximum = 50f                  //y축의 왼쪽 데이터 최대값은 50
            legend.apply {                              //범례 세팅
                textSize = 15f
                verticalAlignment = Legend.LegendVerticalAlignment.TOP
                horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
                orientation = Legend.LegendOrientation.HORIZONTAL
                setDrawInside(false)
            }
            val lineData = LineData()
            chart.data = lineData
            addEntry()
        }
    }

    fun addEntry(){
        val data = chart.data
        data?.let {
            var set : ILineDataSet? = data.getDataSetByIndex(0)
            //임의의 데이터셋 (0번 부터 시작)
            if(set == null){
                set = createSet()
                data.addDataSet(set)
            }
            data.addEntry(Entry(3f,36.8f), 0)
            data.addEntry(Entry(4f,46.9f), 0)
            data.addEntry(Entry(5f,30f), 0)
            //데이터 엔트리 추가 Entry(x값,y값)
            data.notifyDataChanged()
            chart.apply {
                notifyDataSetChanged()
                setVisibleXRangeMaximum(5f)
                setPinchZoom(false)
                isDoubleTapToZoomEnabled = false
                description.text = ""
                setBackgroundColor(Color.WHITE)
                description.textSize = 15f
                setExtraOffsets(8f, 16f, 8f, 16f)
            }
        }
    }

    fun createSet(): LineDataSet{
        val set = LineDataSet(null, "workrate")
        set.apply {
            axisDependency = YAxis.AxisDependency.LEFT
            color = Color.GREEN
            valueTextSize = 10f
            lineWidth = 2f
            circleRadius = 3f
            fillAlpha = 0
            fillColor = Color.WHITE
            highLightColor = Color.WHITE
            setDrawValues(true)
        }
        return set
    }
}
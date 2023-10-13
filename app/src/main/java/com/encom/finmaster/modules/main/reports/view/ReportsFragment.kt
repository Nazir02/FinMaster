package com.encom.finmaster.modules.main.reports.view

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.encom.finmaster.R
import com.encom.finmaster.modules.main.profile.vm.ProfileViewModel
import com.encom.finmaster.modules.main.reports.vm.ReportsViewModel
import com.encom.finmaster.modules.main.settings.profile.view.adapter.ProfileAdapter
import lecho.lib.hellocharts.model.PieChartData
import lecho.lib.hellocharts.model.SliceValue
import lecho.lib.hellocharts.view.PieChartView
import java.util.ArrayList

class ReportsFragment : Fragment() {
    lateinit var pieChartView: PieChartView
    private lateinit var viewModel: ReportsViewModel
    private lateinit var textViewDokhod:TextView
    private lateinit var textViewRaskhod:TextView

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(this).get(ReportsViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_reports, container, false)
textViewDokhod=view.findViewById(R.id.textViewDokhod)
        textViewRaskhod=view.findViewById(R.id.textViewRaskhod)
        pieChartView = view.findViewById(R.id.chart)

        val dokhod:Double=viewModel.getAllIncomeMoney()
        val raskhod:Double=viewModel.getAllOutcomeMoney()
        val total:Double=dokhod+raskhod
        textViewDokhod.text="$dokhod TJS"
        textViewRaskhod.text="$raskhod TJS"

        val pieData : MutableList<SliceValue> = ArrayList()
        pieData.add(SliceValue(raskhod.toFloat(), Color.RED).setLabel("$raskhod расход"))
        pieData.add(SliceValue(dokhod.toFloat(), Color.BLUE).setLabel("$dokhod доход"))



        val pieChartData : PieChartData = PieChartData(pieData)
        pieChartData.setHasLabels(true).valueLabelTextSize = 14
        pieChartData.setHasCenterCircle(true).setCenterText1("Общий $total").setCenterText1FontSize(20).centerText1Color =
            Color.parseColor("#0097A7")
        pieChartView.pieChartData = pieChartData;


        return view
    }





}
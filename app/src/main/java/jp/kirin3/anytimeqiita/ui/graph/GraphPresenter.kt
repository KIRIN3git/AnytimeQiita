package jp.kirin3.anytimeqiita.ui.graph

import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import jp.kirin3.anytimeqiita.R
import jp.kirin3.anytimeqiita.ui.stocks.GraphRepository

class GraphPresenter(
    private val graphRepository: GraphRepository,
    private val graphView: GraphContract.View
) : GraphContract.Presenter {

    init {
        graphView.presenter = this
    }

    companion object {
        const val BAR_FORMATTED = "%.0f"
        const val BAR_LABEL = "リーディング時間"
    }

    override fun setGraph() {
        val xAxis = listOf<String>(
            "2020/09/01",
            "2020/09/02",
            "2020/09/03",
            "2020/09/05",
            "2020/09/07",
            "2020/09/09",
            "2020/09/10"
        )

        val yAxis = listOf(32f, 58f, 23f, 11f, 0f, 34f, 12f)

        //①Entryにデータ格納
        var entryList = mutableListOf<BarEntry>()
        for (i in xAxis.indices) {
            entryList.add(
//                BarEntry(x[i], y[i])
                BarEntry(i.toFloat(), yAxis[i], xAxis[i])
            )
        }

        //BarDataSetのリスト
        val barDataSets = mutableListOf<IBarDataSet>()

        //②DataSetにデータ格納
        val barDataSet = BarDataSet(entryList, BAR_LABEL)

        //③DataSetのフォーマット指定
        barDataSet.color = R.color.lightBlue2
        barDataSet.valueTextSize = 15f
        barDataSet.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return BAR_FORMATTED.format(value)
            }
        }

        //リストに格納
        barDataSets.add(barDataSet)

        //④BarDataにBarDataSet格納
        val barData = BarData(barDataSets)

        graphView.showGraph(barData, xAxis)
    }
}


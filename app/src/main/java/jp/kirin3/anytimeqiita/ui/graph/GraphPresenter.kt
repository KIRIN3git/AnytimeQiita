package jp.kirin3.anytimeqiita.ui.graph

import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import jp.kirin3.anytimeqiita.R
import jp.kirin3.anytimeqiita.data.ReadingTimeData
import jp.kirin3.anytimeqiita.ui.stocks.GraphDataSource
import jp.kirin3.anytimeqiita.ui.stocks.GraphRepository
import kirin3.jp.mljanken.util.LogUtils.LOGI
import kirin3.jp.mljanken.util.TimeUtils

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
        const val DAY_AGO = -7
    }

    override fun loadGraph() {
        graphRepository.getBetweenReadingTime(
            DAY_AGO,
            object : GraphDataSource.LoadTaskListCallback {
                override fun onGraphListLoaded(readingTimeDataList: List<ReadingTimeData>) {
                    setBarData(readingTimeDataList)
                }

                override fun onDataNotAvailable() {
                    // no-op
                }
            })
    }

    fun setBarData(readingTimeDataList: List<ReadingTimeData>) {

        var xAxis = mutableListOf<String>()
        var yAxis = mutableListOf<Float>()
//
        for (readingTimeData in readingTimeDataList) {
            xAxis.add(TimeUtils.getStringFromDate(readingTimeData.date))
            yAxis.add(readingTimeData.minute.toFloat())
            LOGI("" + readingTimeData.date + " " + readingTimeData.minute.toFloat())
        }

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


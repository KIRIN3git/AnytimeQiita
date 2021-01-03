package jp.kirin3.anytimeqiita.ui.graph

import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import jp.kirin3.anytimeqiita.R
import jp.kirin3.anytimeqiita.data.ReadingTimeData
import jp.kirin3.anytimeqiita.ui.record.ViewPagerMember
import jp.kirin3.anytimeqiita.ui.stocks.GraphDataSource
import jp.kirin3.anytimeqiita.ui.stocks.GraphRepository
import kirin3.jp.mljanken.util.TimeUtils
import java.util.*

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
        const val GRAPH_NUM = 7
        const val WEEK_NUM = 7
        const val DAILY_DAYS_AGO = -7
        const val WEEKLY_DAYS_AGO = -70
        const val MONTHLY_DAYS_AGO = -365
        const val MONTH_NUM_TO_GET = 11
    }

    override fun loadGraph() {

        val days_ago = when (GraphFragment.viewPagerMember) {
            ViewPagerMember.WEEKLY.position -> getWeeklyStartDays()
            ViewPagerMember.MONTHLY.position -> getMonthlyStartDays()
            else -> DAILY_DAYS_AGO
        }

        graphRepository.getBetweenReadingTime(
            days_ago,
            object : GraphDataSource.LoadTaskListCallback {
                override fun onGraphListLoaded(readingTimeDataList: List<ReadingTimeData>) {
                    setBarData(readingTimeDataList)
                }

                override fun onDataNotAvailable() {
                    // no-op
                }
            })
    }

    private fun getDailyXAxis(readingTimeDataList: List<ReadingTimeData>): Axis? {
        var xAxis = mutableListOf<String>()
        var yAxis = mutableListOf<Float>()

        for (i in (-GRAPH_NUM + 1)..0) {
            val dayAgoDate = TimeUtils.getHmsClearDate(TimeUtils.getAdditionDate(i)) ?: return null
            xAxis.add(TimeUtils.getStringMmddFromDate(dayAgoDate))
            var isAdded = false
            for (readingTimeData in readingTimeDataList) {
                if (readingTimeData.date == dayAgoDate) {
                    yAxis.add(readingTimeData.minute.toFloat())
                    isAdded = true
                }
            }
            if (!isAdded) {
                yAxis.add(0f)
            }
        }
        return Axis(xAxis, yAxis)
    }

    /**
     * 週表示のグラフデータの開始日が現在日の何日前か取得
     */
    private fun getWeeklyStartDays(): Int {
        return -(TimeUtils.getDayOfTheWeekFromDate(Date()) + ((GRAPH_NUM - 1) * WEEK_NUM))
    }

    /**
     * 月表示のグラフデータの開始日が現在日の何日前か取得
     */
    private fun getMonthlyStartDays(): Int {
        var startDays = 0
        startDays += TimeUtils.getCalendarDateFromDate(Date())
        for (i in 1..MONTH_NUM_TO_GET) {
            startDays += TimeUtils.getDayOfMonthFromDate(Date(), -i)
        }
        return -startDays
    }

    private fun getWeeklyXAxis(readingTimeDataList: List<ReadingTimeData>): Axis? {
        var xAxis = mutableListOf<String>()
        var yAxis = mutableListOf<Float>()

        for (i in getWeeklyStartDays()..0) {
            val dayAgoDate = TimeUtils.getHmsClearDate(TimeUtils.getAdditionDate(i)) ?: return null
            if (TimeUtils.getDayOfTheWeekFromDate(dayAgoDate) != Calendar.SUNDAY) continue
            val dayAgoNextWeekDate =
                TimeUtils.getHmsClearDate(TimeUtils.getAdditionDate(i + WEEK_NUM)) ?: return null
            var yAxisCount = 0f
            xAxis.add(TimeUtils.getStringMmddFromDate(dayAgoDate))
            for (readingTimeData in readingTimeDataList) {
                if (readingTimeData.date in dayAgoDate..dayAgoNextWeekDate) {
                    yAxisCount += readingTimeData.minute.toFloat()
                }
            }
            yAxis.add(yAxisCount)
        }
        return Axis(xAxis, yAxis)
    }

    private fun getMonthlyXAxis(readingTimeDataList: List<ReadingTimeData>): Axis? {
        var xAxis = mutableListOf<String>()
        var yAxis = mutableListOf<Float>()

        for (i in getMonthlyStartDays()..0) {
            val dayAgoDate = TimeUtils.getHmsClearDate(TimeUtils.getAdditionDate(i)) ?: return null
            if (TimeUtils.getCalendarDateFromDate(dayAgoDate) != 1) continue
            val targetMonth = TimeUtils.getCalendarMonthFromDate(dayAgoDate)
            var yAxisCount = 0f
            xAxis.add(TimeUtils.getStringYymmFromDate(dayAgoDate))
            for (readingTimeData in readingTimeDataList) {
                if (TimeUtils.getCalendarMonthFromDate(readingTimeData.date) == targetMonth) {
                    yAxisCount += readingTimeData.minute.toFloat()
                }
            }
            yAxis.add(yAxisCount)
        }
        return Axis(xAxis, yAxis)
    }

    private data class Axis(
        val xAxis: MutableList<String>,
        val yAxis: MutableList<Float>
    )

    fun setBarData(readingTimeDataList: List<ReadingTimeData>) {

        val axis = when (GraphFragment.viewPagerMember) {
            ViewPagerMember.WEEKLY.position -> {
                getWeeklyXAxis(readingTimeDataList)
            }
            ViewPagerMember.MONTHLY.position -> {
                getMonthlyXAxis(readingTimeDataList)
            }
            else -> {
                getDailyXAxis(readingTimeDataList)
            }
        } ?: return

        //①Entryにデータ格納
        var entryList = mutableListOf<BarEntry>()
        for (i in axis.xAxis.indices) {
            entryList.add(
//                BarEntry(x[i], y[i])
                BarEntry(i.toFloat(), axis.yAxis[i], axis.xAxis[i])
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

        graphView.showGraph(barData, axis.xAxis)
    }
}


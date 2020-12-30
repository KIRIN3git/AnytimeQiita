package jp.kirin3.anytimeqiita.ui.graph

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import jp.kirin3.anytimeqiita.R
import jp.kirin3.anytimeqiita.injection.Injection
import jp.kirin3.anytimeqiita.ui.record.RecordFragment
import kirin3.jp.mljanken.util.LogUtils


class GraphFragment : Fragment(), GraphContract.View {

    private lateinit var barChart: BarChart
    override lateinit var presenter: GraphContract.Presenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        LogUtils.LOGD("")

        val root = inflater.inflate(R.layout.fragment_graph, container, false)

        presenter = GraphPresenter(
            Injection.provideGraphRepository(),
            this
        )

        barChart = root.findViewById(R.id.barChart)
        presenter.setGraph()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        arguments?.takeIf { it.containsKey(RecordFragment.VIEW_PAGER_MEMBER_POSITION) }?.apply {
            getInt(RecordFragment.VIEW_PAGER_MEMBER_POSITION).toString()

        }
    }

    override fun showGraph(barData: BarData, xAxis: List<String>) {
        //⑤BarChartにBarData格納
        barChart.apply {
            data = barData
            description.apply {
//                text = "ここ最近の講読時間"
//                textSize = 15f
                isEnabled = false
            }
//            setScaleEnabled(false)
        }

        //⑥Chartのフォーマット指定
        //X軸の設定
        barChart.xAxis.apply {
            isEnabled = true
            textColor = Color.BLACK
            setDrawGridLines(false)
//            setDrawLabels(false)
            position = XAxis.XAxisPosition.BOTTOM
            setDrawGridLines(false)
//            labelCount = 7
            valueFormatter = IndexAxisValueFormatter(xAxis)
        }

        //Y軸（左）の設定
        barChart.axisLeft.apply {
//            setLabelCount(10, false);
            setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
            valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return getString(R.string.word_bar_y_formatted).format(value)
                }
            }
            axisMinimum = 0f
        }

        barChart.axisRight.apply {
            isEnabled = false
        }

        //⑦barChart更新
        barChart.invalidate()
    }
}
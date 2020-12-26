package jp.kirin3.anytimeqiita.ui.graph

import com.github.mikephil.charting.data.BarData
import jp.kirin3.anytimeqiita.presenter.BasePresenter
import jp.kirin3.anytimeqiita.view.BaseView

/**
 * This specifies the contract between the view and the presenter.
 */
interface GraphContract {

    interface View : BaseView<Presenter> {
        fun showGraph(barData: BarData, xAxis: List<String>)
    }

    interface Presenter : BasePresenter {
        fun setGraph()
    }
}

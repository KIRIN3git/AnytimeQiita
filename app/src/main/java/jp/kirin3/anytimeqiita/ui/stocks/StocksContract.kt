package jp.kirin3.anytimeqiita.ui.stocks

import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import jp.kirin3.anytimeqiita.data.StocksResponseData
import jp.kirin3.anytimeqiita.presenter.BasePresenter
import jp.kirin3.anytimeqiita.view.BaseView

/**
 * This specifies the contract between the view and the presenter.
 */
interface StocksContract {

    interface View : BaseView<Presenter> {
        fun showMessage(msg: String)
        fun showStocksRecyclerView(
            stocks: List<StocksResponseData>?
        )
    }

    interface Presenter : BasePresenter {


        fun startLoggedIn(stocksRecyclerView:RecyclerView)
        fun readNextStocks(stocksRecyclerView:RecyclerView)

        fun startNotLoggedIn()

        fun getMessage()
        fun processAccessToken(code: String)
        fun refreshLayout(refreshLayout: SwipeRefreshLayout)
    }
}

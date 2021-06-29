package jp.kirin3.anytimeqiita.ui.stocks

import jp.kirin3.anytimeqiita.data.StocksResponseData
import jp.kirin3.anytimeqiita.model.StocksModel
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

        fun clearStocksRecyclerView()
        fun setRefreshingInterface(flg: Boolean)
    }

    interface Presenter : BasePresenter {
        fun setup(view: View, viewModel: StocksModel)
        fun stop()
        fun handleGettingStockListFromAny()
        fun handleGettingStockListFromApi()
        fun startNotLoggedIn()
        fun getMessage()
        fun processAccessToken(code: String)
    }
}

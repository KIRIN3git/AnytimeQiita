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
        fun showStocksRecyclerView(
            stocks: List<StocksResponseData>?
        )

        fun clearStocksRecyclerView()
        fun showLoadingDialog()
        fun handleLoadingDialog()
    }

    interface Presenter : BasePresenter {
        fun setup(view: View, viewModel: StocksModel)
        fun stop()
        fun handleGettingStockListFromAny()
        fun initGettingStockListFromApi()
        fun continueGettingStockListFromApi()
        fun startNotLoggedIn()
        fun getMessage()
        fun processAccessToken(code: String)
        fun isStockLoadCompleted():Boolean
        fun getLoadedStocksCount():Int
    }
}

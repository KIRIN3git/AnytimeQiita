package jp.kirin3.anytimeqiita.ui.stocks

import io.reactivex.Scheduler
import jp.kirin3.anytimeqiita.data.StocksResponseData
import jp.kirin3.anytimeqiita.model.AuthenticatedUserModel
import jp.kirin3.anytimeqiita.model.StocksModel
import jp.kirin3.anytimeqiita.usecase.StocksUseCase
import kirin3.jp.mljanken.util.LogUtils.LOGE
import javax.inject.Inject
import javax.inject.Named

class StocksPresenter @Inject constructor(
    private val stocksUseCase: StocksUseCase,
    @Named("ui") private val uiScheduler: Scheduler
) : StocksContract.Presenter {

    private lateinit var view: StocksContract.View
    private lateinit var viewModel: StocksModel

    override fun setup(view: StocksContract.View, viewModel: StocksModel) {
        this.view = view
        this.viewModel = viewModel
    }

    override fun handleGettingStockListFromAny() {
        if (stocksUseCase.isLoadCompleted()) {
            view.showStocksRecyclerView(stocksUseCase.getStockListFromDb())
        } else {
            handleGettingStockListFromApi()
        }
    }

    override fun handleGettingStockListFromApi() {
        view.clearStocksRecyclerView()
        resetStocks()
        getStockList()
    }

    private fun resetStocks() {
        stocksUseCase.resetStockListFromDb()
        stocksUseCase.resetPageCount()
    }

    private fun getStockList() {
        view.setRefreshingInterface(true)
        stocksUseCase.loadStockList(AuthenticatedUserModel.getAuthenticatedUserIdFromCache(),
            object : StocksDataSource.LoadTasksCallback {
                override fun onLoadSuccess(stockList: List<StocksResponseData>) {
                    view.showStocksRecyclerView(stockList)
                    getStockList()
                }

                // 取得データがない = 全データ取得完了
                override fun onLoadNoData() {
                    view.setRefreshingInterface(false)
                }

                override fun onLoadFailure() {
                    LOGE("onLoadFailure")
                    view.setRefreshingInterface(false)
                }
            })
    }

    override fun startNotLoggedIn() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun getMessage() {
//       getTasks()
    }


    override fun processAccessToken(code: String) {
    }
}


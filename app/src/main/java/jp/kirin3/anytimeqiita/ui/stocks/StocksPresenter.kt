package jp.kirin3.anytimeqiita.ui.stocks

import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import jp.kirin3.anytimeqiita.data.StocksResponseData
import jp.kirin3.anytimeqiita.database.StocksDatabase
import jp.kirin3.anytimeqiita.model.AuthenticatedUserModel
import jp.kirin3.anytimeqiita.model.StocksModel
import jp.kirin3.anytimeqiita.usecase.StocksUseCase
import kirin3.jp.mljanken.util.LogUtils.LOGE
import kirin3.jp.mljanken.util.LogUtils.LOGI
import javax.inject.Inject
import javax.inject.Named

class StocksPresenter @Inject constructor(
    private val stocksUseCase: StocksUseCase,
    @Named("ui") private val uiScheduler: Scheduler
) : StocksContract.Presenter {

    companion object {
        private const val MAX_STOCKS_PAGE = 10
    }

    private lateinit var view: StocksContract.View
    private lateinit var viewModel: StocksModel
    private val disposables = CompositeDisposable()


    override fun setup(view: StocksContract.View, viewModel: StocksModel) {
        this.view = view
        this.viewModel = viewModel
    }

    override fun stop() {
        disposables.clear()
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
        view.setRefreshingInterface(true)
        getStockList()
    }

    private fun resetStocks() {
        stocksUseCase.resetStockListFromDb()
        stocksUseCase.resetPageCount()
    }

    private fun getStockList() {
        val userId = AuthenticatedUserModel.getAuthenticatedUserIdFromCache() ?: return

        view.setRefreshingInterface(true)
        stocksUseCase.loadStockList(userId)
            .observeOn(uiScheduler)
            .doOnSubscribe {

            }
            .doFinally {
//                stocksUseCase.addOnePageCount()

//                getStockList()
            }
            .doAfterSuccess {
                LOGI("")
            }
            .subscribe({ result ->
                if (result.isEmpty() || stocksUseCase.getPageCount() > MAX_STOCKS_PAGE) {
//                    view.showStocksRecyclerView(stocksUseCase.getStockListFromDb())
//                    view.showStocksRecyclerView(result)
                    stocksUseCase.setStockLoadCompleted(true)
                    view.setRefreshingInterface(false)
                } else {
//                    if (stocksUseCase.getPageCount() == 1) {
                    // 取得データはDBに保存
                    StocksDatabase.insertStocksDataList(result)
                    stocksUseCase.addOnePageCount()
                    view.showStocksRecyclerView(result)
//                    }


                    getStockList()

                }
            }, { e ->
                view.setRefreshingInterface(false)
                LOGE("Failed to load StockList ${e.message}")
            })
            .addTo(disposables)
    }

    private fun getStockListOld() {
        view.setRefreshingInterface(true)
        stocksUseCase.loadStockListOld(AuthenticatedUserModel.getAuthenticatedUserIdFromCache(),
            object : StocksDataSource.LoadTasksCallback {
                override fun onLoadSuccess(stockList: List<StocksResponseData>) {
                    view.showStocksRecyclerView(stockList)
                    getStockListOld()
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


package jp.kirin3.anytimeqiita.ui.stocks

import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import jp.kirin3.anytimeqiita.database.StocksDatabase
import jp.kirin3.anytimeqiita.model.LoginModel
import jp.kirin3.anytimeqiita.model.StocksModel
import jp.kirin3.anytimeqiita.usecase.StocksUseCase
import kirin3.jp.mljanken.util.LogUtils.LOGE
import javax.inject.Inject
import javax.inject.Named

class StocksPresenter @Inject constructor(
    private val stocksUseCase: StocksUseCase,
    @Named("ui") private val uiScheduler: Scheduler
) : StocksContract.Presenter {

    companion object {
        // 無限ロードを防ぐストッパー
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
        if (isStockLoadCompleted()) {
            view.showStocksRecyclerView(stocksUseCase.getStockListFromDb())
        } else {
            initGettingStockListFromApi()
        }
    }

    override fun continueGettingStockListFromApi() {
        getStockList()
    }

    override fun initGettingStockListFromApi() {
        initStocks()
        getStockList()
    }

    private fun initStocks() {
        stocksUseCase.setStockLoadCompleted(false)
        view.clearStocksRecyclerView()
        stocksUseCase.resetStockListFromDb()
        stocksUseCase.resetPageCount()
        viewModel.resetRecyclerViewParcelable()
    }

    private fun getStockList() {
        val userId = LoginModel.getAuthenticatedUserId() ?: return

        stocksUseCase.loadStockList(userId)
            .observeOn(uiScheduler)
            .doOnSubscribe {
            }
            .doFinally {
            }
            .doAfterSuccess {
            }
            .subscribe({ result ->
                if (result.isEmpty() || stocksUseCase.getPageCount() > MAX_STOCKS_PAGE) {
                    stocksUseCase.setStockLoadCompleted(true)
                } else {
                    // 取得データはDBに保存
                    StocksDatabase.insertStocksDataList(result)
                    stocksUseCase.addOnePageCount()
                    view.showStocksRecyclerView(result)
                    // 再起呼び出し
                    getStockList()
                }
                view.handleLoadingDialog()
            }, { e ->
                LOGE("Failed to load StockList ${e.message}")
            })
            .addTo(disposables)
    }

//    private fun getStockListOld() {
//        view.setRefreshingInterface(true)
//        stocksUseCase.loadStockListOld(
//            LoginModel.getAuthenticatedUserId(),
//            object : StocksDataSource.LoadTasksCallback {
//                override fun onLoadSuccess(stockList: List<StocksResponseData>) {
//                    view.showStocksRecyclerView(stockList)
//                    getStockListOld()
//                }
//
//                // 取得データがない = 全データ取得完了
//                override fun onLoadNoData() {
//                    view.setRefreshingInterface(false)
//                }
//
//                override fun onLoadFailure() {
//                    LOGE("onLoadFailure")
//                    view.setRefreshingInterface(false)
//                }
//            })
//    }

    override fun isStockLoadCompleted(): Boolean {
        return stocksUseCase.isLoadCompleted()
    }

    override fun getLoadedStocksCount(): Int {
        return stocksUseCase.getLoadedStocksCount()
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


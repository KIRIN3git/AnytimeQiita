package jp.kirin3.anytimeqiita.ui.stocks

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import jp.kirin3.anytimeqiita.data.StocksResponseData
import jp.kirin3.anytimeqiita.model.AuthenticatedUserModel

class StocksPresenter(
    private val stocksRepository: StocksRepository,
    private val stocksView: StocksContract.View
) : StocksContract.Presenter {

    // FragmentのpresenterにViewを設定
    init {
        stocksView.presenter = this
    }

    override fun getFirstStocks(stocksRecyclerView: RecyclerView) {
        stocksView.setRefreshingIntarface(true)
        stocksRepository.getStocksFromDbOrApi(object : StocksDataSource.LoadTasksCallback {
            override fun onLoadSuccess(stocks: List<StocksResponseData>) {
                stocksView.setRefreshingIntarface(false)
                stocksView.showStocksRecyclerView(stocks)
            }

            override fun onLoadNoData() {
                stocksView.setRefreshingIntarface(false)
            }

            override fun onLoadFailure() {
                stocksView.setRefreshingIntarface(false)
            }
        })
    }

    override fun getNextStocks(stocksRecyclerView: RecyclerView) {
        stocksView.setRefreshingIntarface(true)

        stocksRepository.loadStockList(
            AuthenticatedUserModel.getAuthenticatedUserIdFromCache(),
            false,
            object : StocksDataSource.LoadTasksCallback {
                override fun onLoadSuccess(stocks: List<StocksResponseData>) {
                    stocksView.setRefreshingIntarface(false)
                    stocksView.showStocksRecyclerView(stocks)
                }

                override fun onLoadNoData() {
                    stocksView.setRefreshingIntarface(false)
                }

                override fun onLoadFailure() {
                    stocksView.setRefreshingIntarface(false)
                }
            })
    }

    override fun handleGettingStockList(context: Context?) {
        if (context == null) return
        stocksView.setRefreshingIntarface(true)
        getAllStocks(context, true)
    }

    override fun getAllStocks(context: Context, isFirst: Boolean) {
        stocksRepository.loadStockList(
            AuthenticatedUserModel.getAuthenticatedUserIdFromCache(),
            isFirst,
            object : StocksDataSource.LoadTasksCallback {
                override fun onLoadSuccess(stocks: List<StocksResponseData>) {
                    getAllStocks(context, false)
                }

                // 取得データがない = 全データ取得完了
                override fun onLoadNoData() {
                    stocksRepository.getStocksFromDB().let {
                        stocksView.setRefreshingIntarface(false)
                        stocksView.showStocksRecyclerView(it)
                    }
                }

                override fun onLoadFailure() {
                    stocksView.setRefreshingIntarface(false)
                }
            })
    }


    override fun refreshLayout(
    ) {
//        stocksRepository.loadStockList(
//            AuthenticatedUserModel.getAuthenticatedUserIdFromCache(),
//            true,
//            object : StocksDataSource.LoadTasksCallback {
//                override fun onLoadSuccess(stocks: List<StocksResponseData>) {
//                    stocksView.showStocksRecyclerView(stocks)
//                    stocksView.setRefreshingIntarface(false)
//                }
//
//                override fun onLoadNoData() {
//                    TODO("Not yet implemented")
//                }
//
//                override fun onLoadFailure() {
//                    stocksView.setRefreshingIntarface(false)
//                }
//            })

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


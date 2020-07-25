package jp.kirin3.anytimeqiita.ui.stocks

import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import jp.kirin3.anytimeqiita.data.StocksResponseData
import jp.kirin3.anytimeqiita.database.StocksDatabase
import jp.kirin3.anytimeqiita.model.AuthenticatedUserModel

class StockslPresenter(
    private val stocksRepository: StocksRepository,
    private val stocksView: StocksContract.View
) : StocksContract.Presenter {

    // FragmentのpresenterにViewを設定
    init {
        stocksView.presenter = this
    }

    override fun startLoggedIn(stocksRecyclerView: RecyclerView) {
        stocksRepository.getStocksFromAny(object : StocksDataSource.LoadTasksCallback {
            override fun onStocksLoaded(stocks: List<StocksResponseData>) {
                stocksView.showStocksRecyclerView(stocks)
            }

            override fun onDataNotAvailable() {

            }
        })
    }

    override fun readNextStocks(stocksRecyclerView: RecyclerView) {
        stocksRepository.loadStocks(
            AuthenticatedUserModel.getAuthenticatedUserIdFromCache(),
            false,
            object : StocksDataSource.LoadTasksCallback {
                override fun onStocksLoaded(stocks: List<StocksResponseData>) {
                    stocksView.showStocksRecyclerView(stocks)
                }

                override fun onDataNotAvailable() {
                }
            })
    }

    override fun refreshLayout(
        refreshLayout: SwipeRefreshLayout
    ) {

        stocksRepository.loadStocks(
            AuthenticatedUserModel.getAuthenticatedUserIdFromCache(),
            true,
            object : StocksDataSource.LoadTasksCallback {
                override fun onStocksLoaded(stocks: List<StocksResponseData>) {
                    stocksView.showStocksRecyclerView(stocks)
                    refreshLayout.setRefreshing(false)
                }

                override fun onDataNotAvailable() {
                    refreshLayout.setRefreshing(false)
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


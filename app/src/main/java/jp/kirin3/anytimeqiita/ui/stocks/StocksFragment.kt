package jp.kirin3.anytimeqiita.ui.stocks

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import jp.kirin3.anytimeqiita.R
import jp.kirin3.anytimeqiita.data.StocksResponseData
import jp.kirin3.anytimeqiita.database.StocksDatabase
import jp.kirin3.anytimeqiita.helper.LoginHelper
import jp.kirin3.anytimeqiita.injection.Injection
import jp.kirin3.anytimeqiita.model.StocksModel
import kirin3.jp.mljanken.util.LogUtils.LOGD
import kirin3.jp.mljanken.util.LogUtils.LOGI

class StocksFragment : Fragment(), StocksContract.View, SwipeRefreshLayout.OnRefreshListener,
    StocksRecyclerViewHolder.ItemClickListener {

    private lateinit var refreshLayout: SwipeRefreshLayout
    private lateinit var stocksRecyclerView: RecyclerView
    private lateinit var viewAdapter: StocksRecyclerAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var setAdapterFlg: Boolean = false
    private var nowLoadingFlg: Boolean = false

    override lateinit var presenter: StocksContract.Presenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (context == null) return null

        val root = inflater.inflate(R.layout.fragment_stocks, container, false)

        stocksRecyclerView = root.findViewById(R.id.stocks_recycler_view)

        presenter = StockslPresenter(
            Injection.provideStocksRepository(),
            this
        )

        setRefreshLayout(root)

        return root
    }

    override fun onResume() {
        super.onResume()
        LOGI("")

        if (LoginHelper.isLoginCompleted(context) == true) {
            presenter.startLoggedIn(stocksRecyclerView)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // 位置の保存
        StocksModel.parcelable = stocksRecyclerView.layoutManager?.onSaveInstanceState()
    }

    override fun showMessage(msg: String) {
        LOGI("")
    }

    fun setRefreshLayout(root: View) {
        refreshLayout = root.findViewById(R.id.refresh_layout)
        refreshLayout.setOnRefreshListener(this)
        refreshLayout.setColorSchemeResources(
            R.color.orange2
        )
    }

    override fun showStocksRecyclerView(
        stocks: List<StocksResponseData>?
    ) {
        val ctext = context
        if (ctext == null || stocks == null) return

        // MainThread
        val handler = Handler(Looper.getMainLooper())
        handler.post(Runnable {
            if (setAdapterFlg == true) {
                viewAdapter.addItem(stocks)
            } else {
                viewAdapter = StocksRecyclerAdapter(ctext, this, stocks.toMutableList())
                viewManager = LinearLayoutManager(ctext, LinearLayoutManager.VERTICAL, false)

                stocksRecyclerView.apply {
                    // use a linear layout manager
                    layoutManager = viewManager
                    // specify an viewAdapter (see also next example)
                    adapter = viewAdapter
                    // ラストスクロールリスナー
                    addOnScrollListener(scrollListener())
                }

                // 位置の復元
                stocksRecyclerView.layoutManager?.onRestoreInstanceState(StocksModel.parcelable)
            }

            setAdapterFlg = true
            nowLoadingFlg = false
            refreshLayout.setRefreshing(false)
        })
    }

    private fun clearStocksRecyclerView() {
        viewAdapter.clearItem()
    }

    private inner class scrollListener : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)

            if (!recyclerView.canScrollVertically(1)) {
                if (nowLoadingFlg == false) {
                    nowLoadingFlg = true
                    presenter.readNextStocks(stocksRecyclerView)
                    refreshLayout.setRefreshing(true)
                }
            }
        }
    }

    /**
     * StocksRecyclerViewHolder.ItemClickListener
     */
    override fun onItemClick(view: View, position: Int) {
        Toast.makeText(context, "position $position was tapped", Toast.LENGTH_SHORT).show()
    }

    /**
     * SwipeRefreshLayout.OnRefreshListener
     */
    override fun onRefresh() {
        if (LoginHelper.isLoginCompleted(context) == true && nowLoadingFlg == false) {
            nowLoadingFlg == true
            clearStocksRecyclerView()
            StocksDatabase.deleteStocksDataList()
            presenter.refreshLayout(refreshLayout)
        }
    }
}
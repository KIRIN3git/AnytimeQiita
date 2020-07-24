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
import jp.kirin3.anytimeqiita.helper.LoginHelper
import jp.kirin3.anytimeqiita.injection.Injection
import kirin3.jp.mljanken.util.LogUtils.LOGI

class StocksFragment : Fragment(), StocksContract.View, SwipeRefreshLayout.OnRefreshListener,
    StocksRecyclerViewHolder.ItemClickListener {

    //    private lateinit var stockViewModel: StocksViewModel
    private lateinit var refreshLayout: SwipeRefreshLayout


    private lateinit var stocksRecyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override lateinit var presenter: StocksContract.Presenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (context == null) return null

//        stockViewModel =
//            ViewModelProviders.of(this).get(StocksViewModel::class.java)
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
        viewAdapter = StocksRecyclerAdapter(ctext, this, stocks)
        viewManager = LinearLayoutManager(ctext, LinearLayoutManager.VERTICAL, false)

        stocksRecyclerView.apply {
            // MainThread
            val handler = Handler(Looper.getMainLooper())
            handler.post(Runnable {
                // use a linear layout manager
                layoutManager = viewManager
                // specify an viewAdapter (see also next example)
                adapter = viewAdapter
                // ラストスクロールリスナー
                addOnScrollListener(scrollListener())
            })
        }
    }

    private inner class  scrollListener: RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)

            if (!recyclerView.canScrollVertically(1)) {
                Toast.makeText(activity, "最終行です", Toast.LENGTH_LONG).show()

                presenter.startLoggedIn(stocksRecyclerView)
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

        if (LoginHelper.isLoginCompleted(context) == true) {
            presenter.refreshLayout(refreshLayout)
        }
    }
}
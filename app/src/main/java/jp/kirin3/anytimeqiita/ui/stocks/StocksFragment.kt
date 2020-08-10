package jp.kirin3.anytimeqiita.ui.stocks

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import jp.kirin3.anytimeqiita.R
import jp.kirin3.anytimeqiita.data.FoldersBasicData
import jp.kirin3.anytimeqiita.data.StocksResponseData
import jp.kirin3.anytimeqiita.database.FoldersDatabase
import jp.kirin3.anytimeqiita.database.StocksDatabase
import jp.kirin3.anytimeqiita.helper.LoginHelper
import jp.kirin3.anytimeqiita.injection.Injection
import jp.kirin3.anytimeqiita.model.StocksModel
import jp.kirin3.anytimeqiita.ui.reading.ReadingFragment
import kirin3.jp.mljanken.util.LogUtils.LOGI

class StocksFragment : Fragment(), StocksContract.View, SwipeRefreshLayout.OnRefreshListener,
    StocksRecyclerViewHolder.ItemClickListener {

    private lateinit var refreshLayout: SwipeRefreshLayout
    private lateinit var stocksRecyclerView: RecyclerView
    private var viewAdapter: StocksRecyclerAdapter? = null
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
            refreshLayout.setRefreshing(true)
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
                viewAdapter?.let {
                    it.addItem(stocks)
                }
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
        viewAdapter?.let {
            it.clearItem()
        }
    }

    private inner class scrollListener : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)

            if (!recyclerView.canScrollVertically(1)) {
                if (nowLoadingFlg == false) {
                    nowLoadingFlg = true
                    presenter.readNextStocks(stocksRecyclerView)
                }
            }
        }
    }

    /**
     * StocksRecyclerViewHolder.ItemClickListener
     */
    override fun onItemClick(url: String, position: Int) {

//        Toast.makeText(context, "position $position was tapped $title", Toast.LENGTH_SHORT).show()


        settingPrefectureDialog(url, FoldersDatabase.selectFoldersData())
    }

    fun settingPrefectureDialog(url: String, folders: List<FoldersBasicData>?) {

        var items: MutableList<String>? = null

        folders?.let {
            for (folder in folders) {
                items?.let {
                    items?.add(folder.name)
                } ?: let {
                    items = mutableListOf(folder.name)
                }
            }
        }


        // タイトル部分のTextView
        val paddingLeftRight =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8f, resources.displayMetrics)
                .toInt()
        val paddingTopBottom =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8f, resources.displayMetrics)
                .toInt()
        val textView = TextView(context!!)
        // タイトルの背景色
        textView.setBackgroundColor(ContextCompat.getColor(context!!, R.color.orange))
        // タイトルの文字色
        textView.setTextColor(Color.WHITE)
        textView.layoutParams =
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        textView.setPadding(paddingLeftRight, paddingTopBottom, paddingLeftRight, paddingTopBottom)
        // テキスト
        textView.text = "title"
        // テキストサイズ
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)


        var onReadingClickListener = DialogInterface.OnClickListener { dialog, id ->
            val params = bundleOf(
                ReadingFragment.URL_PARAM_FLG to url,
                ReadingFragment.REFRESH_FLG_PARAM_FLG to true
            )
            findNavController().navigate(R.id.bottom_navigation_reading, params)
        }

        val builder = AlertDialog.Builder(context!!)
        builder.setCustomTitle(textView)
            .setCancelable(false)
            .setPositiveButton("CANCEL", null)
            .setNegativeButton("READING", onReadingClickListener)
        if (items != null) {
            builder.setItems(items?.toTypedArray()) { dialog, which ->
                LOGI("www" + which)
            }
        }
        builder.show()
    }


    override fun setRefreshingIntarface(refreshFlg: Boolean) {
        refreshLayout.setRefreshing(refreshFlg)
    }


    /**
     * SwipeRefreshLayout.OnRefreshListener
     */
    override fun onRefresh() {
        if (LoginHelper.isLoginCompleted(context) == true && nowLoadingFlg == false) {
            nowLoadingFlg == true
            clearStocksRecyclerView()
            StocksDatabase.deleteStocksDataList()
            presenter.refreshLayout()
        }
    }
}
package jp.kirin3.anytimeqiita.ui.stocks

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import jp.kirin3.anytimeqiita.MainApplication
import jp.kirin3.anytimeqiita.R
import jp.kirin3.anytimeqiita.data.FilesData
import jp.kirin3.anytimeqiita.data.StocksResponseData
import jp.kirin3.anytimeqiita.database.FilesDatabase
import jp.kirin3.anytimeqiita.database.FoldersDatabase
import jp.kirin3.anytimeqiita.model.StocksModel
import jp.kirin3.anytimeqiita.source.dialog.StocksDialogFragment
import jp.kirin3.anytimeqiita.source.dialog.StocksDialogParameter
import jp.kirin3.anytimeqiita.ui.reading.LoginModel
import jp.kirin3.anytimeqiita.ui.reading.ReadingFragment
import kirin3.jp.mljanken.util.LogUtils.LOGI
import javax.inject.Inject

class StocksFragment : Fragment(), StocksContract.View, SwipeRefreshLayout.OnRefreshListener,
    StocksRecyclerViewHolder.ItemClickListener, StocksDialogFragment.StocksDialogListener {

    private lateinit var refreshLayout: SwipeRefreshLayout
    private lateinit var stocksRecyclerView: RecyclerView
    private var viewAdapter: StocksRecyclerAdapter? = null
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var hasAdapter: Boolean = false
    private var nowLoadingFlg: Boolean = false


    //override lateinit var presenter: StocksContract.Presenter

    @Inject
    override lateinit var presenter: StocksContract.Presenter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val nonNullActivity = activity ?: return

        val viewModel = ViewModelProviders.of(nonNullActivity).get(StocksModel::class.java)

        MainApplication.component.inject(this)

        presenter.setup(this, viewModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (context == null) return null

        val root = inflater.inflate(R.layout.fragment_stocks, container, false)

        stocksRecyclerView = root.findViewById(R.id.stocks_recycler_view)

        setRefreshLayout(root)

        return root
    }

    override fun onResume() {
        super.onResume()
        LOGI("")

        if (LoginModel.isLoginCompleted(context)) {
            refreshLayout.isRefreshing = true
            presenter.handleGettingFirstStockList()
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

    private fun setRefreshLayout(root: View) {
        refreshLayout = root.findViewById(R.id.refresh_layout)
        refreshLayout.setOnRefreshListener(this)
        refreshLayout.setColorSchemeResources(
            R.color.orange2
        )
    }

    override fun showStocksRecyclerView(
        stockList: List<StocksResponseData>?
    ) {
        val nonNullContext = context ?: return
        if (stockList == null) return

        if (viewAdapter != null) {
            viewAdapter?.let {
                it.addItem(stockList)
            }
        } else {
            viewManager = LinearLayoutManager(nonNullContext, LinearLayoutManager.VERTICAL, false)
            viewAdapter = StocksRecyclerAdapter(nonNullContext, this, stockList.toMutableList())

            stocksRecyclerView.apply {
                layoutManager = viewManager
                adapter = viewAdapter
                // ラストスクロールリスナー
                addOnScrollListener(ScrollListener())
            }

            // 位置の復元
            stocksRecyclerView.layoutManager?.onRestoreInstanceState(StocksModel.parcelable)
        }
        nowLoadingFlg = false
        refreshLayout.isRefreshing = false
    }

    private fun clearStocksRecyclerView() {
        viewAdapter?.let {
            it.clearItem()
        }
    }

    private inner class ScrollListener : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)

            // スクロール末尾に行った時にリロード
//            if (!recyclerView.canScrollVertically(1)) {
//                if (!nowLoadingFlg) {
//                    nowLoadingFlg = true
//                    presenter.getNextStocks(stocksRecyclerView)
//                }
//            }
        }
    }

    /**
     * StocksRecyclerViewHolder.ItemClickListener
     */
    override fun onItemClick(stockId: String, url: String) {
        showStocksDialog(stockId, url)
    }

    override fun onReadNowButtonClick(dialog: DialogFragment, url: String?) {
        val params = bundleOf(
            ReadingFragment.URL_PARAM to url,
            ReadingFragment.IS_REFRESH_WEBVIEW_PARAM to true
        )
        findNavController().navigate(R.id.bottom_navigation_reading, params)
    }

    private fun isNotFileInsert(searchFile: FilesData?): Boolean {
        if (searchFile == null) return true
        val files = FilesDatabase.selectFailsData() ?: return false
        for (file in files) {
            if (file.stocks_id.equals(searchFile.stocks_id) && file.folders_seqid == searchFile.folders_seqid) {
                return true
            }
        }
        return false
    }

    private fun showStocksDialog(stockId: String, url: String) {
        childFragmentManager.beginTransaction().add(
            StocksDialogFragment.newInstance(
                StocksDialogParameter(
                    stockId,
                    url,
                    FoldersDatabase.selectFoldersData(),
                    FilesDatabase.selectFailsData()
                )
            ),
            null
        ).commitNowAllowingStateLoss()
    }


    override fun setRefreshingInterface(refreshFlg: Boolean) {
        refreshLayout.isRefreshing = refreshFlg
    }

    /**
     * SwipeRefreshLayout.OnRefreshListener
     */
    override fun onRefresh() {
//        if (LoginModel.isLoginCompleted(context) && !nowLoadingFlg) {
//            nowLoadingFlg = true
//            clearStocksRecyclerView()
//            StocksDatabase.deleteStocksDataList()
//            presenter.refreshLayout()
//        }
    }
}
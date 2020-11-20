package jp.kirin3.anytimeqiita.ui.stocks

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import jp.kirin3.anytimeqiita.R
import jp.kirin3.anytimeqiita.data.FilesData
import jp.kirin3.anytimeqiita.data.StocksResponseData
import jp.kirin3.anytimeqiita.database.FilesDatabase
import jp.kirin3.anytimeqiita.database.FoldersDatabase
import jp.kirin3.anytimeqiita.database.StocksDatabase
import jp.kirin3.anytimeqiita.injection.Injection
import jp.kirin3.anytimeqiita.model.StocksModel
import jp.kirin3.anytimeqiita.ui.reading.LoginModel
import jp.kirin3.anytimeqiita.ui.reading.ReadingFragment
import jp.kirin3.anytimeqiita.util.AlertDialogFragment
import jp.kirin3.anytimeqiita.util.AlertDialogParameter
import kirin3.jp.mljanken.util.LogUtils.LOGI

class StocksFragment : Fragment(), StocksContract.View, SwipeRefreshLayout.OnRefreshListener,
    StocksRecyclerViewHolder.ItemClickListener,
    AlertDialogFragment.NoticeDialogListener {

    private lateinit var refreshLayout: SwipeRefreshLayout
    private lateinit var stocksRecyclerView: RecyclerView
    private var viewAdapter: StocksRecyclerAdapter? = null
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var setAdapterFlg: Boolean = false
    private var nowLoadingFlg: Boolean = false

    // ダイアログタップ時の保存データ
    private var dialogStockId: String? = null
    private var dialogStockUrl: String? = null
    private var dialogFoldersNameList: MutableList<String>? = null
    private var dialogFoldersSeqidList: MutableList<Int>? = null

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

        if (LoginModel.isLoginCompleted(context) == true) {
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
        val nonNullContext = context ?: return
        if (stocks == null) return

        // MainThread
        val handler = Handler(Looper.getMainLooper())
        //  handler.post(Runnable {
        if (setAdapterFlg == true) {
            viewAdapter?.let {
                it.addItem(stocks)
            }
        } else {
            viewManager = LinearLayoutManager(nonNullContext, LinearLayoutManager.VERTICAL, false)
            viewAdapter = StocksRecyclerAdapter(nonNullContext, this, stocks.toMutableList())

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
    override fun onItemClick(stockId: String, url: String) {

//        Toast.makeText(context, "position $position was tapped $title", Toast.LENGTH_SHORT).show()
        setStocksDialogData(stockId, url)
        showSelectStocksAlertDialog()
        //settingPrefectureDialog(context, url, FoldersDatabase.selectFoldersData())
    }

    private fun setStocksDialogData(stockId: String, url: String) {
        dialogStockId = stockId
        dialogStockUrl = url
        val folders = FoldersDatabase.selectFoldersData()
        dialogFoldersNameList = mutableListOf()
        dialogFoldersSeqidList = mutableListOf()
        folders?.let {
            for (folder in folders) {
                dialogFoldersNameList?.add(folder.name)
                dialogFoldersSeqidList?.add(folder.seqid)
            }
        }
    }

    override fun onDialogPositiveClick(dialog: DialogFragment) {
        val params = bundleOf(
            ReadingFragment.URL_PARAM to dialogStockUrl,
            ReadingFragment.IS_REFRESH_WEBVIEW_PARAM to true
        )
        findNavController().navigate(R.id.bottom_navigation_reading, params)
    }

    override fun onDialogNegativeClick(dialog: DialogFragment) {
        TODO("Not yet implemented")
    }

    override fun onDialogNeutralClick(dialog: DialogFragment) {
        TODO("Not yet implemented")
    }

    override fun onDialogListClick(dialog: DialogFragment, which: Int) {
        var folderSeqid = 0
        dialogFoldersSeqidList?.let {
            folderSeqid = it[which]
        } ?: return

        if (isNotFileInsert(FilesData(folderSeqid, dialogStockId))) return
        FilesDatabase.insertOneFailsDataList(FilesData(folderSeqid, dialogStockId))
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

    private fun showSelectStocksAlertDialog() {
        childFragmentManager.beginTransaction().add(
            AlertDialogFragment.newInstance(
                AlertDialogParameter(
                    title = R.string.message_input_folder_name,
                    titleBackgroundColor = R.color.orange,
                    positiveButtonText = R.string.reading,
                    list = dialogFoldersNameList
                )
            ),
            null
        ).commitNowAllowingStateLoss()
    }


    override fun setRefreshingIntarface(refreshFlg: Boolean) {
        refreshLayout.setRefreshing(refreshFlg)
    }

    /**
     * SwipeRefreshLayout.OnRefreshListener
     */
    override fun onRefresh() {
        if (LoginModel.isLoginCompleted(context) == true && nowLoadingFlg == false) {
            nowLoadingFlg == true
            clearStocksRecyclerView()
            StocksDatabase.deleteStocksDataList()
            presenter.refreshLayout()
        }
    }
}
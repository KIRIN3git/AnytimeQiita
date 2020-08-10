package jp.kirin3.anytimeqiita.ui.folders

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
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
import jp.kirin3.anytimeqiita.R
import jp.kirin3.anytimeqiita.data.FoldersBasicData
import jp.kirin3.anytimeqiita.injection.Injection
import jp.kirin3.anytimeqiita.model.FoldersModel
import jp.kirin3.anytimeqiita.ui.reading.ReadingFragment
import jp.kirin3.anytimeqiita.ui.solders.FoldersRecyclerAdapter
import jp.kirin3.anytimeqiita.ui.stocks.FoldersRecyclerViewHolder
import kirin3.jp.mljanken.util.LogUtils.LOGI
import kirin3.jp.mljanken.util.SettingsUtils
import java.util.*

class FoldersFragment : Fragment(), FoldersContract.View,
    FoldersRecyclerViewHolder.ItemClickListener {

    private lateinit var foldersRecyclerView: RecyclerView
    private var viewAdapter: FoldersRecyclerAdapter? = null
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var setAdapterFlg: Boolean = false
    private var nowLoadingFlg: Boolean = false

    override lateinit var presenter: FoldersContract.Presenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (context == null) return null

        val root = inflater.inflate(R.layout.fragment_folders, container, false)

        foldersRecyclerView = root.findViewById(R.id.folders_recycler_view)

        presenter = FoldersPresenter(
            Injection.provideFoldersRepository(),
            this
        )

        return root
    }

    override fun onResume() {
        super.onResume()
        LOGI("")

        if (SettingsUtils.getCreateFirstFoldersFlg(context) == false) {
            presenter.createFirstFolders(foldersRecyclerView)
            SettingsUtils.setCreateFirstFoldersFlg(context, true)
        }

        presenter.readFolders()
    }

    override fun onDestroy() {
        super.onDestroy()
        // 位置の保存
        FoldersModel.parcelable = foldersRecyclerView.layoutManager?.onSaveInstanceState()
    }

    override fun showMessage(msg: String) {
        LOGI("")
    }


    override fun showFoldersRecyclerView(
        folders: MutableList<FoldersBasicData>?
    ) {
        val ctext = context
        if (ctext == null || folders == null) return

        if (setAdapterFlg == true) {
            viewAdapter?.let {
                it.addItem(folders)
            }
        } else {
            addLastAddFolder(folders)
            viewAdapter = FoldersRecyclerAdapter(ctext, this, folders.toMutableList())
            viewManager = LinearLayoutManager(ctext, LinearLayoutManager.VERTICAL, false)

            foldersRecyclerView.apply {
                // use a linear layout manager
                layoutManager = viewManager
                // specify an viewAdapter (see also next example)
                adapter = viewAdapter
            }

            // 位置の復元
            foldersRecyclerView.layoutManager?.onRestoreInstanceState(FoldersModel.parcelable)
        }

        setAdapterFlg = true
        nowLoadingFlg = false
//        })
    }

    private fun addLastAddFolder(folders:MutableList<FoldersBasicData>){
        val folder:FoldersBasicData = FoldersBasicData(4,"", Date(),true)
        folders.add(folder)
    }

    private fun clearFoldersRecyclerView() {
        viewAdapter?.let {
            it.clearItem()
        }
    }

    /**
     * FoldersRecyclerViewHolder.ItemClickListener
     */
    override fun onItemClick(url: String, position: Int) {

//        Toast.makeText(context, "position $position was tapped $title", Toast.LENGTH_SHORT).show()
        settingPrefectureDialog(url)
    }

    fun settingPrefectureDialog(url: String) {
        val items = arrayOf(
            "北海道（ほっかいどう）", "青森（あおもり）", "岩手（いわて）", "宮城（みやぎ）"
        )
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

        AlertDialog.Builder(context!!)
            .setCustomTitle(textView)
            .setCancelable(false)
            .setItems(items) { dialog, which ->
                //                SettingsUtils.setSettingRadioIdPrefecture(context!!, which + 1)
            }
            .setPositiveButton("CANCEL", null)
            .setNegativeButton("READING", onReadingClickListener)
            .show()
    }
}
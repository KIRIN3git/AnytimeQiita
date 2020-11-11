package jp.kirin3.anytimeqiita.ui.folders

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jp.kirin3.anytimeqiita.R
import jp.kirin3.anytimeqiita.data.FoldersData
import jp.kirin3.anytimeqiita.data.StocksResponseData
import jp.kirin3.anytimeqiita.database.FilesDatabase
import jp.kirin3.anytimeqiita.database.FoldersDatabase
import jp.kirin3.anytimeqiita.database.StocksDatabase
import jp.kirin3.anytimeqiita.injection.Injection
import jp.kirin3.anytimeqiita.model.FoldersModel
import jp.kirin3.anytimeqiita.source.dialog.FoldersDialogFragment
import jp.kirin3.anytimeqiita.source.dialog.FoldersDialogParameter
import jp.kirin3.anytimeqiita.ui.solders.FoldersRecyclerAdapter
import jp.kirin3.anytimeqiita.ui.stocks.FoldersRecyclerViewHolder
import jp.kirin3.anytimeqiita.util.DialogUtils
import kirin3.jp.mljanken.util.LogUtils.LOGI
import kirin3.jp.mljanken.util.SettingsUtils

class FoldersFragment : Fragment(), FoldersContract.View,
    FoldersRecyclerViewHolder.ItemClickListener, FoldersDialogFragment.FolderDialogListener {

    private lateinit var foldersRecyclerView: RecyclerView
    private var viewAdapter: FoldersRecyclerAdapter? = null
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var setAdapterFlg: Boolean = false
    private var nowLoadingFlg: Boolean = false

    // ダイアログデータ
    private var dialogStocksList: MutableList<StocksResponseData>? = null
    private var dialogSeqid: Int? = null
    private var dialogFolderName: String? = null
    private var dialogPosition: Int? = null


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
            presenter.createFirstFolders()
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
        foldersList: MutableList<FoldersData>
    ) {
        val nonNullContext = context ?: return
        val filesList = FilesDatabase.selectFailsData()
        val stocksList = StocksDatabase.selectStocksData()

//        addLastAddFolder(folders)

        viewManager = LinearLayoutManager(nonNullContext, LinearLayoutManager.VERTICAL, false)
        viewAdapter = FoldersRecyclerAdapter(
            nonNullContext,
            this,
            foldersList.toMutableList(),
            filesList,
            stocksList
        )

        foldersRecyclerView.apply {
            // use a linear layout manager
            layoutManager = viewManager
            // specify an viewAdapter (see also next example)
            adapter = viewAdapter
        }

        // 位置の復元
        foldersRecyclerView.layoutManager?.onRestoreInstanceState(FoldersModel.parcelable)

        nowLoadingFlg = false
    }

    private fun clearFoldersRecyclerView() {
        viewAdapter?.let {
            it.clearItem()
        }
    }

    override fun onFolderClick(
        seqid: Int,
        folderName: String,
        position: Int,
        lastItemFlg: Boolean
    ) {
        showFoldersDialog(seqid, folderName, position)
    }

    override fun onLastFolderClick() {
        settingAddEditFolderPrefectureDialog(context)
    }

    private fun setFilesDialogData(seqid: Int, folderName: String, position: Int) {
        dialogSeqid = seqid
        dialogFolderName = folderName
        dialogPosition = position
        dialogStocksList = mutableListOf()
        val filesList = FilesDatabase.selectFailsDataBySeqid(seqid) ?: return

        for (file in filesList) {
            file.stocks_id?.let { stocks_id ->
                StocksDatabase.selectStocksDataById(stocks_id)?.let { stockResponseData ->
                    dialogStocksList?.add(stockResponseData)
                }
            }
        }
    }

    private fun showFoldersDialog(seqid: Int, folderName: String, position: Int) {
        setFilesDialogData(seqid, folderName, position)

        childFragmentManager.beginTransaction().add(
            FoldersDialogFragment.newInstance(
                FoldersDialogParameter(
                    dialogStocksList,
                    seqid,
                    folderName,
                    position
                )
            ),
            null
        ).commitNowAllowingStateLoss()
    }

    /**
     * FoldersDialogFragment.FolderDialogListener
     */
    override fun onDeleteFolderButtonClick(dialog: DialogFragment, folders_seqid: Int) {
        FilesDatabase.deleteFailsDataListByFoldersSeqid(folders_seqid)
        FoldersDatabase.deleteFoldersDataListBySeqid(folders_seqid)
        presenter.readFolders()
    }

    override fun onChangeFolderNameButtonClick(
        dialog: DialogFragment,
        folderName: String,
        position: Int
    ) {
        settingEditFolderPrefectureDialog(context, folderName, position)
    }

    override fun onDeleteFileButtonLongClick(
        dialog: DialogFragment,
        folders_seqid: Int,
        stocks_id: String
    ) {
        val nonNullDialogSeqid = dialogSeqid ?: return
        val nonNullDialogFolderName = dialogFolderName ?: return
        val nonNullDialogPosition = dialogPosition ?: return

        FilesDatabase.deleteFailsDataListByFoldersSeqidAndStocksId(folders_seqid, stocks_id)
        presenter.readFolders()
        showFoldersDialog(nonNullDialogSeqid, nonNullDialogFolderName, nonNullDialogPosition)
    }

    private fun settingEditFolderPrefectureDialog(context: Context?, name: String, position: Int) {

        if (context == null) return

        val textView = DialogUtils.getDialogText(context, resources, "フォルダー", R.color.orange)
        var editText = EditText(context)
        editText.setText(name, TextView.BufferType.NORMAL)
        var onCreateClickListener = getEditFolderDialogClickListener(editText, position)

        val builder = AlertDialog.Builder(context)
        builder.setCustomTitle(textView)
            .setCancelable(false)
            .setPositiveButton("キャンセル", null)
            .setNegativeButton("更新", onCreateClickListener)
            .setView(editText)
            .show()
    }

    private fun getEditFolderDialogClickListener(
        editText: EditText,
        position: Int
    ): DialogInterface.OnClickListener {
        return DialogInterface.OnClickListener { dialog, id ->
            if (editText.text.toString().length > 0) {
                presenter.editFolderName(getNextSeqid(), editText.text.toString(), position)
                presenter.readFolders()
            }
        }
    }

    private fun settingAddEditFolderPrefectureDialog(context: Context?) {

        if (context == null) return

        val textView = DialogUtils.getDialogText(context, resources, "フォルダー", R.color.orange)
        var editText = EditText(context)
        var onCreateClickListener = getAddFolderDialogClickListener(editText)

        val builder = AlertDialog.Builder(context)
        builder.setCustomTitle(textView)
            .setCancelable(false)
            .setPositiveButton("キャンセル", null)
            .setNegativeButton("登録", onCreateClickListener)
            .setView(editText)
            .show()
    }

    private fun getAddFolderDialogClickListener(editText: EditText): DialogInterface.OnClickListener {
        return DialogInterface.OnClickListener { dialog, id ->
            if (editText.text.toString().length > 0) {
                presenter.createNewFolder(getNextSeqid(), editText.text.toString())
                presenter.readFolders()
            }
        }
    }

    private fun getNextSeqid(): Int {
        val seqid = SettingsUtils.getFolderSeqid(context) + 1
        SettingsUtils.setFolderSeqid(context, seqid)
        return seqid
    }
}
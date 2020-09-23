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
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jp.kirin3.anytimeqiita.R
import jp.kirin3.anytimeqiita.data.FoldersBasicData
import jp.kirin3.anytimeqiita.injection.Injection
import jp.kirin3.anytimeqiita.model.FoldersModel
import jp.kirin3.anytimeqiita.ui.solders.FoldersRecyclerAdapter
import jp.kirin3.anytimeqiita.ui.stocks.FoldersRecyclerViewHolder
import jp.kirin3.anytimeqiita.util.DialogUtils
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
        folders: MutableList<FoldersBasicData>?
    ) {
        val ctext = context
        if (ctext == null || folders == null) return

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

        nowLoadingFlg = false
//        })
    }

    private fun addLastAddFolder(folders: MutableList<FoldersBasicData>) {
        val folder: FoldersBasicData = FoldersBasicData(4, "", Date(), true)
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
    override fun onItemClick(name: String, position: Int, add_flg: Boolean) {
        if (add_flg) {
            settingAddFolderPrefectureDialog(context)
        } else {
            settingEditFolderPrefectureDialog(context, name, position)
        }
    }

    private fun settingAddFolderPrefectureDialog(ctext: Context?) {

        if (ctext == null) return

        val textView = DialogUtils.getDialogText(ctext, resources, "フォルダー",R.color.orange)
        var editText = EditText(ctext)
        var onCreateClickListener = getAddFolderDialogClickListener(editText)

        val builder = AlertDialog.Builder(ctext)
        builder.setCustomTitle(textView)
            .setCancelable(false)
            .setPositiveButton("CANCEL", null)
            .setNegativeButton("CREATE", onCreateClickListener)
            .setView(editText)
            .show()
    }

    private fun settingEditFolderPrefectureDialog(ctext: Context?, name: String, position: Int) {

        if (ctext == null) return

        val textView = DialogUtils.getDialogText(ctext, resources, "フォルダー",R.color.orange)
        var editText = EditText(ctext)
        editText.setText(name, TextView.BufferType.NORMAL)
        var onCreateClickListener = getEditFolderDialogClickListener(editText, position)

        val builder = AlertDialog.Builder(ctext)
        builder.setCustomTitle(textView)
            .setCancelable(false)
            .setPositiveButton("キャンセル", null)
            .setNegativeButton("更新", onCreateClickListener)
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

    private fun getNextSeqid(): Int {
        val seqid = SettingsUtils.getFolderSeqid(context) + 1
        SettingsUtils.setFolderSeqid(context, seqid)
        return seqid
    }

}
package jp.kirin3.anytimeqiita.source.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jp.kirin3.anytimeqiita.R
import jp.kirin3.anytimeqiita.data.FilesData
import jp.kirin3.anytimeqiita.data.FoldersData
import jp.kirin3.anytimeqiita.database.FilesDatabase
import jp.kirin3.anytimeqiita.ui.solders.FoldersRecyclerAdapter
import jp.kirin3.anytimeqiita.ui.stocks.FoldersRecyclerViewHolder
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.fragment_folders_dialog.header_text_view
import kotlinx.android.synthetic.main.fragment_stocks_dialog.*

class StocksDialogFragment : DialogFragment(), FoldersRecyclerViewHolder.ItemClickListener {

    private var viewAdapter: FoldersRecyclerAdapter? = null
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var paramFoldersList: List<FoldersData>? = null
    private var paramStocksId: String? = null
    private var paramStocksUrl: String? = null

    companion object {
        private const val PARAMETER = "parameter"

        fun newInstance(parameter: StocksDialogParameter): StocksDialogFragment {
            return StocksDialogFragment().also { fragment ->
                fragment.arguments = Bundle().also { bundle ->
                    bundle.putParcelable(PARAMETER, parameter)
                }
            }
        }
    }

    interface StocksDialogListener {
        fun onReadNowButtonClick(
            dialog: DialogFragment,
            url: String?
        )
    }

    internal var listener: StocksDialogListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        listener = when {
            parentFragment is StocksDialogListener -> parentFragment as StocksDialogListener
            context is StocksDialogListener -> context
            else -> null
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).also { dialog ->
            val nonNullContext: Context =
                context ?: throw java.lang.IllegalStateException("dialog context cannot be null")
            val parameter = arguments?.getParcelable<StocksDialogParameter>(PARAMETER)
                ?: throw java.lang.IllegalStateException("Dialog parameter cannot be null")

            paramFoldersList = parameter.foldersList
            paramStocksId = parameter.stockId
            paramStocksUrl = parameter.stockUrl

            dialog.setContentView(R.layout.fragment_stocks_dialog)

            dialog.header_text_view.text = nonNullContext.getText(R.string.word_register_folder)

            paramFoldersList?.let {
                viewManager =
                    LinearLayoutManager(nonNullContext, LinearLayoutManager.VERTICAL, false)
                viewAdapter = FoldersRecyclerAdapter(
                    nonNullContext,
                    this,
                    it.toMutableList(),
                    parameter.filesList,
                    false
                )

                dialog.dialog_folders_recycler_view.apply {
                    // use a linear layout manager
                    layoutManager = viewManager
                    // specify an viewAdapter (see also next example)
                    adapter = viewAdapter
                }
            }
            dialog.read_now_button.setOnClickListener {
                if (paramStocksUrl == null) return@setOnClickListener

                listener?.onReadNowButtonClick(
                    this,
                    paramStocksUrl
                )
                dismissAllowingStateLoss()
            }

        }
    }

    override fun onFolderClick(seqid: Int, name: String, position: Int, lastItemFlg: Boolean) {
        if (isNotFileInsert(FilesData(seqid, paramStocksId))) return
        FilesDatabase.insertOneFailsData(FilesData(seqid, paramStocksId))
        Toast.makeText(
            context,
            this.getString(R.string.message_add_folder),
            Toast.LENGTH_LONG
        ).show()
        dismissAllowingStateLoss()
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


//    var folderSeqid = 0
//    dialogFoldersSeqidList?.let {
//        folderSeqid = it[which]
//    } ?: return
//
//    if (isNotFileInsert(FilesData(folderSeqid, dialogStockId))) return
//    FilesDatabase.insertOneFailsData(FilesData(folderSeqid, dialogStockId))

    override fun onLastFolderClick() {
        //no-op
    }

}

/**
 * AlertDialogFragmentの引数
 *
 * message設定時にlistは設定できない
 */
@Parcelize
data class StocksDialogParameter(
    val stockId: String? = null,
    val stockUrl: String? = null,
    val foldersList: List<FoldersData>? = null,
    val filesList: List<FilesData>? = null
) : Parcelable
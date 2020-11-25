package jp.kirin3.anytimeqiita.source.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jp.kirin3.anytimeqiita.R
import jp.kirin3.anytimeqiita.data.StocksResponseData
import jp.kirin3.anytimeqiita.ui.reading.ReadingFragment
import jp.kirin3.anytimeqiita.ui.stocks.StocksRecyclerAdapter
import jp.kirin3.anytimeqiita.ui.stocks.StocksRecyclerViewHolder
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.fragment_folders_dialog.*

class FoldersDialogFragment : DialogFragment(), StocksRecyclerViewHolder.ItemClickListener,
    FoldersInterceptRecyclerItemClickListener.OnRecyclerClickListener {

    private var viewAdapter: StocksRecyclerAdapter? = null
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewListener: FoldersInterceptRecyclerItemClickListener.OnRecyclerClickListener
    private var paramStocksList: MutableList<StocksResponseData>? = null

    companion object {
        private const val PARAMETER = "parameter"

        fun newInstance(parameter: FoldersDialogParameter): FoldersDialogFragment {
            return FoldersDialogFragment().also { fragment ->
                fragment.arguments = Bundle().also { bundle ->
                    bundle.putParcelable(PARAMETER, parameter)
                }
            }
        }
    }

    interface FolderDialogListener {
        fun onDeleteFolderButtonClick(
            dialog: DialogFragment,
            folders_seqid: Int
        )

        fun onChangeFolderNameButtonClick(
            dialog: DialogFragment,
            folderName: String,
            position: Int
        )

        fun onFolderButtonLongClick(
            dialog: DialogFragment,
            folders_seqid: Int,
            stocks_id: String
        )
    }

    internal var listener: FolderDialogListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        listener = when {
            parentFragment is FolderDialogListener -> parentFragment as FolderDialogListener
            context is FolderDialogListener -> context
            else -> null
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).also { dialog ->
            val nonNullContext: Context =
                context ?: throw java.lang.IllegalStateException("dialog context cannot be null")
            val parameter = arguments?.getParcelable<FoldersDialogParameter>(PARAMETER)
                ?: throw java.lang.IllegalStateException("Dialog parameter cannot be null")

            paramStocksList = parameter.stocksList
            dialog.setContentView(R.layout.fragment_folders_dialog)

            dialog.header_text_view.text = parameter.folderName
            paramStocksList?.let {
                viewManager =
                    LinearLayoutManager(nonNullContext, LinearLayoutManager.VERTICAL, false)
                viewAdapter = StocksRecyclerAdapter(nonNullContext, this, it)
                viewListener = this

                dialog.dialog_stocks_recycler_view.apply {
                    // use a linear layout manager
                    layoutManager = viewManager
                    // specify an viewAdapter (see also next example)
                    adapter = viewAdapter
                    addOnItemTouchListener(
                        FoldersInterceptRecyclerItemClickListener(
                            nonNullContext,
                            dialog.dialog_stocks_recycler_view,
                            viewListener
                        )
                    )
                }
            }
            dialog.change_folder_name_button.setOnClickListener {
                listener?.onChangeFolderNameButtonClick(
                    this,
                    parameter.folderName,
                    parameter.position
                )
                dismissAllowingStateLoss()
            }
            dialog.delete_folder_button.setOnClickListener {
                listener?.onDeleteFolderButtonClick(
                    this,
                    parameter.seqId
                )
                dismissAllowingStateLoss()
            }
        }
    }

    /**
     * 本来はここにリストのクリック処理を追記するが今回はロングタップ使用のためインターセプトのリスナーを使用
     */
    override fun onItemClick(stockId: String, url: String) {
//        val params = bundleOf(
//            ReadingFragment.URL_PARAM to url,
//            ReadingFragment.IS_REFRESH_WEBVIEW_PARAM to true
//        )
//        findNavController().navigate(R.id.bottom_navigation_reading, params)
//        dismissAllowingStateLoss()
    }

    override fun onItemClick(view: View, position: Int) {
        paramStocksList?.let {

            val params = bundleOf(
                ReadingFragment.URL_PARAM to it[position].url,
                ReadingFragment.IS_REFRESH_WEBVIEW_PARAM to true
            )
            findNavController().navigate(R.id.bottom_navigation_reading, params)
            dismissAllowingStateLoss()
        }
        Log.d("MainActivty", "シングルタップ")
    }

    override fun onItemLongClick(view: View, position: Int) {
        val parameter = arguments?.getParcelable<FoldersDialogParameter>(PARAMETER)
            ?: throw java.lang.IllegalStateException("Dialog parameter cannot be null")

        paramStocksList?.let {
            listener?.onFolderButtonLongClick(
                this,
                parameter.seqId,
                it[position].id
            )
            dismissAllowingStateLoss()
        }
        Log.d("MainActivty", "長押しのタップ")
    }


//
//    override fun onDoubleClick(view: View, position: Int) {
//    }
}

/**
 * AlertDialogFragmentの引数
 *
 * message設定時にlistは設定できない
 */
@Parcelize
data class FoldersDialogParameter(
    val stocksList: MutableList<StocksResponseData>? = null,
    val seqId: Int,
    val folderName: String,
    val position: Int
) : Parcelable

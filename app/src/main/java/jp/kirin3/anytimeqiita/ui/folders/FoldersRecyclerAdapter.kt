package jp.kirin3.anytimeqiita.ui.solders

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jp.kirin3.anytimeqiita.R
import jp.kirin3.anytimeqiita.data.FilesData
import jp.kirin3.anytimeqiita.data.FoldersData
import jp.kirin3.anytimeqiita.data.StocksResponseData
import jp.kirin3.anytimeqiita.ui.stocks.FoldersRecyclerViewHolder

class FoldersRecyclerAdapter(
    private val context: Context,
    private val itemClickListener: FoldersRecyclerViewHolder.ItemClickListener,
    private val foldersList: MutableList<FoldersData>,
    private val filesList: List<FilesData>?,
    private val stocksList: List<StocksResponseData>?
) : RecyclerView.Adapter<FoldersRecyclerViewHolder>() {

    private var recyclerView: RecyclerView? = null

    fun addItem(addList: List<FoldersData>) {
        foldersList.addAll(addList)
        notifyDataSetChanged()
    }

    fun clearItem() {
        foldersList.clear()
        notifyDataSetChanged()
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        this.recyclerView = null
    }

    override fun onBindViewHolder(holder: FoldersRecyclerViewHolder, position: Int) {
        holder?.let {

            if (isLastPosition(position)) {
                it.folderDefaultLayout.visibility = View.GONE
                it.folderAddLayout.visibility = View.VISIBLE
            } else {
                it.numTextView.text = countStocksNum(foldersList[position].seqid).toString()
                it.nameTextView.text = foldersList[position].name
                it.folderDefaultLayout.visibility = View.VISIBLE
                it.folderAddLayout.visibility = View.GONE
            }
        }
    }

    private fun countStocksNum(seqid: Int): Int {
        var count = 0
        if (filesList == null) return 0
        for (file in filesList) {
            if (file.folders_seqid == seqid) {
                count++
            }
        }
        return count
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoldersRecyclerViewHolder {

        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.folders_low, parent, false)

        view.setOnClickListener { view ->
            recyclerView?.let {
                if (isLastPosition(it.getChildAdapterPosition(view))) {
                    itemClickListener.onLastFolderClick()
                } else {
                    itemClickListener.onFolderClick(
                        foldersList[it.getChildAdapterPosition(view)].seqid,
                        foldersList[it.getChildAdapterPosition(view)].name,
                        it.getChildAdapterPosition(view),
                        isLastPosition(it.getChildAdapterPosition(view))
                    )
                }
            }
        }

        return FoldersRecyclerViewHolder(view)
    }

    /** recyclerViewのコンテンツのサイズ
     * 末尾にフォルダ追加用itemを追加
     */
    override fun getItemCount(): Int {
        return foldersList.size + 1
    }

    /**
     * 追加用カード確認
     */
    private fun isLastPosition(position: Int): Boolean {
        return position + 1 == itemCount
    }
}
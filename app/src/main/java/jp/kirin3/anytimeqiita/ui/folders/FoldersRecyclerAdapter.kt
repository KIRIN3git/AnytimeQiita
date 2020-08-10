package jp.kirin3.anytimeqiita.ui.solders

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jp.kirin3.anytimeqiita.R
import jp.kirin3.anytimeqiita.data.FoldersBasicData
import jp.kirin3.anytimeqiita.ui.stocks.FoldersRecyclerViewHolder
import kirin3.jp.mljanken.util.LogUtils.LOGI
import kirin3.jp.mljanken.util.TimeUtils

class FoldersRecyclerAdapter(
    private val context: Context,
    private val itemClickListener: FoldersRecyclerViewHolder.ItemClickListener,
    private val holdersList: MutableList<FoldersBasicData>
) : RecyclerView.Adapter<FoldersRecyclerViewHolder>() {

    private var recyclerView: RecyclerView? = null

    fun addItem(addList: List<FoldersBasicData>) {
        holdersList.addAll(addList)
        notifyDataSetChanged()
    }

    fun clearItem() {
        holdersList.clear()
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

            it.nameTextView.text = holdersList.get(position).name

            if(holdersList.get(position).add_flg == true){
                it.folderDefaultLayout.visibility = View.GONE
                it.folderAddLayout.visibility = View.VISIBLE
            } else{
                it.folderDefaultLayout.visibility = View.VISIBLE
                it.folderAddLayout.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoldersRecyclerViewHolder {

        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.folders_low, parent, false)

        view.setOnClickListener { view ->
            recyclerView?.let {
                itemClickListener.onItemClick(
                    holdersList[it.getChildAdapterPosition(view)].name,
                    it.getChildAdapterPosition(view)
                )
            }
        }

        return FoldersRecyclerViewHolder(view)
    }

    // recyclerViewのコンテンツのサイズ
    override fun getItemCount(): Int {
        return holdersList.size
    }


}
package jp.kirin3.anytimeqiita.ui.stocks

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.folders_low.view.*
import kotlinx.android.synthetic.main.stocks_low.view.*

class FoldersRecyclerViewHolder(view:View): RecyclerView.ViewHolder(view){

    interface ItemClickListener {
        fun onItemClick(url:String, position: Int)
    }
    var nameTextView = view.nameTextView
}
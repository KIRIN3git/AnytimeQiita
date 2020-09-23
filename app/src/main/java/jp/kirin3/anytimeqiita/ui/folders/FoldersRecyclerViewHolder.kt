package jp.kirin3.anytimeqiita.ui.stocks

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.folders_low.view.*

class FoldersRecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    interface ItemClickListener {
        fun onItemClick(url: String, position: Int, add_flg: Boolean)
    }

    var nameTextView = view.nameTextView
    var folderDefaultLayout = view.folder_default_lauout
    var folderAddLayout = view.folder_add_lauout

}
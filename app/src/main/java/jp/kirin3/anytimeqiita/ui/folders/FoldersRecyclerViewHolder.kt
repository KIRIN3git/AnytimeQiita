package jp.kirin3.anytimeqiita.ui.stocks

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.folders_low.view.*

class FoldersRecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    interface ItemClickListener {
        fun onFolderClick(seqid:Int,name: String, position: Int, lastItemFlg: Boolean)
        fun onLastFolderClick()
    }

    var nameTextView = view.nameTextView
    var numTextView = view.numTextView
    var folderDefaultLayout = view.folder_default_lauout
    var folderAddLayout = view.folder_add_lauout

}
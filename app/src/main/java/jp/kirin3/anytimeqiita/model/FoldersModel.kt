package jp.kirin3.anytimeqiita.model

import android.os.Parcelable
import androidx.lifecycle.ViewModel
import jp.kirin3.anytimeqiita.data.FoldersData

class FoldersModel() : ViewModel() {

    companion object {

        var parcelable: Parcelable? = null

        private var cacheFoldersList: List<FoldersData>? = null

        fun setFoldersToCache(foldersList: List<FoldersData>?) {
            cacheFoldersList = foldersList
        }

        fun getFoldersFromCache():List<FoldersData>?{
            return cacheFoldersList
        }

    }
}
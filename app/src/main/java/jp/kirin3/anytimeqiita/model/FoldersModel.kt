package jp.kirin3.anytimeqiita.model

import android.os.Parcelable
import androidx.lifecycle.ViewModel
import jp.kirin3.anytimeqiita.data.FoldersBasicData

class FoldersModel() : ViewModel() {

    companion object {

        var parcelable: Parcelable? = null

        private var cacheFoldersList: List<FoldersBasicData>? = null

        fun setFoldersToCache(foldersList: List<FoldersBasicData>?) {
            cacheFoldersList = foldersList
        }

    }
}
package jp.kirin3.anytimeqiita.database

import io.realm.Realm
import io.realm.kotlin.where
import jp.kirin3.anytimeqiita.data.FilesData

object FilesDatabase {

    private const val FOLDERS_SEQID = "folders_seqid"
    private const val STOCKS_ID = "stocks_id"
    fun insertOneFailsDataList(file: FilesData?) {
        if (file == null) return

        var realm = Realm.getDefaultInstance()

        realm.beginTransaction()
        realm.insert(file)
        realm.commitTransaction()

        realm.close()
    }

    fun insertAllFailsDataList(relationList: List<FilesData>?) {
        if (relationList == null) return

        var realm = Realm.getDefaultInstance()

        realm.beginTransaction()
        for (folder in relationList) {
            realm.insert(folder)
        }
        realm.commitTransaction()

        realm.close()
    }

    fun deleteFailsDataList() {
        var realm = Realm.getDefaultInstance()

        val relationData = realm.where<FilesData>().findAll()

        realm.beginTransaction()
        relationData.deleteAllFromRealm()
        realm.commitTransaction()

        realm.close()

    }

    fun deleteFailsDataListByFoldersSeqid(folders_seqid: Int) {
        var realm = Realm.getDefaultInstance()

        val filesData = realm.where<FilesData>()
            .equalTo(FOLDERS_SEQID, folders_seqid)
            .findAll()
        realm.beginTransaction()
        filesData.deleteAllFromRealm()
        realm.commitTransaction()

        realm.close()
    }

    fun deleteFailsDataListByFoldersSeqidAndStocksId(folders_seqid: Int, stocks_id: String) {
        var realm = Realm.getDefaultInstance()

        val filesData = realm.where<FilesData>()
            .equalTo(FOLDERS_SEQID, folders_seqid)
            .equalTo(STOCKS_ID, stocks_id)
            .findAll()
        realm.beginTransaction()
        filesData.deleteAllFromRealm()
        realm.commitTransaction()

        realm.close()
    }

    fun selectFailsData(): List<FilesData>? {
        var realm = Realm.getDefaultInstance()

        val relation = realm.where<FilesData>().findAll()
        if (relation.count() == 0) return null
        val relationList = realm.copyFromRealm(relation)

        realm.close()

        return relationList
    }

    fun selectFailsDataBySeqid(seqid: Int): List<FilesData>? {
        var realm = Realm.getDefaultInstance()

        val relation = realm.where<FilesData>()
            .equalTo(FOLDERS_SEQID, seqid)
            .findAll()
        if (relation.count() == 0) return null
        val relationList = realm.copyFromRealm(relation)

        realm.close()

        return relationList
    }
}
package jp.kirin3.anytimeqiita.ui.bookmark

import jp.kirin3.anytimeqiita.source.TasksDataSource
import jp.kirin3.anytimeqiita.source.TasksRepository
import jp.kirin3.anytimeqiita.data.AccessTokenResponseData
import kirin3.jp.mljanken.util.LogUtils.LOGD

class BookmarklPresenter(
    private val bookmarkRepository: TasksRepository,
    private val bookmarkView: BookmarkContract.View
){

    data class User(var name: String, val age: Int)



//
//    override fun processAccessToken(code: String) {
//        bookmarkRepository.getTasks(code, object : TasksDataSource.LoadTasksCallback {
//            override fun onStocksLoaded(tasks: AccessTokenResponseData) {
//
//                LOGD("processAccessToken onStocksLoaded")
//                bookmarkView.showMessage(tasks.client_id)
//            }
//
//            override fun onDataNotAvailable() {
//                LOGD("processAccessToken onDataNotAvailable")
//            }
//        })
//    }
}


package jp.kirin3.anytimeqiita.ui.folder

import jp.kirin3.anytimeqiita.source.TasksRepository

class FolderlPresenter(
    private val folderRepository: TasksRepository,
    private val folderView: FolderContract.View
){

    data class User(var name: String, val age: Int)



//
//    override fun processAccessToken(code: String) {
//        bookmarkRepository.getTasks(code, object : TasksDataSource.LoadTasksCallback {
//            override fun onStocksLoaded(tasks: AccessTokenResponseData) {
//
//                LOGD("processAccessToken onStocksLoaded")
//                folderView.showMessage(tasks.client_id)
//            }
//
//            override fun onDataNotAvailable() {
//                LOGD("processAccessToken onDataNotAvailable")
//            }
//        })
//    }
}


package jp.kirin3.anytimeqiita.ui.bookmark

import jp.kirin3.anytimeqiita.source.TasksDataSource
import jp.kirin3.anytimeqiita.source.TasksRepository
import jp.kirin3.anytimeqiita.data.AccessTokenResponseData
import kirin3.jp.mljanken.util.LogUtils.LOGD

class BookmarklPresenter(
    private val bookmarkRepository: TasksRepository,
    private val bookmarkView: BookmarkContract.View
) : BookmarkContract.Presenter {

    // FragmentのpresenterにViewを設定
    init {
        LOGD("xxxxxxxxxxxxxxinit")
        bookmarkView.presenter = this
    }

    override fun start() {
//        getMessage()

    }

    override fun getMessage() {
//          getTasks()
    }

    data class User(var name: String, val age: Int)

    override fun processAccessToken(code: String) {
        bookmarkRepository.getTasks(code, object : TasksDataSource.LoadTasksCallback {
            override fun onTasksLoaded(tasks: AccessTokenResponseData) {
                LOGD("processAccessToken onTasksLoaded")
                bookmarkView.showMessage(tasks.client_id)
            }

            override fun onDataNotAvailable() {
                LOGD("processAccessToken onDataNotAvailable")
            }
        })
    }

//
//    override fun processAccessToken(code: String) {
//        bookmarkRepository.getTasks(code, object : TasksDataSource.LoadTasksCallback {
//            override fun onTasksLoaded(tasks: AccessTokenResponseData) {
//
//                LOGD("processAccessToken onTasksLoaded")
//                bookmarkView.showMessage(tasks.client_id)
//            }
//
//            override fun onDataNotAvailable() {
//                LOGD("processAccessToken onDataNotAvailable")
//            }
//        })
//    }
}


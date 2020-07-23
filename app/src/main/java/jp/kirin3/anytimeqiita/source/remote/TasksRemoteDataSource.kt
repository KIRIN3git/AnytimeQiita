package jp.kirin3.anytimeqiita.source.remote

import androidx.annotation.VisibleForTesting
import jp.kirin3.anytimeqiita.source.TasksDataSource

class TasksRemoteDataSource : TasksDataSource {


    // Retrofitによる通信処理を記載
    override fun getTasks(code:String,callback: TasksDataSource.LoadTasksCallback) {


//        val response = ApiClient.fetchAccessToken()



//        var tasks: List<Task> = listOf<Task>(Task("Remote Title", ""))
//        callback.onDataNotAvailable()
//        callback.onStocksLoaded(tasks)
    }

    companion object {
        private var INSTANCE: TasksRemoteDataSource? = null

        @JvmStatic
//        fun getInstance(appExecutors: AppExecutors, tasksDao: TasksDao): TasksRemoteDataSource {
        fun getInstance(): TasksRemoteDataSource {
            if (INSTANCE == null) {
                synchronized(TasksRemoteDataSource::javaClass) {
                    INSTANCE = TasksRemoteDataSource()
                }
            }
            return INSTANCE!!
        }

        @VisibleForTesting
        fun clearInstance() {
            INSTANCE = null
        }
    }
}
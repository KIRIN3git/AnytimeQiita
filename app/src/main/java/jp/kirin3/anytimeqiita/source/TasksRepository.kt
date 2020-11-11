package jp.kirin3.anytimeqiita.source

class TasksRepository(
    val tasksRemoteDataSource: TasksDataSource
) : TasksDataSource {


    companion object {

        private var INSTANCE: TasksRepository? = null

        /**
         * Returns the single instance of this class, creating it if necessary.

         * @param tasksRemoteDataSource the backend data source
         * *
         * @param tasksLocalDataSource  the device storage data source
         * *
         * @return the [TasksRepository] instance
         */

//        @JvmStatic fun getInstance(tasksRemoteDataSource: TasksDataSource,
//                                   tasksLocalDataSource: TasksDataSource): TasksRepository {

        @JvmStatic
        fun getInstance(
            tasksRemoteDataSource: TasksDataSource
        ): TasksRepository {
            return INSTANCE ?: TasksRepository(tasksRemoteDataSource)
                .apply { INSTANCE = this }
        }

        /**
         * Used to force [getInstance] to create a new instance
         * next time it's called.
         */
        @JvmStatic
        fun destroyInstance() {
            INSTANCE = null
        }
    }

    override fun getTasks(code: String, callback: TasksDataSource.LoadTasksCallback) {
/*
        // データをキャッシュする処理
        ApiClient.fetchAccessToken(code, object : TasksDataSource.LoadTasksCallback {
            override fun onStocksLoaded(tasks: AccessTokenResponseData) {
                // Presenterにcallback
                callback.onStocksLoaded(tasks)
            }

            override fun onDataNotAvailable() {
                LOGD("onDataNotAvailable")
            }
        })
*/

/*
        tasksRemoteDataSource.getTasks(object : TasksDataSource.LoadTasksCallback {
            override fun onStocksLoaded(tasks: List<AccessTokenResponseData>) {
                // Presenterにcallback
                callback.onStocksLoaded(tasks)
            }
            override fun onDataNotAvailable() {

            }
        })
 */
    }
}
package jp.kirin3.anytimeqiita.source

import jp.kirin3.anytimeqiita.data.AccessTokenResponseData

interface TasksDataSource {


    interface LoadTasksCallback {

        fun onTasksLoaded(tasks: AccessTokenResponseData)

        fun onDataNotAvailable()
    }

    interface GetTaskCallback {

        fun onTaskLoaded(task: Task)

        fun onDataNotAvailable()
    }

     fun getTasks(code:String,callback: LoadTasksCallback)

    /*
     fun getTask(taskId: String, callback: GetTaskCallback)

     fun saveTask(task: Task)

     fun completeTask(task: Task)

     fun completeTask(taskId: String)

     fun activateTask(task: Task)

     fun activateTask(taskId: String)

     fun clearCompletedTasks()

     fun refreshTasks()

     fun deleteAllTasks()

     fun deleteTask(taskId: String)

      */
}
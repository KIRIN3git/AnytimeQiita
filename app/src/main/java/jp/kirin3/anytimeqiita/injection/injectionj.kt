package jp.kirin3.anytimeqiita.injection

import jp.kirin3.anytimeqiita.source.TasksRepository
import jp.kirin3.anytimeqiita.source.remote.TasksRemoteDataSource

/**
 * Enables injection of mock implementations for
 * [TasksDataSource] at compile time. This is useful for testing, since it allows us to use
 * a fake instance of the class to isolate the dependencies and run a test hermetically.
 *
 * モック実装のインジェクションを可能にします
 * コンパイル時の[TasksDataSource]。 これはテストに役立ちます。
 * 依存関係を分離し、テストをハーメチックに実行するためのクラスの偽のインスタンス。
 **/

object Injection {
//    fun provideTasksRepository(context: Context): TasksRepository {
//        val database = ToDoDatabase.getInstance(context)
//        return TasksRepository.getInstance(FakeTasksRemoteDataSource.getInstance(),
//            TasksLocalDataSource.getInstance(AppExecutors(), database.taskDao()))
//    }
    //TODO: KIRIN3 TasksRemoteDataSorceのインスタンスも入れたいが一旦やめる
    fun provideTasksRepository(): TasksRepository {
        return TasksRepository.getInstance(TasksRemoteDataSource.getInstance())
    }
}

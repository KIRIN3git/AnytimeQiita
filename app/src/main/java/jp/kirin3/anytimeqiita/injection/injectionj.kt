package jp.kirin3.anytimeqiita.injection

import jp.kirin3.anytimeqiita.source.TasksRepository
import jp.kirin3.anytimeqiita.source.remote.TasksRemoteDataSource
import jp.kirin3.anytimeqiita.ui.setting.SettingRepository
import jp.kirin3.anytimeqiita.ui.stocks.FoldersRepository
import jp.kirin3.anytimeqiita.ui.stocks.StocksRepository

/**
 * Enables injection of mock implementations for
 * [TasksDataSource] at compile time. This is useful for testing, since it allows us to use
 * a fake instance of the class to isolate the dependencies and run a fragment_folders_dialog hermetically.
 *
 * モック実装のインジェクションを可能にします
 * コンパイル時の[TasksDataSource]。 これはテストに役立ちます。
 * 依存関係を分離し、テストをハーメチックに実行するためのクラスの偽のインスタンス。
 **/

object Injection {
//        fun provideTasksRepository(context: Context): TasksRepository {
//        val database = ToDoDatabase.getInstance(context)
//        return TasksRepository.getInstance(FakeTasksRemoteDataSource.getInstance(),
//            TasksLocalDataSource.getInstance(AppExecutors(), database.taskDao()))
//    }
    //TODO: KIRIN3 TasksRemoteDataSorceのインスタンスも入れたいが一旦やめる
    fun provideTasksRepository(): TasksRepository {
        return TasksRepository.getInstance(TasksRemoteDataSource.getInstance())
    }

    fun provideStocksRepository(): StocksRepository {
        return StocksRepository.getInstance()
    }

    fun provideFoldersRepository(): FoldersRepository {
        return FoldersRepository.getInstance()
    }

    fun provideSettingRepository(): SettingRepository {
        return SettingRepository.getInstance()
    }
}

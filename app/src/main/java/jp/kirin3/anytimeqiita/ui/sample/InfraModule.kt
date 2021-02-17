package jp.kirin3.anytimeqiita.ui.sample

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class InfraModule {

    @Singleton
    @Provides
    fun provideSampleRepository(): SampleRepository {
        return RepositoryContainer.sample
    }
}
package jp.kirin3.anytimeqiita.di

import dagger.Module
import dagger.Provides
import jp.kirin3.anytimeqiita.infra.repository.SampleRepository
import javax.inject.Singleton

@Module
class InfraModule {
    @Singleton
    @Provides
    fun provideSampleRepository(): SampleRepository {
        return RepositoryContainer.sample
    }
}
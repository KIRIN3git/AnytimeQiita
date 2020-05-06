package jp.kirin3.anytimeqiita.source

import jp.kirin3.anytimeqiita.source.local.UserLocalDataSource

class UserRepository(val userLocalDataSource: UserLocalDataSource) : UserDataSorce {


    companion object {
        private var INSTANCE: UserRepository? = null

        fun getInstance(
            userLocalDataSource: UserLocalDataSource
        ): UserRepository {
            return INSTANCE ?: UserRepository(userLocalDataSource)
                .apply { INSTANCE = this }
        }
    }

//    override fun loadUserData() {
//
//    }
}
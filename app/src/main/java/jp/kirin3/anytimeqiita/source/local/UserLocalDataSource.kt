package jp.kirin3.anytimeqiita.source.local

import jp.kirin3.anytimeqiita.source.UserDataSorce

class UserLocalDataSource:UserDataSorce {

    companion object{
        private var INSTANCE: UserLocalDataSource? = null
        fun getInstance(): UserLocalDataSource{
            if(INSTANCE == null) {
                synchronized(UserLocalDataSource::javaClass){
                    INSTANCE = UserLocalDataSource()
                }
            }
            return INSTANCE!!
        }
    }
}
package jp.kirin3.anytimeqiita.model

import androidx.lifecycle.ViewModel
import jp.kirin3.anytimeqiita.data.AuthenticatedUserResponceData
import jp.kirin3.anytimeqiita.util.StringUtils

class AuthenticatedUserModel : ViewModel() {

    companion object {
        private var cacheAuthenticatedUser: AuthenticatedUserResponceData? = null
    }


    fun setAuthenticatedUserFromCache(userData: AuthenticatedUserResponceData?) {
        cacheAuthenticatedUser = userData
    }

    fun getAuthenticatedUserFromCache(): AuthenticatedUserResponceData? {
        return cacheAuthenticatedUser
    }

    fun hasAuthenticatedUserIdFromCache( ):Boolean{
        val user = getAuthenticatedUserFromCache()?:let{
            return false
        }
        if( StringUtils.isEmpty(user.id)){
            return false
        }
        return true
    }
}
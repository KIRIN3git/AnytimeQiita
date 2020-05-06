package jp.kirin3.anytimeqiita.model

import androidx.lifecycle.ViewModel
import jp.kirin3.anytimeqiita.data.AuthenticatedUserResponceData

class AuthenticatedUserModel : ViewModel() {

    companion object {

        private var catchAuthenticatedUser: AuthenticatedUserResponceData? = null
    }


    fun setAuthenticatedUser(userData: AuthenticatedUserResponceData) {
        catchAuthenticatedUser = userData
    }

    fun getAuthenticatedUser():AuthenticatedUserResponceData?{
        return catchAuthenticatedUser
    }
}
package jp.kirin3.anytimeqiita.api

import io.reactivex.Observable
import jp.kirin3.anytimeqiita.data.AuthenticatedUserData
import retrofit2.http.GET
import retrofit2.http.Header

interface AuthenticatedUserService {
    @GET("api/v2/authenticated_user")
    fun fetchRepos(@Header("Authorization") requestHeader: String): Observable<AuthenticatedUserData>
}

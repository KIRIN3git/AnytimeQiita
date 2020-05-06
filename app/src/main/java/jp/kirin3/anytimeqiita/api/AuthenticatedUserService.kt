package jp.kirin3.anytimeqiita.api

import io.reactivex.Observable
import jp.kirin3.anytimeqiita.data.AuthenticatedUserResponceData
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface AuthenticatedUserService {
    @GET("api/v2/authenticated_user")
    fun fetchRepos(@Header("Authorization") authorization: String): Observable<AuthenticatedUserResponceData>
}

package jp.kirin3.anytimeqiita.api

import io.reactivex.Observable
import jp.kirin3.anytimeqiita.data.AccessTokenRequestData
import jp.kirin3.anytimeqiita.data.AccessTokenResponseData
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AccessTokenService {
    @Headers(
        "Content-Type: application/json"
    )
    @POST("api/v2/access_tokens")
    fun fetchRepos(@Body requestData: AccessTokenRequestData): Observable<AccessTokenResponseData>
}

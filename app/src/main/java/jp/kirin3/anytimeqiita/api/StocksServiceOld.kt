package jp.kirin3.anytimeqiita.api

import io.reactivex.Observable
import jp.kirin3.anytimeqiita.data.StocksResponseData
import retrofit2.http.*

interface StocksServiceOld {
    @GET("api/v2/users/{user_id}/stocks")
    fun fetchRepos(
        @Path("user_id") userId: String,
        @Header("Host") host: String,
        @QueryMap query:Map<String, String>
    ): Observable<List<StocksResponseData>>
}

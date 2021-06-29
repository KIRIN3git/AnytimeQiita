package jp.kirin3.anytimeqiita.api

import io.reactivex.Single
import jp.kirin3.anytimeqiita.data.StocksResponseData
import jp.kirin3.anytimeqiita.di.RetrofitFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.QueryMap

class StocksClient {

    private fun getService(retrofitFactory: RetrofitFactory): StocksService {
        return retrofitFactory.buildRetrofit().create(StocksService::class.java)
    }

    fun fetchStocks(
        retrofitFactory: RetrofitFactory,
        userId: String,
        page: String,
        perPage: String
    ): Single<List<StocksResponseData>> {
        val requestData: Map<String, String> = mapOf("page" to page, "per_page" to perPage)
        return getService(retrofitFactory).fetchRepos(
            userId,
            retrofitFactory.getHostName(),
            requestData
        )
    }

    interface StocksService {
        @GET("api/v2/users/{user_id}/stocks")
        fun fetchRepos(
            @Path("user_id") userId: String,
            @Header("Host") host: String,
            @QueryMap query: Map<String, String>
        ): Single<List<StocksResponseData>>
    }
}
package jp.kirin3.anytimeqiita.infra.network.apiclient

import io.reactivex.Single
import jp.kirin3.anytimeqiita.data.entity.SampleDataResult
import retrofit2.Retrofit
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

class SampleAPIClient constructor(retrofit: Retrofit) {

    private val service = retrofit.create(SampleValidateService::class.java)

    fun sampleValidate(
        code: String
    ): Single<SampleDataResult> {
        return service.sampleValidate(
            code
        )
    }

    interface SampleValidateService {
        @FormUrlEncoded
        @POST("/xxxxxx;yyyyy")
        fun sampleValidate(
            @Field("code") code: String
        ): Single<SampleDataResult>
    }
}
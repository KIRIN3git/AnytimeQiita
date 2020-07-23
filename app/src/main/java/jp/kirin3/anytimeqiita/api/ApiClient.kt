package jp.kirin3.anytimeqiita.api

import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import jp.kirin3.anytimeqiita.MainApplication.Companion.QIITA_CLIENT_ID
import jp.kirin3.anytimeqiita.MainApplication.Companion.QIITA_CLIENT_SEACRET
import jp.kirin3.anytimeqiita.data.*
import kirin3.jp.mljanken.util.LogUtils.LOGD
import kirin3.jp.mljanken.util.LogUtils.LOGI
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private val BASE_URL = "https://qiita.com/"
    private val HOST = "qiita.com"
    private val HEADER_ACCESS_TOKEN_BEARER = "Bearer "

    private fun restClient(): Retrofit {

        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    interface AccessTokenApiCallback {
        fun onTasksLoaded(responseData: AccessTokenResponseData)
        fun onDataNotAvailable()
    }

    fun fetchAccessToken(code: String, callback: AccessTokenApiCallback) {
        val service = restClient().create(AccessTokenService::class.java)
        val requestData = AccessTokenRequestData(
            QIITA_CLIENT_ID,
            QIITA_CLIENT_SEACRET,
            code
        )
        val repos = service.fetchRepos(requestData)

//        LOGI("repos.toString()" + repos.)

        repos
            .subscribeOn(Schedulers.io())
            //.observeOn(AndroidSchedulers.mainThread())
            .observeOn(Schedulers.io())
            .subscribe(object : Observer<AccessTokenResponseData> {
                override fun onSubscribe(d: Disposable) {
                    LOGI("")
                }

                override fun onComplete() {
                    LOGI("")
                }

                override fun onError(e: Throwable) {
                    LOGD("通信 -> 失敗:$e")
                    callback.onDataNotAvailable()
                }

                override fun onNext(responseData: AccessTokenResponseData) {
                    LOGI("")
                    LOGI("RESPONSE_DATA[" + responseData.toString() + "]")
                    callback.onTasksLoaded(responseData)
                }
            })
    }

    interface AuthenticatedUserApiCallback {
        fun onTasksLoaded(responseData: AuthenticatedUserResponceData)
        fun onDataNotAvailable()
    }

    fun fetchAuthenticatedUser(accessToken: String?, callback: AuthenticatedUserApiCallback) {
        if (accessToken == null) {
            callback.onDataNotAvailable()
            return
        }

        val service = restClient().create(AuthenticatedUserService::class.java)

        val requestHeader = HEADER_ACCESS_TOKEN_BEARER + accessToken

        val repos = service.fetchRepos(requestHeader)

        repos
            .subscribeOn(Schedulers.io())
            //.observeOn(AndroidSchedulers.mainThread())
            .observeOn(Schedulers.io())
            .subscribe(object : Observer<AuthenticatedUserResponceData> {
                override fun onSubscribe(d: Disposable) {
                    LOGI("")
                }

                override fun onComplete() {
                    LOGI("")
                }

                override fun onError(e: Throwable) {
                    LOGD("通信 -> 失敗:$e")
                    callback.onDataNotAvailable()
                }

                override fun onNext(responseData: AuthenticatedUserResponceData) {
                    LOGI("")
                    LOGI("RESPONSE_DATA[" + responseData.toString() + "]")
                    callback.onTasksLoaded(responseData)
                }
            })
    }

    interface StocksApiCallback {
        fun onTasksLoaded(responseData: List<StocksResponseData>)
        fun onDataNotAvailable()
    }

    fun fetchStocks(userId: String?, page: String, callback: StocksApiCallback) {
        val perPage = "10";
        if (userId == null) {
            callback.onDataNotAvailable()
            return
        }

        val service = restClient().create(StocksService::class.java)

//        val requestData = StocksRequestData(
//            page,perPage
//        )
        val requestData: Map<String, String> = mapOf("page" to page, "perPage" to perPage)

        val repos = service.fetchRepos(userId, HOST, requestData)

        repos
            .subscribeOn(Schedulers.io())
            //.observeOn(AndroidSchedulers.mainThread())
            .observeOn(Schedulers.io())
            .subscribe(object : Observer<List<StocksResponseData>> {
                override fun onSubscribe(d: Disposable) {
                    LOGI("")
                }

                override fun onComplete() {
                    LOGI("")
                }

                override fun onError(e: Throwable) {
                    LOGD("通信 -> 失敗:$e")
                    callback.onDataNotAvailable()
                }

                override fun onNext(responseData: List<StocksResponseData>) {
                    LOGI("")
//                    LOGI("RESPONSE_DATA[" + responseData.toString() + "]")
                    for (data in responseData) {
                        LOGI("data.title" + data.title)
                        LOGI("data.url" + data.url)
                        LOGI("data.coediting" + data.coediting)
//                        LOGI("data.created_at" + data.created_at)
//
//                        if (data.group != null && data.group.name != null) {
//                            LOGI("XXXXXXXXXXXXXXXXx" + data.group.name)
//                        }
//                        if (data.user != null && data.user.followees_count != null) {
//                            LOGI("XXXXXXXXXXXXXYYYY" + data.user.followees_count)
//                        }
                    }
                    callback.onTasksLoaded(responseData)
                }
            })
    }
}
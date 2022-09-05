package com.app.sdk.sdk.data

import com.app.sdk.sdk.config.SdkConfig
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

object ApiHelper {
    private val pushApi: PushApi by lazy { createApi() }

    private fun createApi() = Retrofit.Builder()
        .baseUrl(SdkConfig.serverAddress)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(PushApi::class.java)

    fun sendUserPushToken(packageName: String, fcmToken: String) =
        mapOf("package_name" to packageName, "push_token" to fcmToken)
            .let(pushApi::sendToken)

    interface PushApi {
        @POST("push/add")
        fun sendToken(@Body fields: Map<String, String>): Call<ResponseBody>
    }
}
package com.codingtroops.foodies.model.data

import com.codingtroops.foodies.model.response.ProblemResponse
import retrofit2.http.GET
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DoseApi @Inject constructor(private val service: Service) {
    suspend fun getProblems(): ProblemResponse = service.getProblems()

    interface Service {
        @GET("jKVv22p8")
        suspend fun getProblems(): ProblemResponse
    }

    companion object {
//        const val API_URL = "https://www.themealdb.com/api/json/v1/1/"
        const val API_URL = "https://pastebin.com/raw/"
    }
}



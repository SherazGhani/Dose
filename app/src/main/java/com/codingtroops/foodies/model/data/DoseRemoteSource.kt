package com.codingtroops.foodies.model.data

import com.codingtroops.foodies.model.response.ProblemResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DoseRemoteSource @Inject constructor(private val doseApi: DoseApi) {
    suspend fun fetchProblems(): ProblemResponse = withContext(Dispatchers.IO) {
        doseApi.getProblems()
    }
}
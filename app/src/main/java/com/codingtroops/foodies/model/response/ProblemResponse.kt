package com.codingtroops.foodies.model.response

import com.google.gson.annotations.SerializedName

data class ProblemResponse(
    @SerializedName("problems")
    val problems: List<Problem> = ArrayList()
)
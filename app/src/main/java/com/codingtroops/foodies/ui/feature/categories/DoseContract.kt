package com.codingtroops.foodies.ui.feature.categories

import com.codingtroops.foodies.model.response.ProblemResponse
import com.codingtroops.foodies.model.response.User


class DoseContract {

    data class RemoteState(
        val problemResponse: ProblemResponse = ProblemResponse(),
        val isLoading: Boolean = false
    )

    data class LocalState(
        var user: User? = User(),
        val isLoading: Boolean = false
    )

    sealed class Effect {
        object DataWasLoaded : Effect()
    }
}
package com.codingtroops.foodies.ui.feature.categories

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingtroops.foodies.model.data.DoseLocalSource
import com.codingtroops.foodies.model.data.DoseRemoteSource
import com.codingtroops.foodies.model.response.ProblemResponse
import com.codingtroops.foodies.model.response.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DoseViewModel @Inject constructor(
    private val remoteSource: DoseRemoteSource,
    private val localSource: DoseLocalSource) :
    ViewModel() {

    var remoteState by mutableStateOf(
        DoseContract.RemoteState(
            problemResponse = ProblemResponse(),
            isLoading = false
        )
    )
        private set

    var localState by mutableStateOf(
        DoseContract.LocalState(
            user = User(),
            isLoading = false
        )
    )
        private set

    var effects = Channel<DoseContract.Effect>(UNLIMITED)
        private set

    init {
        viewModelScope.launch { getProblems() }
    }

    private suspend fun getProblems() {
        val problemResponse = remoteSource.fetchProblems()
        viewModelScope.launch {
            remoteState = remoteState.copy(problemResponse = problemResponse, isLoading = false)
            effects.send(DoseContract.Effect.DataWasLoaded)
        }
    }

    fun insertUser(user: User){
        localSource.insertUser(user)
        viewModelScope.launch {
            localState = localState.copy(user = null, isLoading = false)
            effects.send(DoseContract.Effect.DataWasLoaded)
        }
    }

    fun getUser() {
        val user = localSource.users
        viewModelScope.launch {
            localState = localState.copy(user = user.first(), isLoading = false)
            effects.send(DoseContract.Effect.DataWasLoaded)
        }
    }
}




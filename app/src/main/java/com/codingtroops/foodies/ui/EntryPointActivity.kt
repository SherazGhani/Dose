package com.codingtroops.foodies.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.codingtroops.foodies.ui.feature.categories.DoseScreen
import com.codingtroops.foodies.ui.feature.categories.DoseViewModel
import com.codingtroops.foodies.ui.NavigationKeys.Route.PROBLEMS_LIST
import com.codingtroops.foodies.ui.auth.LoginScreen
import com.codingtroops.foodies.ui.theme.ComposeSampleTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.receiveAsFlow


// Single Activity per app
@AndroidEntryPoint
class EntryPointActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeSampleTheme {
                DoseApp()
            }
        }
    }

}

@Composable
private fun DoseApp() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = NavigationKeys.Route.LOGIN_SCREEN) {
        composable(route = NavigationKeys.Route.LOGIN_SCREEN) {
            LoginDestination(navController)
        }

        composable(route = NavigationKeys.Route.PROBLEMS_LIST) {
            ProblemsDestination(navController)
        }
    }
}

@Composable
private fun LoginDestination(navController: NavHostController){
    val viewModel: DoseViewModel = hiltViewModel()
    LoginScreen(
        localState = viewModel.localState,
        effectFlow = viewModel.effects.receiveAsFlow(),
        onNavigationRequested = {user ->
            viewModel.insertUser(user)
            navController.navigate(PROBLEMS_LIST)
        }
    )
}

@Composable
private fun ProblemsDestination(navController: NavHostController) {
    val viewModel: DoseViewModel = hiltViewModel()
    DoseScreen(
        remoteState = viewModel.remoteState,
        localState = viewModel.localState,
        effectFlow = viewModel.effects.receiveAsFlow(),
        onNavigationRequested = {})
}

object NavigationKeys {

    object Route {
        const val LOGIN_SCREEN = "login_screen"
        const val PROBLEMS_LIST = "problems_list"
    }

}
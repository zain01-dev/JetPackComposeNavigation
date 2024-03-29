package com.example.simplenavigationcompose.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.simplenavigationcompose.ui.screens.HomeScreen
import com.example.simplenavigationcompose.ui.screens.LoginScreen
import com.example.simplenavigationcompose.ui.screens.ProfileScreen
import com.example.simplenavigationcompose.ui.screens.SearchScreen


//Nav Host -> area where compose screen will be displayed
//Nav Graph -> area where we define the navigation routes, places where we can navigate to
//Nav Controller -> area where we define the navigation actions, manage backstack interaction and interaction between screens
//Nav Route -> area where we define the navigation routes, places where we can navigate to
//Nav Argument -> area where we define the navigation arguments, data that we can pass between screens
//Nav Back Stack -> area where we define the navigation back stack, the order of screens that we can navigate back to
//Nav Deep Link -> area where we define the navigation deep links, the way we can navigate to a specific screen from outside the app
//Nav Host Controller -> area where we define the navigation host controller, the way we can navigate to a specific screen from outside the app


@Composable
fun NavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = NavRoute.Login.path
    ) {
        addLoginScreen(navController, this)

        addHomeScreen(navController, this)

        addProfileScreen(navController, this)

        addSearchScreen(navController, this)
    }
}

private fun addLoginScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(route = NavRoute.Login.path) {
        LoginScreen(
            navigateToHome = {
                navController.navigate(NavRoute.Home.path)
            }
        )
    }
}

private fun addHomeScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(route = NavRoute.Home.path) {

        HomeScreen(
            navigateToProfile = { id, showDetails ->
                navController.navigate(NavRoute.Profile.withArgs(id.toString(), showDetails.toString()))
            },
            navigateToSearch = { query ->
                navController.navigate(NavRoute.Search.withArgs(query))
            },
            popBackStack = { navController.popBackStack() },
            popUpToLogin= { popUpToLogin(navController) },
        )
    }
}

private fun popUpToLogin(navController: NavHostController) {
    navController.popBackStack(NavRoute.Login.path, inclusive = false)
}

private fun addProfileScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(
        route = NavRoute.Profile.withArgsFormat(NavRoute.Profile.id, NavRoute.Profile.showDetails),
        arguments = listOf(
            navArgument(NavRoute.Profile.id) {
                type = NavType.IntType
            }
            ,
            navArgument(NavRoute.Profile.showDetails) {
                type = NavType.BoolType
            }
        )
    ) { navBackStackEntry ->

        val args = navBackStackEntry.arguments

        ProfileScreen(
            id = args?.getInt(NavRoute.Profile.id)!!,
            showDetails = args.getBoolean(NavRoute.Profile.showDetails),
            popBackStack = { navController.popBackStack() },
            popUpToLogin = { popUpToLogin(navController) }
        )
    }
}

private fun addSearchScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(
        route = NavRoute.Search.withArgsFormat(NavRoute.Search.query),
        arguments = listOf(
            navArgument(NavRoute.Search.query) {
                type = NavType.StringType
                nullable = true
            }
        )
    ) { navBackStackEntry ->

        val args = navBackStackEntry.arguments

        SearchScreen(
            query = args?.getString(NavRoute.Search.query),
            popBackStack = { navController.popBackStack() },
            popUpToLogin = { popUpToLogin(navController) }
        )
    }
}

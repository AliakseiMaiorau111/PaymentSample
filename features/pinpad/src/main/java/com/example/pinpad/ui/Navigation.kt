package com.example.pinpad.ui

import android.os.Bundle
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

sealed class Screen(
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList(),
    val enterTransaction: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = { AppTransition.defaultEnter },
    val exitTransaction: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = { AppTransition.defaultExit }
)

fun NavGraphBuilder.aaa() {}

fun NavGraphBuilder.appDestination(
    screen: Screen,
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit
) {
    composable(
        route = screen.route,
        arguments = screen.arguments,
        enterTransition = screen.enterTransaction,
        exitTransition = screen.exitTransaction,
        content = content
    )
}

fun NavController.navigate(destination: Screen) {
    navigate(destination.route)
}

object Destination {
    object PinPadScreen : Screen(
        "pinpad",
        enterTransaction = {
            when(initialState.destination.route) {
                PinPadScreen.route -> AppTransition.shared2AxisEnterBackward
                else -> AppTransition.defaultEnter
            }
        },
        exitTransaction = {
            AppTransition.defaultExit
        }
    )

    object ReceiptScreen : Screen(
        "receipt/{transaction}",
        arguments = listOf(navArgument("transaction") {
            type = NavType.StringType // TODO: Pass full model (e.g. as JSON string)
        }),
        enterTransaction = { AppTransition.shared2AxisEnterForward },
        exitTransaction = { AppTransition.shared2AxisExitForward }
    ) {
        fun idForm(arguments: Bundle?): String {
            return arguments?.getString("transaction")!!
        }

        fun buildRoute(transaction: String) = "receipt/$transaction"
    }
}
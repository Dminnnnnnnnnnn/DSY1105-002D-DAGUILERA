package com.duoc.loginapp.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.duoc.loginapp.ui.screen.LoginScreen
import com.duoc.loginapp.ui.screen.RegistroScreen
import com.duoc.loginapp.ui.screen.ResumenScreen
import com.duoc.loginapp.viewmodel.UsuarioViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val usuarioViewModel: UsuarioViewModel = viewModel()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                viewModel = usuarioViewModel,
                onLoginSuccess = {
                    navController.navigate("resumen") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onNavigateToRegistro = { navController.navigate("registro") }
            )
        }

        composable("registro") {
            RegistroScreen(
                navController = navController,
                viewModel = usuarioViewModel
            )
        }

        composable("resumen") {
            ResumenScreen(
                navController = navController,
                viewModel = usuarioViewModel
            )
        }
    }
}
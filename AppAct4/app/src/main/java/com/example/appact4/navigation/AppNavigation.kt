package com.example.appact4.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.appact4.ui.screen.RegistroScreen
import com.example.appact4.ui.screen.ResumenScreen
import com.example.appact4.viewmodel.UsuarioViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val usuarioViewModel: UsuarioViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "registro"
    ){
        composable("registro"){
            RegistroScreen(navController, usuarioViewModel)
        }
        composable("resumen"){
            ResumenScreen(usuarioViewModel)
        }
    }
}
package com.duoc.loginapp.ui.state

import com.duoc.loginapp.model.Usuario

data class UsuarioUiState(
    val isLoggedIn: Boolean = false,
    val usuarioActual: Usuario? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)


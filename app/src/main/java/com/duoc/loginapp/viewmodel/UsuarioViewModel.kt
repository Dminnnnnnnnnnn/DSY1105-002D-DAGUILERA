package com.duoc.loginapp.viewmodel

import androidx.lifecycle.ViewModel
import com.duoc.loginapp.repository.FakeUserRepository
import com.duoc.loginapp.ui.state.RegistroErrores
import com.duoc.loginapp.ui.state.RegistroUiState
import com.duoc.loginapp.ui.state.UsuarioUiState
import com.duoc.loginapp.model.UsuarioErrores
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class UsuarioViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(UsuarioUiState())
    val uiState: StateFlow<UsuarioUiState> = _uiState.asStateFlow()

    private val _estado = MutableStateFlow(RegistroUiState())
    val estado: StateFlow<RegistroUiState> = _estado.asStateFlow()

    fun iniciarSesion(correo: String, contrasena: String) {
        if (correo.isBlank() || contrasena.isBlank()) {
            _uiState.update { it.copy(errorMessage = UsuarioErrores.CAMPOS_VACIOS) }
            return
        }

        _uiState.update { it.copy(isLoading = true, errorMessage = null) }

        val usuario = FakeUserRepository.login(correo, contrasena)

        if (usuario != null) {
            _uiState.update {
                it.copy(isLoggedIn = true, usuarioActual = usuario, isLoading = false)
            }
        } else {
            _uiState.update {
                it.copy(isLoading = false, errorMessage = UsuarioErrores.CREDENCIALES_INCORRECTAS)
            }
        }
    }

    fun registrarUsuario(): Boolean {
        val reg = _estado.value
        val correoExiste = FakeUserRepository.existeCorreo(reg.correo)

        val errores = RegistroErrores(
            nombre = if (reg.nombre.isBlank()) "El nombre es obligatorio" else null,
            correo = when {
                reg.correo.isBlank() -> "El correo es obligatorio"
                !android.util.Patterns.EMAIL_ADDRESS.matcher(reg.correo).matches() -> "Formato de correo inválido"
                correoExiste -> "Este correo ya está registrado"
                else -> null
            },
            clave = if (reg.clave.length < 6) "Mínimo 6 caracteres" else null,
            direccion = if (reg.direccion.isBlank()) "La dirección es obligatoria" else null
        )

        val hayErrores = errores.nombre != null || errores.correo != null ||
                errores.clave != null || errores.direccion != null || !reg.aceptaTerminos

        _estado.update { it.copy(errores = errores) }

        if (!hayErrores) {
            val nuevoUsuario = FakeUserRepository.registrar(reg.nombre, reg.correo, reg.clave)
            _uiState.update {
                it.copy(isLoggedIn = true, usuarioActual = nuevoUsuario)
            }
            return true
        }
        return false
    }

    fun cerrarSesion() {
        _uiState.update { UsuarioUiState() }
        _estado.update { RegistroUiState() }
    }

    fun onNombreChange(nombre: String) { _estado.update { it.copy(nombre = nombre) } }
    fun onCorreoChange(correo: String) { _estado.update { it.copy(correo = correo) } }
    fun onClaveChange(clave: String) { _estado.update { it.copy(clave = clave) } }
    fun onDireccionChange(direccion: String) { _estado.update { it.copy(direccion = direccion) } }
    fun onAceptarTerminosChange(acepta: Boolean) { _estado.update { it.copy(aceptaTerminos = acepta) } }
}
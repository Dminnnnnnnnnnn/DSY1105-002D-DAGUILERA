package com.duoc.loginapp.repository

import com.duoc.loginapp.model.Rol
import com.duoc.loginapp.model.Usuario

object FakeUserRepository {
    private val usuarios = mutableListOf(
        Usuario(1, "Admin Principal", "admin@modoguardian.cl", "admin123", Rol.ADMINISTRADOR),
        Usuario(2, "Super Visor", "supervisor@modoguardian.cl", "super123", Rol.SUPERVISOR),
        Usuario(3, "Operador Turno", "operador@modoguardian.cl", "op123", Rol.OPERADOR)
    )

    fun login(correo: String, contrasena: String): Usuario? {
        return usuarios.find { it.correo.equals(correo, ignoreCase = true) && it.contrasena == contrasena }
    }

    fun existeCorreo(correo: String): Boolean {
        return usuarios.any { it.correo.equals(correo, ignoreCase = true) }
    }

    fun registrar(nombre: String, correo: String, contrasena: String): Usuario {
        val nuevoId = (usuarios.maxOfOrNull { it.id } ?: 0) + 1
        val nuevoUsuario = Usuario(nuevoId, nombre, correo, contrasena, Rol.OPERADOR)
        usuarios.add(nuevoUsuario)
        return nuevoUsuario
    }
}
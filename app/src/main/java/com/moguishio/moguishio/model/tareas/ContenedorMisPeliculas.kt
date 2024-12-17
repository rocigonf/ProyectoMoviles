package com.moguishio.moguishio.model.tareas

import android.content.Context

// A través de esta clase se realizan todas las operaciones necesarias, ya que instancia el repositorio dando acceso al DAO

class ContenedorMisPeliculas(private val context: Context) {
    val repositorioMisPeliculas: RepositorioMisPeliculas by lazy {
        RepositorioMisPeliculas(BaseDatosMisPeliculas.getMyFriendsDatabase(context).daoMisPeliculas())
    }
}
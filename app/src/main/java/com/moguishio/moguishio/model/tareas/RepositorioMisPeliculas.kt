package com.moguishio.moguishio.model.tareas

class RepositorioMisPeliculas(private val daoMisPeliculas: DaoMisPeliculas) {
    fun getAll() = daoMisPeliculas.getAll()

    suspend fun insertarPelicula(miPelicula: MiPelicula)
            = daoMisPeliculas.insertarPelicula(miPelicula)

    suspend fun borrarMisPeliculas(misPeliculas: List<MiPelicula>)
            = daoMisPeliculas.borrarMisPeliculas(misPeliculas)
}
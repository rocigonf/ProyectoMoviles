package com.moguishio.moguishio.model.tareas

class RepositorioMisPeliculas(private val daoMisPeliculas: DaoMisPeliculas) {
    fun getAll() = daoMisPeliculas.getAll()

    fun getTotalNoVistas() = daoMisPeliculas.getTotalNoVistas()

    suspend fun insertarPelicula(miPelicula: MiPelicula)
            = daoMisPeliculas.insertarPelicula(miPelicula)

    suspend fun borrarMisPeliculas(misPeliculas: List<MiPelicula>)
            = daoMisPeliculas.borrarMisPeliculas(misPeliculas)

    suspend fun borrarPelicula(miPelicula: MiPelicula)
            = daoMisPeliculas.borrarPelicula(miPelicula)

    suspend fun modificarPelicula(miPelicula: MiPelicula)
            = daoMisPeliculas.modificarPelicula(miPelicula)
}
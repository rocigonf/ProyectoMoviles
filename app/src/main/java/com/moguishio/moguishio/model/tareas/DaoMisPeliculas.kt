package com.moguishio.moguishio.model.tareas

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface DaoMisPeliculas {
    @Query("SELECT * FROM peliculas") // Deduzco que esto puede ser sensible a SQLInjection xD
    fun getAll(): Flow<List<MiPelicula>> // Flow hace que la lista se pueda ir llenando a partir de una fuente

    @Query("SELECT COUNT(*) FROM peliculas WHERE vista = false")
    fun getTotalNoVistas(): Flow<Int>

    @Insert
    suspend fun insertarPelicula(miPelicula: MiPelicula)

    @Delete
    suspend fun borrarMisPeliculas(misPeliculas: List<MiPelicula>)

    @Delete
    suspend fun borrarPelicula(miPelicula: MiPelicula)

    @Update
    suspend fun modificarPelicula(miPelicula: MiPelicula)
}
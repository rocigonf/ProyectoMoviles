package com.moguishio.moguishio.model.tareas

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DaoMisPeliculas {
    @Query("SELECT * FROM films") // Deduzco que esto puede ser sensible a SQLInjection xD
    fun getAll(): Flow<List<MiPelicula>> // Flow hace que la lista se pueda ir llenando a partir de una fuente

    @Insert
    suspend fun insertarPelicula(miPelicula: MiPelicula)

    @Delete
    suspend fun borrarMisPeliculas(misPeliculas: List<MiPelicula>)
}
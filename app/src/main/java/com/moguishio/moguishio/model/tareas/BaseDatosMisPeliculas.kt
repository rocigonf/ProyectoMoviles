package com.moguishio.moguishio.model.tareas

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Como es un acceso a la base de datos, dejo los nombres de variables, métodos, etc. en inglés (no tengo huevos a cambiar algo y que se joda xD)

@Database(entities = [MiPelicula::class], version = 1, exportSchema = false)
abstract class BaseDatosMisPeliculas: RoomDatabase() {
    abstract fun daoMisPeliculas(): DaoMisPeliculas

    companion object {
        @Volatile // Permite que todos los cambios o lecturas sean visibles a otros hilos
        private var Instance: BaseDatosMisPeliculas? = null

        fun getMyFriendsDatabase(context: Context): BaseDatosMisPeliculas {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context = context,
                    klass = BaseDatosMisPeliculas::class.java,
                    name = "peliculas" // Cambio "sample" por "films" porque patata
                )
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
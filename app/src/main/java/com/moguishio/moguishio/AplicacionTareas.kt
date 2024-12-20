package com.moguishio.moguishio

import android.app.Application
import com.moguishio.moguishio.model.tareas.ContenedorMisPeliculas

class AplicacionTareas : Application(){
    lateinit var container: ContenedorMisPeliculas

    override fun onCreate() {
        super.onCreate()
        container = ContenedorMisPeliculas(this)

    }
}
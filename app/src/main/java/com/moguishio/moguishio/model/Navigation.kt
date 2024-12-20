package com.moguishio.moguishio.model

sealed class Navigation(val route: String)
{
    data object Principal : Navigation("Principal")
    data object AcercaDe : Navigation("AcercaDe")
    data object SobreNosotros : Navigation("SobreNosotros")
    data object Configuracion : Navigation("Configuration")
    data object Peliculas : Navigation("Films")
    data object InicioSesion : Navigation("InicioSesion")
    data object Registro : Navigation("Registro")
    data object Tareas : Navigation("Tareas")
}
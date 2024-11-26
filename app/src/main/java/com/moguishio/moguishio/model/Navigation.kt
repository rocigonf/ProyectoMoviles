package com.moguishio.moguishio.model

sealed class Navigation(val route: String)
{
    data object Principal : Navigation("Principal")
    data object AcercaDe : Navigation("AcercaDe")
    data object SobreNosotros : Navigation("SobreNosotros")
    data object Configuracion : Navigation("Configuration")
    data object Films : Navigation("Films")
}
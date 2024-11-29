package com.moguishio.moguishio.ui.views

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.moguishio.moguishio.ui.components.BotonVolver

@Composable
fun Films(navController: NavHostController, context: Context) {
    BotonVolver(navController, context)
}
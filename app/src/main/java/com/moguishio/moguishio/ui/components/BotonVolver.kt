package com.moguishio.moguishio.ui.components

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.moguishio.moguishio.R

@Composable
fun BotonVolver(navController: NavHostController, context: Context){
    CustomButton(onClick = {navController.navigateUp()}, text = context.getString(R.string.go_back))
}
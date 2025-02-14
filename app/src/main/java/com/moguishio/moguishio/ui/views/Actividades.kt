package com.moguishio.moguishio.ui.views

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavHostController
import com.moguishio.moguishio.viewmodel.activities.ViewModelActivities
import com.moguishio.moguishio.viewmodel.authentication.AuthState
import com.moguishio.moguishio.viewmodel.authentication.ViewModelAuth

@Composable
fun Actividades(navController: NavHostController, context: Context, viewModelActivities: ViewModelActivities, authViewModel: ViewModelAuth){
    val authState = authViewModel.authState.observeAsState()
    LaunchedEffect(authState.value) {
        when(authState.value)
        {
            is AuthState.Error -> navController.navigate("Principal")
            else -> navController.navigate("Principal")
        }
    }
}
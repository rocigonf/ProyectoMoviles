package com.moguishio.moguishio.components

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import kotlin.time.Duration

@Composable
fun MakeToast(
    text: String,
    duration: Int,
    context: Context
){
    Toast.makeText(context, text, duration).show()
}
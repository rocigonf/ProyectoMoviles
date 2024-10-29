package com.moguishio.moguishio

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun MainPage() {
    val image = painterResource(R.drawable.scale)
    Column (
        modifier = Modifier.fillMaxSize(),
    ){
        Text(
            text = "Help",
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(50.dp))
        Image (
            painter = image,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = "LARGA VIDA A OLIVER",
            textAlign = TextAlign.Justify
        )
    }
}
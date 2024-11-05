package com.moguishio.moguishio

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.moguishio.moguishio.data.MakeCheckBox
import com.moguishio.moguishio.data.RadioButtonListExample

@Composable
fun ConfigPage() {
    val scrollState = rememberScrollState()
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.inversePrimary)
            .padding(30.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            MakeCheckBox("MONDONGO")
            Button(
                onClick = {}
            ){
                Text(
                    text = "Guarda, mamahuevo"
                )
            }
            RadioButtonListExample()
        }
    }
}
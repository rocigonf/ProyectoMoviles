package com.moguishio.moguishio.data

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MakeCheckBox(text: String) {
    Row(
        modifier = Modifier.height(48.dp),
        verticalAlignment = Alignment.CenterVertically
    )
    {
        val checked = remember { mutableStateOf(true) }
        Checkbox(
            checked = checked.value,
            onCheckedChange = { checked.value = it }
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = text
        )
    }
}
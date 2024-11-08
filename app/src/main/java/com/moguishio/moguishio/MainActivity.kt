package com.moguishio.moguishio

import android.content.res.Configuration
//import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
//import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.moguishio.moguishio.ui.theme.AppTheme
import com.moguishio.moguishio.ui.theme.AppTypography

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                val navController = rememberNavController()
                //val mediaPlayer = MediaPlayer.create(LocalContext.current, R.raw.sound)
                var change by remember { mutableStateOf(true) }
                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.inversePrimary)
                )
                {
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick =
                        {
                            //change = !change
                            //mediaPlayer.start()
                        },
                        modifier = Modifier.alpha(0f)
                    ) {
                        Text(
                            text = "buton",
                            style = AppTypography.headlineSmall
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.inversePrimary)
                    ) {

                        Spacer(modifier = Modifier.height(20.dp))
                        Button(
                            onClick = {
                                change = !change
                            },
                            shape = MaterialTheme.shapes.medium,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                contentColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Text(
                                text = getString(R.string.changeView),
                                style = AppTypography.headlineSmall
                            )
                        }
                        if (change) {
                            ConfigPage()
                        } else {
                            SobreNosotros()
                        }
                    }
                }
            }
        }
    }
}

// Preview Acerca De
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AcercaDePreview(){
    AppTheme {
        MainPage()
    }
}

// Preview Sobre Nosotros
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SobreNosotrosPreview(){
    AppTheme {
        SobreNosotros()
    }
}

//hey sorry about you just got in my way i promise honey i can feel your pain maybe i enjoy it just a little bit does that make me INSANE PAPA PA PAPA PA PAPA PA PAPA
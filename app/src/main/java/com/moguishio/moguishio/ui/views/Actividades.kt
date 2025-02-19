package com.moguishio.moguishio.ui.views

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.moguishio.moguishio.R
import com.moguishio.moguishio.model.activities.ActivityResponse
import com.moguishio.moguishio.ui.components.BotonVolver
import com.moguishio.moguishio.ui.components.EstablecerTexto
import com.moguishio.moguishio.ui.theme.AppTypography
import com.moguishio.moguishio.viewmodel.activities.ViewModelActivities
import com.moguishio.moguishio.viewmodel.authentication.AuthState
import com.moguishio.moguishio.viewmodel.authentication.ViewModelAuth
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Actividades(navController: NavHostController, context: Context, viewModelActivities: ViewModelActivities){
    //val authState = authViewModel.authState.observeAsState()

    //LaunchedEffect(authState.value) {
    //    when(authState.value)
    //    {
    //        is AuthState.Error -> navController.navigate("Principal")
    //        is AuthState.Unauthenticated -> navController.navigate("Principal")
    //        else -> {}
    //    }
    //}

    val token = viewModelActivities.token.observeAsState("")
    LaunchedEffect(token.value) {
        if (token.value.isNotEmpty()) {
            Log.e("rosiogamba", "S")
            viewModelActivities.getAllActivities()
        }
    }

    LaunchedEffect(Unit) {
        viewModelActivities.loadCredentials()
    }

    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { TabItem.entries.size })
    val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }

    val activities = viewModelActivities.activities.observeAsState(emptyList())

    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "Home") }) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding())
        ) {
            TabRow(
                selectedTabIndex = selectedTabIndex.value,
                modifier = Modifier.fillMaxWidth()
            ) {
                TabItem.entries.forEachIndexed { index, currentTab ->
                    Tab(
                        selected = selectedTabIndex.value == index,
                        selectedContentColor = colorScheme.primary,
                        unselectedContentColor = colorScheme.outline,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(currentTab.ordinal)
                            }
                        },
                        text = { Text(text = currentTab.text) },
                        icon = {
                            Icon(
                                imageVector = if (selectedTabIndex.value == index)
                                    currentTab.selectedIcon else currentTab.unselectedIcon,
                                contentDescription = "Tab Icon"
                            )
                        }
                    )
                }
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                when(selectedTabIndex.value){
                    0 -> ShowActivities(activities.value)
                    1 -> ShowUserActivities()
                }
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = TabItem.entries[selectedTabIndex.value].text)
                }
            }
        }
    }
}

enum class TabItem(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val text: String
) {
    Shop(
        unselectedIcon = Icons.Outlined.ShoppingCart,
        selectedIcon = Icons.Filled.ShoppingCart,
        text = "Shop"
    ),
    Favourite(
        unselectedIcon = Icons.Outlined.FavoriteBorder,
        selectedIcon = Icons.Filled.Favorite,
        text = "Favourite"
    )
}

@Composable
fun ShowActivities(activities: List<ActivityResponse>)
{
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(75.dp))
        EstablecerTexto(
            text = "",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            style = AppTypography.displayLarge
        )
        Spacer(modifier = Modifier.height(50.dp))

        if (activities.isEmpty()) {
            CircularProgressIndicator()
        } else {
            ActivitiesList(activities)
        }

    }
}

@Composable
fun ShowUserActivities() {

}

@Composable
fun ActivitiesList(filmList: List<ActivityResponse>) {
    LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
        itemsIndexed(items = filmList) { index, item ->
            ActivityItem(film = item)
        }
    }
}

@Composable
fun ActivityItem(film: ActivityResponse) {
    Card(
        modifier = Modifier
            .padding(8.dp, 4.dp)
            .fillMaxWidth()
            .height(110.dp), shape = RoundedCornerShape(8.dp)
    ) {
        Surface {

            Row(
                Modifier
                    .padding(4.dp)
                    .fillMaxSize()
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxHeight()
                        .weight(0.7f)
                ) {
                    EstablecerTexto(
                        text = film.name,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    EstablecerTexto(
                        text = film.description,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    EstablecerTexto(
                        text = film.place,
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }

}
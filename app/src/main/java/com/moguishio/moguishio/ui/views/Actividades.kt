package com.moguishio.moguishio.ui.views

import android.content.Context
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.moguishio.moguishio.R
import com.moguishio.moguishio.model.activities.ActivityResponse
import com.moguishio.moguishio.ui.components.EstablecerTexto
import com.moguishio.moguishio.ui.theme.AppTypography
import com.moguishio.moguishio.viewmodel.activities.ViewModelActivities
import com.moguishio.moguishio.viewmodel.authentication.AuthState
import com.moguishio.moguishio.viewmodel.authentication.ViewModelAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
@Composable
fun Actividades(navController: NavHostController, context: Context, viewModelActivities: ViewModelActivities, viewModelAuth: ViewModelAuth){
    val authState = viewModelAuth.authState.observeAsState()

    LaunchedEffect(authState.value) {
        Log.e("AAAAA", authState.value.toString())
        when(authState.value)
        {
            is AuthState.Error -> navController.navigate("Principal")
            is AuthState.Unauthenticated -> navController.navigate("Principal")
            else -> {
                if(authState.value == null)
                {
                    navController.navigate("Principal")
                }
            }
        }
    }

    val token = viewModelActivities.token.observeAsState("")
    LaunchedEffect(token.value) {
        if (authState.value == AuthState.Authenticated && token.value.isNotEmpty()) {
            viewModelActivities.getAllActivities()
            viewModelActivities.getUserActivities()
        }
    }

    LaunchedEffect(Unit) {
        viewModelActivities.loadCredentials()
    }

    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { TabItem.entries.size })
    val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }
    val snackbarHostState = remember { SnackbarHostState() }

    val activities = viewModelActivities.activities.observeAsState(emptyList())
    val participations = viewModelActivities.userActivities.observeAsState(emptyList())


    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "Home") }) },
        snackbarHost = { SnackbarHost(snackbarHostState) }
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
                        text = {
                            when(index) {
                                0 -> Text(context.getString(R.string.activities ))
                                1 -> Text(context.getString(R.string.participations ))
                            }
                        },
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
                    0 -> ShowActivities(activities.value, snackbarHostState, viewModelActivities, context)
                    1 -> ShowUserActivities(participations.value, snackbarHostState, viewModelActivities, context)
                }
            }
        }
    }
}

enum class TabItem(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    Activities(
        unselectedIcon = Icons.Outlined.Place,
        selectedIcon = Icons.Filled.Place
    ),
    Participation(
        unselectedIcon = Icons.Outlined.Face,
        selectedIcon = Icons.Filled.Face,
    )
}

@Composable
fun ShowActivities(activities: List<ActivityResponse>, snackbarHostState: SnackbarHostState, viewModelActivities: ViewModelActivities, context: Context) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(75.dp))
        EstablecerTexto(
            text = context.getString(R.string.activities),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            style = AppTypography.displayLarge
        )
        Spacer(modifier = Modifier.height(50.dp))

        if (activities.isEmpty()) {
            CircularProgressIndicator()
        } else {
            ActivitiesList(activities, snackbarHostState, viewModelActivities, context)
        }
    }
}

@Composable
fun ShowUserActivities(userActivities: List<ActivityResponse>, snackbarHostState: SnackbarHostState, viewModelActivities: ViewModelActivities, context: Context) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(75.dp))
        EstablecerTexto(
            text = context.getString(R.string.participations),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            style = AppTypography.displayLarge
        )
        Spacer(modifier = Modifier.height(50.dp))

        if (userActivities.isEmpty()) {
            Text(context.getString(R.string.activities_null))
        } else {
            UserActivitiesList(userActivities, snackbarHostState, viewModelActivities, context)
        }
    }
}

@Composable
fun UserActivitiesList(userActivities: List<ActivityResponse>, snackbarHostState: SnackbarHostState, viewModelActivities: ViewModelActivities, context: Context) {
    LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
        itemsIndexed(items = userActivities) { _, activity ->
            UserActivityItem(activity = activity, snackbarHostState = snackbarHostState, viewModelActivities, context)
        }
    }
}

@Composable
fun ActivitiesList(activities: List<ActivityResponse>, snackbarHostState: SnackbarHostState, viewModelActivities: ViewModelActivities, context: Context) {
    LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
        itemsIndexed(items = activities) { _, activity ->
            ActivityItem(activity = activity, snackbarHostState = snackbarHostState, viewModelActivities, context)
        }
    }
}

@Composable
fun ActivityItem(activity: ActivityResponse, snackbarHostState: SnackbarHostState, viewModelActivities: ViewModelActivities, context: Context) {
    val scope = rememberCoroutineScope()

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
                        text = activity.id.toString(),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    EstablecerTexto(
                        text = activity.name,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    EstablecerTexto(
                        text = activity.description,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    EstablecerTexto(
                        text = activity.place,
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center
                    )
                }

                IconButton(
                    onClick = {
                        scope.launch {
                            viewModelActivities.createParticipation(activity.id)
                            snackbarHostState.showSnackbar(
                                context.getString(R.string.activity_joined),
                                duration = SnackbarDuration.Long
                            )
                            //viewModelActivities.getUserActivities()
                        }
                    }
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Apuntarse")
                }
            }
        }
    }
}

@Composable
fun UserActivityItem(activity: ActivityResponse, snackbarHostState: SnackbarHostState, viewModelActivities: ViewModelActivities, context: Context) {
    val scope = rememberCoroutineScope()
    var visible by remember { mutableStateOf(true) }
    val density = LocalDensity.current

    /*AnimatedVisibility(
        visible = visible,
        enter = slideInVertically {
            // Slide in from 40 dp from the top.
            with(density) { -40.dp.roundToPx() }
        } + expandVertically(
            // Expand from the top.
            expandFrom = Alignment.Top
        ) + fadeIn(
            // Fade in with the initial alpha of 0.3f.
            initialAlpha = 0.3f
        ),
        exit = slideOutVertically() + shrinkVertically() + fadeOut(),
    ) {*/
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
                            text = activity.id.toString(),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                        EstablecerTexto(
                            text = activity.name,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    }

                    IconButton(
                        onClick = {
                            scope.launch {
                                viewModelActivities.deleteParticipation(activity.id)
                                snackbarHostState.showSnackbar(
                                    context.getString(R.string.activity_deleted),
                                    duration = SnackbarDuration.Long
                                )
                                visible = false
                                //delay(1000)
                            }
                        }
                    ) {
                        Icon(Icons.Filled.Delete, contentDescription = "Desuscribirse")
                    }
                }
            }
        //}
    }
}

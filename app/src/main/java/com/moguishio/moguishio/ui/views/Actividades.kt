package com.moguishio.moguishio.ui.views

import android.content.Context
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.delay
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
        itemsIndexed(items = userActivities, key = { index, _ -> index } ) { _, activity ->
            UserActivityItem(activity = activity, snackbarHostState = snackbarHostState, viewModelActivities, context)
        }
    }
}

@Composable
fun ActivitiesList(activities: List<ActivityResponse>, snackbarHostState: SnackbarHostState, viewModelActivities: ViewModelActivities, context: Context) {
    LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
        itemsIndexed(items = activities, key = { index, _ -> index }) { _, activity ->
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
            .height(150.dp),
        shape = RoundedCornerShape(16.dp),
    ) {
        Surface(
            color = colorScheme.surface.copy(alpha = 0.8f)
        ) {
            Row(
                Modifier
                    .padding(12.dp)
                    .fillMaxSize()
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxHeight()
                        .weight(4f)
                ) {
                    EstablecerTexto(
                        text = context.getString(R.string.activity_name) + " " + activity.name,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium,
                        color = colorScheme.onSurface,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    EstablecerTexto(
                        text = context.getString(R.string.activity_description) + " " + activity.description,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Normal,
                        style = MaterialTheme.typography.bodyMedium,
                        color = colorScheme.onSurface.copy(alpha = 0.7f),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    EstablecerTexto(
                        text = context.getString(R.string.activity_place) + " " + activity.place,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Normal,
                        style = MaterialTheme.typography.bodySmall,
                        color = colorScheme.onSurface.copy(alpha = 0.6f),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                IconButton(
                    onClick = {
                        scope.launch {
                            viewModelActivities.createParticipation(activity.id)
                            snackbarHostState.showSnackbar(
                                context.getString(R.string.activity_joined),
                                duration = SnackbarDuration.Long,
                                withDismissAction = true
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
    //val scope = rememberCoroutineScope()
    var visible by remember { mutableStateOf(true) }

    AnimatedVisibility(
        visible = visible,
        enter = slideInHorizontally(),
        exit = slideOutHorizontally() + shrinkHorizontally(),
    ) {
        Card(
            modifier = Modifier
                .padding(8.dp, 4.dp)
                .fillMaxWidth()
                .height(150.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Surface(
                color = colorScheme.surface.copy(alpha = 0.8f)
            ) {
                Row(
                    Modifier
                        .padding(12.dp)
                        .fillMaxSize()
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxHeight()
                            .weight(4f)
                    ) {
                        EstablecerTexto(
                            text = context.getString(R.string.activity_name) + " " + activity.name,
                            textAlign = TextAlign.Start,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleMedium,
                            color = colorScheme.onSurface,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        EstablecerTexto(
                            text = context.getString(R.string.activity_description) + " " + activity.description,
                            textAlign = TextAlign.Start,
                            fontWeight = FontWeight.Normal,
                            style = MaterialTheme.typography.bodyMedium,
                            color = colorScheme.onSurface.copy(alpha = 0.7f),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        EstablecerTexto(
                            text = context.getString(R.string.activity_place) + " " + activity.place,
                            textAlign = TextAlign.Start,
                            fontWeight = FontWeight.Normal,
                            style = MaterialTheme.typography.bodySmall,
                            color = colorScheme.onSurface.copy(alpha = 0.6f),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    IconButton(
                        onClick = {
                            // Por si el usuario cambia de vista muy rápido
                            visible = false
                            CoroutineScope(Dispatchers.Main).launch {
                                viewModelActivities.deleteParticipation(activity.id)
                                viewModelActivities.getUserActivities()
                                snackbarHostState.showSnackbar(
                                    context.getString(R.string.activity_deleted),
                                    duration = SnackbarDuration.Short,
                                    withDismissAction = true
                                )
                                //delay(1000)

                            }
                        }
                    ) {
                        Icon(Icons.Filled.Delete, contentDescription = "Desuscribirse")
                    }
                }
            }
        }
    }
}

/*@Composable
fun CustomActivityColumn(context: Context, activity: ActivityResponse)
{
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(4.dp)
            .fillMaxHeight()
    ) {
        EstablecerTexto(
            text = context.getString(R.string.activity_name) + activity.name,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium,
            color = colorScheme.onSurface,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(4.dp))

        EstablecerTexto(
            text = context.getString(R.string.activity_description) + activity.name,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Normal,
            style = MaterialTheme.typography.bodyMedium,
            color = colorScheme.onSurface.copy(alpha = 0.7f),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(4.dp))

        EstablecerTexto(
            text = context.getString(R.string.activity_place) + activity.name,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Normal,
            style = MaterialTheme.typography.bodySmall,
            color = colorScheme.onSurface.copy(alpha = 0.6f),
            modifier = Modifier.fillMaxWidth()
        )
    }
}*/
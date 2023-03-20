package com.accenture.mvi.presentation.view

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.accenture.mvi.R
import com.accenture.mvi.presentation.ListViewModel
import com.accenture.mvi.presentation.model.Pokemon
import com.accenture.mvi.presentation.mvi.ListScreenIntent
import com.accenture.mvi.presentation.mvi.ListScreenView
import com.accenture.mvi.presentation.navigation.NavArgs
import com.accenture.mvi.presentation.navigation.Navigation
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(viewModel: ListViewModel = koinViewModel(), navController: NavController) {

    val state by viewModel.uiState.collectAsState()

    BackHandler(enabled = false) {
        viewModel.intent(ListScreenIntent.Back)
    }

    ComposableLifecycle { _, event ->
        if (event == Lifecycle.Event.ON_START) {
            viewModel.intent(ListScreenIntent.LoadContent)
        }
    }

    val systemUiController = rememberSystemUiController()
    val color = MaterialTheme.colorScheme.primary
    SideEffect {
        systemUiController.setSystemBarsColor(color)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp)
                    )},
                scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }, content = { padding ->
            Column(modifier = Modifier
                .padding(padding)
                .fillMaxSize()) {
                when (state) {
                    is ListScreenView.Started -> { }
                    is ListScreenView.ShowLoading -> Loading()
                    is ListScreenView.ShowContent -> {
                        LoadGrid(items = (state as ListScreenView.ShowContent).items)
                    }
                    is ListScreenView.ShowError -> Error()
                    is ListScreenView.NavigateToDetail -> {
                        viewModel.intent(ListScreenIntent.Finish)
                        navController.currentBackStackEntry?.savedStateHandle?.set(
                            key = NavArgs.Detail.key,
                            value = (state as ListScreenView.NavigateToDetail).pokemon
                        )

                        navController.navigate(Navigation.DetailScreen.route)
                    }
                    is ListScreenView.NavigateBack -> {
                        navController.navigateUp()
                    }
                    is ListScreenView.Ended -> { }
                }
            }
        })
}

@Composable
fun LoadGrid(viewModel: ListViewModel = koinViewModel(), items: List<Pokemon> = listOf()) {
    LazyVerticalGrid(columns = GridCells.Fixed(3)) {
        items(items) { item ->
            Box(modifier = Modifier
                .clickable {
                    viewModel.intent(ListScreenIntent.GoToDetail(item))
                }
                .fillMaxSize()
                .background(color = Color(item.backgroundColor))) {
                AsyncImage(model = item.detail().sprites().other.sprite.frontDefault, contentDescription = item.name())
                Spacer(modifier = Modifier.size(24.dp))
                Text(text = item.name(), modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(4.dp),
                    fontSize = 24.sp, color = Color(item.textColor), textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Bold, overflow = TextOverflow.Ellipsis, maxLines = 1
                )
            }
        }
    }
}
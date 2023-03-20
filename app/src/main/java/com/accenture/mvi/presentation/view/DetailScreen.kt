package com.accenture.mvi.presentation.view

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.accenture.mvi.R
import com.accenture.mvi.presentation.DetailViewModel
import com.accenture.mvi.presentation.model.Pokemon
import com.accenture.mvi.presentation.model.TypeDetail
import com.accenture.mvi.presentation.mvi.DetailScreenIntent
import com.accenture.mvi.presentation.mvi.DetailScreenView
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.koin.androidx.compose.koinViewModel
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(viewModel: DetailViewModel = koinViewModel(), pokemon: Pokemon, navController: NavController) {

    val state by viewModel.uiState.collectAsState()

    BackHandler(enabled = true) {
        viewModel.intent(DetailScreenIntent.Back)
    }

    ComposableLifecycle { _, event ->
        if (event == Lifecycle.Event.ON_START) {
            viewModel.intent(DetailScreenIntent.LoadContent(pokemon = pokemon))
        }
    }

    val color = Color(pokemon.textColor)

    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setSystemBarsColor(color)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        color = Color(pokemon.textColor),
                        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp)) },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color(pokemon.topBarColor)
                ),
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center)
        {
            when (state) {
                is DetailScreenView.ShowLoading -> Loading()
                is DetailScreenView.ShowContent -> DetailContent(pokemon = (state as DetailScreenView.ShowContent).pokemon)
                is DetailScreenView.ShowError -> Error()
                is DetailScreenView.NavigateBack -> {
                    navController.navigateUp()
                }
                is DetailScreenView.Finish -> { }
            }
        }
    }
}

@Composable
fun DetailContent(pokemon: Pokemon) {

    val detail = pokemon.detail()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(pokemon.backgroundColor)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center)
    {
        AsyncImage(model = detail.sprites().other.sprite.frontDefault,
            contentDescription = detail.name())
        Spacer(modifier = Modifier.size(8.dp))
        LazyRow {
            items(detail.types()) { typeDetail ->
                TypeIcon(type = typeDetail.type)
                Spacer(modifier = Modifier.size(4.dp))
            }
        }
        Spacer(modifier = Modifier.size(16.dp))
        Text(text = detail.name()
            .replaceFirstChar { it.titlecase(Locale.getDefault()) },
            color = Color(pokemon.textColor),
            textAlign = TextAlign.Left, fontSize = 30.sp, fontWeight = FontWeight.Black)
        Spacer(modifier = Modifier.size(8.dp))
        CardRow(items = listOf(detail.height(),detail.weight()),
            backgroundColor = Color(pokemon.cardBackgroundColor),
            textColor = Color(pokemon.textColor)
        )
    }
}

@Composable
fun CardRow(items: List<Int>, backgroundColor: Color, textColor: Color) {
    LazyRow {
        items(2) {
            when (it) {
                0 -> DetailCard(label = "Height", value = "${items[it]} ft.", backgroundColor, textColor)
                1 -> DetailCard(label = "Weight", value = "${items[it]} lb.", backgroundColor, textColor)
            }
            Spacer(modifier = Modifier.size(8.dp))
        }
    }
}

@Composable
fun TypeIcon(type: TypeDetail) {
    val typeIcon = type.icon()
    val size = 24.dp

    Image(painter = painterResource(id = typeIcon.first), contentDescription = type.name(), modifier = Modifier
        .drawBehind {
            drawCircle(Color(color = typeIcon.second))
        }
        .padding(size / 2)
        .size(size = size))
}

@Composable
fun DetailCard(label: String = "", value: String = "", backgroundColor: Color, textColor: Color) {
    Card(modifier = Modifier.height(80.dp), colors = CardDefaults.cardColors(containerColor = backgroundColor)) {
        ConstraintLayout {
            val (topText, bottomText) = createRefs()

            Text(text = label, modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                .constrainAs(topText) {
                    top.linkTo(parent.top)
                }, color = textColor, style = TextStyle(color = textColor, fontSize = 16.sp,
                fontWeight = FontWeight.Bold)
            )

            Text(text = value, textAlign = TextAlign.Center, modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                .constrainAs(bottomText) {
                    top.linkTo(topText.bottom)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(topText.end)
                    start.linkTo(topText.start)
                }, color = textColor)
        }
    }
}
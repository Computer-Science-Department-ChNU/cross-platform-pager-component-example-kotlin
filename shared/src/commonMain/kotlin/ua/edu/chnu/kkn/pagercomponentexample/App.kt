package ua.edu.chnu.kkn.pagercomponentexample

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import co.touchlab.kermit.Logger
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@Composable
@Preview
fun App() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .systemBarsPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            //HorizontalPagerView()
            //VerticaPagerView()
            HorizontalPagerViewCustomize()
        }
    }
}


@Composable
fun HorizontalPagerView() {
    val pagerState = rememberPagerState(pageCount = { 10 })
    HorizontalPager(state = pagerState) { page ->
        Text(
            text = "Page: $page",
            style = TextStyle(color = Color.White),
            modifier = Modifier
                .fillMaxSize()
                .background(if(page % 2 == 0) Color.Red else Color.Blue)
        )
    }
}

@Composable
fun VerticaPagerView() {
    val pagerState = rememberPagerState(pageCount = { 10 })
    VerticalPager(state = pagerState) { page ->
        Text(
            text = "Page: $page",
            style = TextStyle(color = Color.White),
            modifier = Modifier
                .fillMaxSize()
                .background(if(page % 2 == 0) Color.Red else Color.Blue)
        )
    }
}

@Composable
fun HorizontalPagerViewCustomize() {
    val pagerState = rememberPagerState(pageCount = { 10 })
    Column {
        LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.currentPage }.collect { page ->
                Logger.i("Current page: $page")
            }
        }
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 32.dp)) { page ->
            Card(
                Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .graphicsLayer {
                        val pageOffset = (
                                (pagerState.currentPage - page) + pagerState
                                    .currentPageOffsetFraction
                                ).absoluteValue
                        Logger.i { pageOffset.toString() }

                        // We animate the alpha, between 30% and 100%
                        alpha = lerp(
                            start = 0.3f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                        val scale = lerp(
                            start = 0.6f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )

                        scaleX = scale
                        scaleY = scale
                    }
            ) {
                Text(
                    text = "Page: $page",
                    style = TextStyle(color = Color.White),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(if(page % 2 == 0) Color.Red else Color.Blue)
                )

            }
        }
        Row(
            Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pagerState.pageCount) { iteration ->
                val color = if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(16.dp)
                )
            }
        }
        val coroutineScope = rememberCoroutineScope()
        Button(onClick = {
            coroutineScope.launch {
                pagerState.scrollToPage(5)
            }
        }, modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
            Text("Jump to Page 5")
        }
        Button(
            onClick = {
            coroutineScope.launch {
                pagerState.animateScrollToPage(5)
            }
        }, modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
            Text("Jump to Page 5 animated")
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Button(
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage - 1)
                    }
                },
                enabled = pagerState.currentPage > 0,
                modifier = Modifier.weight(1f)
            ) {
                Text("Back")
            }
            Spacer(modifier = Modifier.weight(0.1f))
            Button(
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                },
                enabled = pagerState.currentPage < pagerState.pageCount - 1,
                modifier = Modifier.weight(1f)
            ) {
                Text("Next")
            }
        }
    }
}

@Composable
fun PagerItem(modifier: Modifier, page: Int) {
    TODO("Not yet implemented")
}